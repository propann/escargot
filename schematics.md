# 🐌 PROJET ESCARGOT - The Sovereign Mesh

**Le seul réseau social qui rampe plus vite que les zombies.**

*Garanti fonctionnel même après la fin du monde (si vous avez du soleil).*

## 🏗️ Architecture de la Résistance

Voici comment nous maintenons l'humanité connectée avec des bouts de ficelle et du code Python :
# 🔌 Schéma de Câblage : Node Escargot V1

Le cœur du système repose sur la symbiose entre un ESP32 (Cerveau/Radio) et un Arduino (Muscle/Crypto).

## Connexion Série (UART)

```ascii
   [ ESP32 Heltec V3 ]                  [ Arduino Nano V3 ]
   | (Master/Radio)  |                  | (Slave/Miner)   |
   |                 |                  |                 |
   | GPIO 17 (TX)    | --[Level Shift]->| D2 (RX)         |
   | GPIO 16 (RX)    | <----------------| D3 (TX)         |
   | GND             | ---------------- | GND             |
   | 5V (VBUS)       | ---------------- | VIN             |
   +-----------------+                  +-----------------+
```

**⚠️ Attention Zombie :**
- Utilisez un pont diviseur de tension (2kΩ/1kΩ) sur la ligne Arduino TX -> ESP32 RX si votre ESP32 n'est pas tolérant 5V.
- Alimentez le tout via une batterie 18650 ou un panneau solaire 5V.
```

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
