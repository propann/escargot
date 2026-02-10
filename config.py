import os
import sys
from dotenv import load_dotenv

# Chargement du fichier .env
# On cherche le .env à la racine du projet (deux niveaux au-dessus de ce fichier)
BASE_DIR = os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
ENV_PATH = os.path.join(BASE_DIR, '.env')

if os.path.exists(ENV_PATH):
    load_dotenv(ENV_PATH)
else:
    print(f"⚠️  ATTENTION: Fichier .env introuvable à {ENV_PATH}")
    print("⚠️  Le système va tenter d'utiliser les variables d'environnement système.")

class Config:
    """
    Configuration centralisée du Bunker.
    Si une variable critique manque, on arrête tout.
    """
    
    @staticmethod
    def get_required(key, default=None):
        value = os.getenv(key, default)
        if value is None:
            print(f"❌ ERREUR CRITIQUE: La variable d'environnement '{key}' est manquante.")
            print("❌ Vérifiez votre fichier .env ou vos variables système.")
            sys.exit(1)
        return value

    # --- Configuration Radio ---
    SERIAL_PORT = get_required.__func__("SERIAL_PORT", "/dev/ttyUSB0")
    
    # --- Configuration Données ---
    DB_PATH = get_required.__func__("DB_PATH", os.path.join(BASE_DIR, "escargot.db"))
    
    # --- Configuration IA ---
    OLLAMA_MODEL = get_required.__func__("OLLAMA_MODEL", "tinyllama")
    OLLAMA_HOST = os.getenv("OLLAMA_HOST", "http://localhost:11434")

    # --- Configuration Web ---
    FLASK_SECRET_KEY = get_required.__func__("FLASK_SECRET_KEY", "super_secret_zombie_key")