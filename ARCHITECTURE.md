# 🏗️ ARCHITECTURE DU SYSTÈME - La Forteresse Numérique

**Classification :** POUR VOS YEUX SEULEMENT
**Statut :** Opérationnel / Bunker-Ready (et légèrement paranoïaque)

## 1. Vue d'Ensemble : La Trinité de la Survie

Le système Escargot n'est pas un simple gadget. C'est un organisme symbiotique composé de trois organes vitaux, conçus pour fonctionner quand tout le reste a échoué.

### A. Le Mineur (Arduino Nano) - "Le Muscle"
C'est l'ouvrier incatigable. Il ne réfléchit pas, il creuse (et il ne demande pas de pause syndicale).
*   **Rôle :** Exécuter l'algorithme de hachage DUCO-S1 (SHA1) pour générer de la monnaie (Mana) et signer cryptographiquement les messages (ECDSA).
*   **Pourquoi :** Parce que la cryptographie coûte cher en cycles CPU, et on ne veut pas bloquer la radio pour ça.

### B. La Radio (ESP32 / Meshtastic) - "La Bouche"
C'est le héraut de l'apocalypse. Il parle lentement, mais il porte loin. Très loin. Genre, pas mal loin.
*   **Rôle :** Gérer le réseau Mesh LoRa (868MHz). Il reçoit les paquets, les répète et les transmet au QG.
*   **Devise :** "Lentement mais sûrement." (1kbps, c'est mieux que 0kbps).

### C. Le QG (Raspberry Pi) - "Le Cerveau"
C'est le stratège. Il voit tout, analyse tout, et décide qui est un ami et qui est un zombie (spoiler : pas vous).
*   **Rôle :** Héberger la base de données, faire tourner l'IA de filtrage (Ollama), et servir l'interface Web aux survivants via WiFi local.

---

## 2. Flux de Données : Le Chemin de la Vérité

Voici comment un message traverse le chaos pour arriver sur votre écran :

```ascii
[ User Phone ]      (Le Survivant tape "Besoin d'eau")
      |
      v  (Bluetooth / WiFi)
      |
[ Meshtastic Node ] (L'ESP32 reçoit le signal)
      |
      v  (LoRa 868MHz - La traversée du désert)
      |
[ Gateway Pi ]      (Le QG capte le message via USB/Serial)
      |
      v  (Analyse Locale)
[ AI Filter ]       (Ollama : "Est-ce un spam de zombie ?")
      |
      +---> [NON] -> Poubelle (/dev/null)
      |
      +---> [OUI] -> [ Web DB ] (SQLite WAL) -> [ Dashboard ]
```

---

## 3. L'Arsenal Technique (Stack Bunker-Ready)

Nous n'avons pas choisi ces technologies pour la "hype", mais pour la survie.

*   **Java 21 (LTS) :** Le moteur du backend.
    *   *Pourquoi ?* Robustesse, typage fort, et surtout les **Virtual Threads** pour gérer des milliers de connexions simultanées sans mettre le CPU à genoux.
*   **Bitsets (RoaringBitmap) :** La compression tactique.
    *   *Pourquoi ?* Stocker des millions d'IDs de messages ou de contraintes logiques en utilisant quelques kilo-octets de RAM. Chaque bit compte.
*   **SQLite (Mode WAL) :** La mémoire de l'éléphant.
    *   *Pourquoi ?* Pas de serveur SQL lourd à configurer. Un fichier, solide, transactionnel. Le mode WAL (Write-Ahead Logging) assure qu'on ne perd rien même si l'alimentation coupe brutalement.
*   **Python (AsyncIO) :** Le liant.
    *   *Pourquoi ?* Pour parler aux périphériques (Serial, I2C) et orchestrer l'IA avec souplesse.

---
*Fin du document. Détruisez après lecture si compromis.*
