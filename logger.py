import logging
import os
from logging.handlers import RotatingFileHandler
from .config import Config

# Création du dossier de logs si nécessaire
LOG_DIR = os.path.join(os.path.dirname(Config.DB_PATH), "logs")
if not os.path.exists(LOG_DIR):
    os.makedirs(LOG_DIR, exist_ok=True)

LOG_FILE = os.path.join(LOG_DIR, "escargot.log")

def setup_logger(name="Escargot"):
    """
    Configure un logger robuste qui écrit dans la console et dans un fichier.
    Rotation: 5MB max par fichier, garde 3 backups.
    """
    logger = logging.getLogger(name)
    logger.setLevel(logging.INFO)

    # Éviter les doublons de logs si la fonction est appelée plusieurs fois
    if logger.hasHandlers():
        return logger

    # Format des logs : [DATE] [NIVEAU] [MODULE] Message
    formatter = logging.Formatter(
        '%(asctime)s [%(levelname)s] [%(name)s] %(message)s',
        datefmt='%Y-%m-%d %H:%M:%S'
    )

    # 1. Handler Console (Pour voir ce qui se passe en direct)
    console_handler = logging.StreamHandler()
    console_handler.setFormatter(formatter)
    logger.addHandler(console_handler)

    # 2. Handler Fichier (Pour l'historique post-mortem)
    file_handler = RotatingFileHandler(LOG_FILE, maxBytes=5*1024*1024, backupCount=3)
    file_handler.setFormatter(formatter)
    logger.addHandler(file_handler)

    return logger

logger = setup_logger()