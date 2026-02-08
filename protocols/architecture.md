# Architecture Escargot

```mermaid
graph TD
    User[📱 Survivant (Android)] -- Bluetooth --> ESP[🕷️ ESP32 (Mesh Node)]
    
    subgraph "Hardware Node"
        ESP -- UART (Serial) --> Nano[🛡️ Arduino Nano (Enclave)]
        Nano -- I2C/SPI --> Sensors[Capteurs (Rad/Temp)]
    end
    
    subgraph "Mesh Network"
        ESP -- LoRa 868MHz --> Relay[📡 Relais (Toit)]
        Relay -- LoRa --> Bridge[🌉 Bridge (Internet intermittent)]
    end
    
    subgraph "Crypto Logic"
        Nano -->|ECDSA| Sign[Signature Message]
        Nano -->|SHA1| Mine[Duino-Coin Mining]
    end
```

## Flux de Données
1. **User** écrit un post.
2. **ESP** réveille **Arduino**.
3. **Arduino** signe le post et calcule un hash DUCO (Proof of Work).
4. **ESP** diffuse le paquet signé.
5. Les autres nœuds vérifient la signature avant de relayer (Anti-Zombie-Spoofing).