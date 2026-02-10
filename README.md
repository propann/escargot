# 🐌 PROJET ESCARGOT - The Sovereign Mesh

**Le seul réseau social qui rampe plus vite que les zombies.**

*Garanti fonctionnel même après la fin du monde (si vous avez du soleil).*
*Note : l'escargot n'inclut pas de coquille, c'est vous la coque.*

## 🏗️ Architecture de la Résistance

Voici comment nous maintenons l'humanité connectée avec des bouts de ficelle et du code Python :

```ascii
+---------------+       +--------------------+       +------------------+       +-----------------+
| Arduino Miner |       |  ESP32 Meshtastic  |       |   Raspberry Pi   |       |  Interface Web  |
| (Crypto/Sign) | ----> |    (Radio LoRa)    | ----> |   (Bridge + IA)  | ----> |   (Dashboard)   |
|   [Identité]  | UART  |   [Transmission]   | USB   |   [Filtrage]     | WiFi  |   [Survivant]   |
+---------------+       +--------------------+       +------------------+       +-----------------+
       ^                                                      |
       |                                                      v
    [Pioche]                                           +--------------+
                                                       |  ATAK (Map)  |
                                                       |  [Tactique]  |
                                                       +--------------+
```

## 🚀 Quickstart (Avant que ça morde)

Vous avez 5 minutes avant la prochaine vague ? C'est parti.

### 1. Préparer le Matériel
1.  **Arduino Nano** : Flashez `firmware/arduino_miner/miner.ino`. C'est votre pioche numérique.
2.  **ESP32** : Installez Meshtastic. Connectez l'Arduino (TX->RX, RX->TX).
3.  **Raspberry Pi** : Branchez l'ESP32 en USB.

### 2. Armer le Logiciel
```bash
# Installez les dépendances (les munitions)
pip install -r requirements.txt

# Installez le Cerveau (Ollama + TinyLlama)
chmod +x install_ollama.sh
./install_ollama.sh
```

### 3. Démarrer la Base (et la garder vivante)
Lancez le service, ouvrez le dashboard, respirez. Si ça plante, blâmez les zombies en premier, puis les logs.
