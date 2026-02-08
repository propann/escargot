# Crypto Layer — Offload Arduino

## Pourquoi offload ?
L’ESP32 garde le mesh LoRa stable. L’Arduino fait le sale boulot crypto/mining.

## Fonctions
- ECDSA signatures (TinyECC)
- Anti-replay (nonce + timestamp)
- Mining DUCO low-power (hash S1)

## Flash via PlatformIO
```bash
cd firmware/arduino-crypto-miner
pio run -t upload
```

## Performance (cible)
- 170 kH/s sur Arduino Nano (variable selon duty-cycle).
- Throttle mining si batterie faible.

## Mode survie
- Survive blackout: stockage offline, sync à la reprise.
