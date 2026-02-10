# 🔌 Guide de Câblage : Le Système Nerveux de l'Escargot

Voici comment connecter le Cerveau (ESP32) au Mineur (Arduino).

```ascii
       ESP32 (Meshtastic)                     Arduino Nano (Mineur)
    +---------------------+                 +---------------------+
    |                     |                 |                     |
    |                 RX  |<----------------| TX (D1)             |
    |                 TX  |---------------->| RX (D0)             |
    |                 GND |-----------------| GND                 |
    |                 5V  |-----------------| 5V / VIN            |
    |                     |                 |                     |
    +---------------------+                 +---------------------+
```

**Notes de survie :**
1. **RX <-> TX** : On croise les effluves. Toujours.
2. **GND commun** : Si tu oublies ça, les électrons vont se perdre et pleurer.
3. **Alimentation** : Si ton ESP32 est en 3.3V, attention au 5V de l'Arduino sur le RX. Un pont diviseur de tension peut sauver ton ESP32 d'une mort prématurée.