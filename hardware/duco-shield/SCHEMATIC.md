# Duco Shield (Stub)

## Objectif
Bouclier minimal pour survivre au black-out : ESP32 LoRa + Arduino Nano (crypto/mining).

## Connexions clés (UART)
| ESP32 | Arduino Nano | Rôle |
|---|---|---|
| TX2 (GPIO17) | RX (D0) | Payload à signer/chiffrer |
| RX2 (GPIO16) | TX (D1) | Signature / cipher retournés |
| GND | GND | Référence |
| 5V (via régulateur) | 5V | Alim Nano |

## Notes
- UART à 115200 bauds.
- L’Arduino est le coffre-fort : ECDSA + AES-256 + anti-replay.
- Ajoute un interrupteur physique pour couper le mining en mode économie.
