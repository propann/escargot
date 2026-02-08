# Duco Shield — Schematics (Stub)

## Objectif
Module plug/unplug pour survivre à la horde : ESP32 LoRa + Arduino Nano en offload crypto/mining.

## Connexions série (UART)
| ESP32 Heltec V3 | Arduino Nano | Rôle |
|---|---|---|
| GPIO17 / TX2 | D0 / RX | Commandes SIGN/MINE |
| GPIO16 / RX2 | D1 / TX | Réponses SIG/SHARE |
| GND | GND | Référence |
| 3.3V | 5V (via régul) | Alim Nano |

## Modularité
- Arduino sur header 2x6 pour retrait rapide.
- ESP32 reste maître du mesh, Arduino offload crypto/mining.

## Notes post-apo
- Ajoute un jumper pour couper UART si fuite d’énergie.
- Prévoir boîtier étanche + camouflage anti-EMP.
