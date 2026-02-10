import threading
import queue
import time
import json
from datetime import datetime
from functools import lru_cache
import ollama
from src.core.config import Config
from src.core.logger import logger
from src.core.database import db

class AIWorker:
    """
    Ouvrier spécialisé qui traite les messages en arrière-plan.
    Utilise un cache pour ne pas réfléchir deux fois à la même chose.
    """
    def __init__(self, input_queue):
        self.input_queue = input_queue
        self.running = False
        self._thread = None

    def start(self):
        if self.running:
            return
        self.running = True
        self._thread = threading.Thread(target=self._worker_loop, daemon=True)
        self._thread.start()
        logger.info("🧠 AIWorker: Cerveau activé et en attente de tâches.")

    def stop(self):
        self.running = False
        if self._thread:
            self._thread.join(timeout=1)

    @lru_cache(maxsize=128)
    def _analyze_text_cached(self, text):
        """
        Interroge Ollama avec mise en cache.
        Si le texte a déjà été vu (doublon Mesh), on retourne le résultat immédiat.
        """
        model = Config.OLLAMA_MODEL
        
        # Prompt strict pour forcer le JSON
        prompt = (
            f"Tu es un système de tri pour survivants. "
            f"Analyse ce message: '{text}'. "
            f"Réponds UNIQUEMENT avec un objet JSON valide contenant deux clés: "
            f"'category' (choisir parmi: URGENCE, SOCIAL, COMMERCE, INFOS) "
            f"et 'summary' (résumé en 5 mots max)."
        )

        try:
            # Appel à l'API Ollama avec format JSON forcé
            response = ollama.chat(
                model=model,
                messages=[{'role': 'user', 'content': prompt}],
                format='json', 
                options={'temperature': 0.1} # Très déterministe
            )
            
            content = response['message']['content']
            return json.loads(content)
            
        except Exception as e:
            # On propage l'erreur pour que la boucle de retry la gère (ne pas cacher les erreurs)
            raise e

    def _worker_loop(self):
        while self.running:
            try:
                # Récupération non-bloquante avec timeout pour vérifier le flag running
                try:
                    msg = self.input_queue.get(timeout=1)
                except queue.Empty:
                    continue

                text = msg.get('text')
                sender = msg.get('sender')
                ts = msg.get('timestamp')
                
                try:
                    logger.info(f"🧠 AIWorker: Analyse de '{text[:30]}...'")
                    
                    # Appel avec Cache + Retry implicite via l'exception
                    result = self._analyze_text_cached(text)
                    
                    category = result.get('category', 'INFOS').upper()
                    # Validation basique des catégories
                    if category not in ['URGENCE', 'SOCIAL', 'COMMERCE', 'INFOS']:
                        category = 'INFOS'
                        
                    logger.info(f"🏷️  Classé: [{category}]")

                    # Sauvegarde en base
                    formatted_ts = datetime.fromtimestamp(ts).strftime('%Y-%m-%d %H:%M:%S') if ts else None
                    db.execute_query(
                        "INSERT INTO messages (sender, text, category, timestamp) VALUES (?, ?, ?, ?)",
                        (sender, text, category, formatted_ts)
                    )
                    
                    self.input_queue.task_done()

                except Exception as e:
                    logger.warning(f"⚠️ AIWorker: Ollama indisponible ({e}). Retry dans 5s...")
                    time.sleep(5)
                    # On remet le message dans la queue pour ne pas le perdre
                    self.input_queue.put(msg)

            except Exception as e:
                logger.critical(f"💀 AIWorker: Erreur fatale dans la boucle: {e}")
                time.sleep(1)