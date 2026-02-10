import threading
import queue
import time
import meshtastic.serial_interface
from pubsub import pub
from src.core.config import Config
from src.core.logger import logger

class RadioInterface:
    """
    Gère la communication radio de manière asynchrone.
    Ne bloque jamais le thread principal.
    """
    def __init__(self):
        self.incoming_queue = queue.Queue()
        self.interface = None
        self.connected = False
        self.running = False
        self._thread = None

    def start(self):
        """Lance le thread de surveillance radio."""
        if self.running:
            return
        self.running = True
        self._thread = threading.Thread(target=self._connection_loop, daemon=True)
        self._thread.start()
        logger.info("📡 RadioInterface: Thread d'écoute démarré.")

    def stop(self):
        """Arrête proprement l'interface."""
        self.running = False
        if self.interface:
            self.interface.close()
        if self._thread:
            self._thread.join(timeout=1)

    def get_message(self):
        """
        Récupère le prochain message de la file (Bloquant).
        Utilisé par le Cerveau pour traiter les messages un par un.
        """
        return self.incoming_queue.get()

    def _on_receive(self, packet, interface):
        """Callback interne déclenché par Meshtastic."""
        try:
            if 'decoded' in packet and 'text' in packet['decoded']:
                text = packet['decoded']['text']
                sender = packet['fromId']
                
                # On structure le paquet pour le reste du système
                msg = {
                    'sender': sender,
                    'text': text,
                    'timestamp': time.time()
                }
                self.incoming_queue.put(msg)
                # logger.debug(f"📨 Message mis en file (Queue size: {self.incoming_queue.qsize()})")
        except Exception as e:
            logger.error(f"💥 Erreur parsing paquet: {e}")

    def _connection_loop(self):
        """Boucle de vie du thread : Connexion -> Surveillance -> Reconnexion."""
        last_heartbeat = 0
        
        while self.running:
            try:
                if not self.connected:
                    logger.info(f"🔌 Tentative de connexion Meshtastic sur {Config.SERIAL_PORT}...")
                    # Cette ligne est bloquante si le port n'existe pas, ou lève une exception
                    self.interface = meshtastic.serial_interface.SerialInterface(devPath=Config.SERIAL_PORT)
                    
                    # Abonnement aux messages textes
                    pub.subscribe(self._on_receive, "meshtastic.receive")
                    
                    self.connected = True
                    logger.info("✅ Radio: Connexion établie avec succès.")

                # Heartbeat (Battement de coeur)
                if time.time() - last_heartbeat > 300: # 5 minutes
                    logger.info("💓 Radio: En ligne et opérationnelle.")
                    last_heartbeat = time.time()
                
                # Pause pour ne pas saturer le CPU
                time.sleep(1)

            except Exception as e:
                self.connected = False
                logger.error(f"💀 Erreur Critique Radio: {e}")
                
                # Nettoyage
                if self.interface:
                    try:
                        self.interface.close()
                    except:
                        pass
                    self.interface = None
                
                logger.info("🔄 Tentative de reconnexion dans 10 secondes...")
                time.sleep(10)