# Schéma de Connexion : Escargot Node (ESP32 + Offload Arduino)

Pour survivre aux zombies, on sépare le cerveau réseau (ESP32) du muscle crypto (Arduino).

## Pinout

| ESP32 (Heltec V3) | Arduino Nano (V3.0) | Fonction |
| :--- | :--- | :--- |
| 3.3V | 3.3V | Alimentation (Partagée) |
| GND | GND | Masse Commune (Vital!) |
| GPIO 17 (TX) | D2 (RX SoftSerial) | Commandes vers Enclave |
| GPIO 16 (RX) | D3 (TX SoftSerial) | Retour Signatures/Hashes |
| GPIO 4 | D4 | Wake-up Interrupt (Low Power) |

## Notes de survie
1. **Voltage Divider** : Si l'Arduino est un modèle 5V, utiliser un diviseur de tension (2kΩ/1kΩ) sur la ligne Arduino TX -> ESP32 RX pour ne pas griller l'ESP.
2. **Shield** : Souder directement sur perfboard pour résistance aux chocs (fuite en courant).
3. **Antenne** : Connecter l'antenne LoRa *avant* d'allumer, sinon le chip radio grille (et adieu les secours).