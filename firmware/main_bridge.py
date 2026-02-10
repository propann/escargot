import time
import sqlite3
import sys
import os
import meshtastic.serial_interface
from pubsub import pub

# Importation dynamique du cerveau (IA)
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))
from backend.ai.classifier import analyze_text

# Configuration
DB_PATH = "escargot.db"

def init_db():
    """Initialise la base de données de survie."""
    try:
        conn = sqlite3.connect(DB_PATH)
        c = conn.cursor()
        # Création de la table avec les colonnes demandées
        c.execute('''CREATE TABLE IF NOT EXISTS messages
                     (id INTEGER PRIMARY KEY AUTOINCREMENT, 
                      sender TEXT, 
                      text TEXT, 
                      duco_valid INTEGER, 
                      category TEXT, 
                      timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)''')
        conn.commit()
        conn.close()
        print("🗄️ DB: Initialisée.")
    except Exception as e:
        print(f"❌ Erreur Init DB: {e}")

def on_receive(packet, interface):
    """Callback exécuté à chaque réception de paquet LoRa."""
    try:
        if 'decoded' in packet and 'text' in packet['decoded']:
            text = packet['decoded']['text']
            sender = packet['fromId']
            
            print(f"📩 REÇU de {sender}: {text}")

            category = "INCONNU"
            duco_valid = 0 # 0 = False, 1 = True

            # Logique de tri
            if text.startswith("DUCO|"):
                print("⛏️ MINAGE: Preuve de travail reçue.")
                category = "MINAGE"
                duco_valid = 1 # On assume valide pour l'instant
            else:
                # On passe le relais à l'IA
                category, summary = analyze_text(text)
                print(f"🧠 IA: [{category}] {summary}")

            # Sauvegarde blindée en base de données
            try:
                conn = sqlite3.connect(DB_PATH)
                c = conn.cursor()
                c.execute("INSERT INTO messages (sender, text, duco_valid, category) VALUES (?, ?, ?, ?)", 
                          (sender, text, duco_valid, category))
                conn.commit()
                conn.close()
                print(f"💾 Sauvegardé: [{category}]")
            except Exception as e:
                print(f"⚠️ Erreur DB: {e}")

    except Exception as e:
        print(f"💥 Erreur traitement paquet: {e}")

def main():
    print("🐌 PONT: Démarrage du système de communication...")
    init_db()

    while True:
        try:
            print("🔌 Connexion au noeud Meshtastic (USB)...")
            # L'interface lance un thread d'écoute en arrière-plan
            interface = meshtastic.serial_interface.SerialInterface()
            
            # Abonnement aux messages
            pub.subscribe(on_receive, "meshtastic.receive")
            print("✅ RADIO: Connecté. En attente de messages...")

            # Boucle de maintien en vie
            while True:
                time.sleep(1)
                # Si l'interface plante, on espère que l'exception remontera ou que le thread mourra
                
        except Exception as e:
            print(f"💀 ERREUR CRITIQUE: {e}")
            print("🔄 Tentative de reconnexion dans 5 secondes...")
            time.sleep(5)

if __name__ == "__main__":
    main()