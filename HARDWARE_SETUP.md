# 🛠️ GUIDE DE MONTAGE - Forger l'Escargot

**Attention :** Ce matériel est votre seule ligne de vie. Traitez-le avec respect (et sans café renversé).

## 1. La Liste de Pillage (BOM)

Si vous devez fouiller un magasin d'électronique abandonné, voici ce qu'il faut prendre (dans l'ordre de priorité) :

1.  **Cerveau Radio :** ESP32 LoRa V3 (Heltec ou TTGO). *Vital.*
2.  **Mineur :** Arduino Nano V3 (ou clone chinois, ça marche pareil). *Essentiel pour l'économie et les débats de campement.*
3.  **QG :** Raspberry Pi 4 ou 5 (Le Zero 2W suffit en mode dégradé). *Pour l'IA.*
4.  **Énergie :** Batterie LiFePO4 ou Li-Ion 18650. *Ne prenez pas de LiPo gonflées.*
5.  **Antenne :** 868MHz (Europe) ou 915MHz (US). *Sans ça, vous êtes sourd et muet.*

## 2. Le Câblage Nerveux (Wiring)

Connectez le Mineur (Arduino) au Cerveau (ESP32). Ne tremblez pas. Si vous tremblez, posez le fer.

### Tableau des Connexions

| Arduino Nano (Mineur) | ESP32 (Radio) | Note Tactique |
| :--- | :--- | :--- |
| **D3 (TX)** | **GPIO 16 (RX)** | *Envoie les preuves de minage.* |
| **D2 (RX)** | **GPIO 17 (TX)** | *Reçoit les ordres de minage.* |
| **GND** | **GND** | *Masse commune. OBLIGATOIRE.* |
| **VIN** | **5V (VBUS)** | *Alimentation (si via USB).* |

### Schéma Rapide
```ascii
   [ ARDUINO ]             [ ESP32 ]
      TX (D3) ------------> RX (16)
      RX (D2) <------------ TX (17)
      GND     ------------- GND
```

## 3. Énergie Vitale (Solaire)

Le réseau électrique est mort. Le soleil est votre ami.

*   **Panneau :** 5V / 6W minimum. Plus c'est gros, plus ça charge (science approuvée).
*   **Régulateur :** TP4056 (pour charger une 18650).
*   **Montage :** Panneau -> TP4056 -> Batterie -> Boost 5V -> USB ESP32.

> **Astuce de Survivant :** Si vous avez un vieux "Powerbank" solaire, démontez-le. L'électronique dedans est souvent suffisante pour alimenter un nœud Escargot H24.

## 4. ☠️ ZONE DE DANGER ☠️

Lisez ceci ou mourrez (numériquement).

### ⚠️ L'Antenne Fantôme
**NE JAMAIS** alimenter le module LoRa sans son antenne vissée.
*Conséquence :* L'énergie radio rebondit à l'intérieur de la puce et la fait frire instantanément. Game Over.

### ⚠️ Inversion de Polarité
Le Rouge sur le Rouge (+), le Noir sur le Noir (-).
*Conséquence :* Fumée magique, odeur de plastique brûlé, larmes. Et un silence radio très esthétique.

### ⚠️ Le Piège du 5V
L'Arduino parle en 5V. L'ESP32 écoute en 3.3V.
*Solution :* Mettez un pont diviseur (2 résistances) sur la ligne Arduino TX -> ESP32 RX si vous voulez que votre ESP32 vive vieux.

---
*Bonne chance pour l'assemblage. Ne laissez pas tomber d'étain sur vos chaussures.*
