# Architecture Escargot

```
[ESP32 LoRa Mesh] <---UART---> [Arduino Nano]
       |                         |
       |                         +-- ECDSA + DUCO mining
       +-- Gossip posts/DMs
```

## Flux principal
1. ESP32 reçoit un post.
2. ESP32 demande à l’Arduino de signer + miner.
3. ESP32 diffuse le message signé via mesh.
4. Shares DUCO stockées offline puis sync.
