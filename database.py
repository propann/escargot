import sqlite3
import threading
from contextlib import contextmanager
from .config import Config
from .logger import logger

class DatabaseManager:
    """
    Gardien de la Mémoire.
    Gère l'accès concurrent à SQLite sans corruption grâce au mode WAL.
    Pattern Singleton pour éviter les conflits d'initialisation.
    """
    _instance = None
    _lock = threading.Lock()

    def __new__(cls):
        if cls._instance is None:
            with cls._lock:
                if cls._instance is None:
                    cls._instance = super(DatabaseManager, cls).__new__(cls)
                    cls._instance._initialized = False
        return cls._instance

    def __init__(self):
        if self._initialized:
            return
        self.db_path = Config.DB_PATH
        self._init_db()
        self._initialized = True

    def _init_db(self):
        """Initialise la DB, crée les tables et active WAL."""
        try:
            # Connexion temporaire pour le setup initial
            with sqlite3.connect(self.db_path) as conn:
                cursor = conn.cursor()
                # WAL = Write-Ahead Logging (Indispensable pour la concurrence)
                cursor.execute("PRAGMA journal_mode=WAL;")
                cursor.execute("PRAGMA synchronous=NORMAL;")
                
                # Migration : Table des messages
                cursor.execute('''CREATE TABLE IF NOT EXISTS messages
                                 (id INTEGER PRIMARY KEY AUTOINCREMENT, 
                                  sender TEXT, 
                                  text TEXT, 
                                  duco_valid INTEGER DEFAULT 0, 
                                  category TEXT DEFAULT 'INCONNU', 
                                  timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)''')
                
                conn.commit()
            logger.info(f"🗄️ DatabaseManager: Base initialisée avec succès (WAL actif).")
        except Exception as e:
            logger.critical(f"❌ DatabaseManager: Impossible d'initialiser la DB: {e}")
            raise

    @contextmanager
    def get_connection(self):
        """
        Fournit une connexion thread-safe via un context manager.
        Gère le commit/rollback automatiquement.
        """
        # check_same_thread=False permet plus de souplesse si l'objet conn est passé
        conn = sqlite3.connect(self.db_path, check_same_thread=False)
        conn.row_factory = sqlite3.Row
        try:
            yield conn
            conn.commit()
        except Exception as e:
            logger.error(f"⚠️ DatabaseManager: Rollback suite à erreur: {e}")
            conn.rollback()
            raise
        finally:
            conn.close()

    def execute_query(self, query, params=()):
        """Exécute une requête d'écriture (INSERT/UPDATE/DELETE)."""
        with self.get_connection() as conn:
            cursor = conn.cursor()
            cursor.execute(query, params)
            return cursor.lastrowid

    def fetch_all(self, query, params=()):
        """Exécute une requête de lecture (SELECT)."""
        with self.get_connection() as conn:
            cursor = conn.cursor()
            cursor.execute(query, params)
            return [dict(row) for row in cursor.fetchall()]

    def fetch_one(self, query, params=()):
        """Récupère un seul enregistrement."""
        with self.get_connection() as conn:
            cursor = conn.cursor()
            cursor.execute(query, params)
            row = cursor.fetchone()
            return dict(row) if row else None

# Instance unique exportée
db = DatabaseManager()