# Intégration DUCO — mode survie

## Objectif
Récompenser posts/relays/likes via mining low-power offloadé sur Arduino Nano.

## Pas-à-pas
1. Flasher l’ESP32 avec `firmware/escargot-node/`.
2. Flasher l’Arduino avec `firmware/arduino-crypto-miner/`.
3. Relier ESP32 ↔ Arduino (UART TX/RX + GND).
4. Configurer wallet DUCO dans le firmware ESP.
5. Envoyer un post `#HordeSeine` → création de share DUCO.

## Flash via PlatformIO
```bash
cd firmware/escargot-node
pio run -t upload

cd firmware/arduino-crypto-miner
pio run -t upload
```

## Test zombie
- Couper Internet.
- Envoyer un post et vérifier `MINE_SHARE`.
- Revenir en ligne via relais → sync shares.
