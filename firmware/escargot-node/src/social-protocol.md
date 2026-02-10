# 📜 Protocole Social Escargot (V1)

Format JSON compact pour économiser la bande passante LoRa.

## Structure du Message
```json
{
  "t": "POST",          // Type: POST, DM, LIKE, SOS
  "u": "Survivor01",    // User ID
  "c": "Zombies au nord !", // Content (Max 140 chars)
  "d": 0.01,            // Duino-Coin Tx (Reward pour le relais)
  "s": "base64_sig",    // Signature ECDSA
  "ts": 1715000000      // Timestamp
}
```