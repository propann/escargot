# Escargot 🐌🧟

**Le seul réseau social qui rampe plus vite que les zombies.**

Inspiré de [LoRaZ-Family-Ops](https://github.com/propann/LoRaZ-Family-Ops) et Meshtastic, mais poussé dans sa version **post-apocalyptique ultime**.

Pas de serveur.  
Pas d’opérateur.  
Pas de Zuck.  
Pas de 4G.  
Et surtout : **pas de zombies qui te coupent la parole**.

### Pourquoi Escargot existe ?
- Quand l’électricité tombe → Escargot continue (solaire + batterie).
- Quand les réseaux classiques meurent → LoRa mesh prend le relais sur des dizaines de km.
- Quand les zombies envahissent Paris → tu postes « En fuite vers Lyon, qui a encore du stock de cartouches ? » et ta communauté le reçoit en clair (ou chiffré si tu veux).
- Chiffrement renforcé par Arduino/ESP32 : même un zombie hacker ne lira pas tes plans de survie.

### Fonctionnalités de survie
- **Posts courts** style Twitter (140 caractères max, parfait quand tu cours)
- **DMs chiffrés end-to-end** (clé partagée via Arduino)
- **Canaux thématiques** : #HordeSeine, #Ravitaillement, #SafeZone, #ZombiesALaTourEiffel
- **Likes & réactions** via messages gossip (pouce levé = "bien reçu", crâne = "danger")
- **Propagation automatique** : ton message saute de nœud en nœud comme un virus (mais en mieux)
- **Mode "Last Stand"** : stockage local + envoi dès qu’un relais apparaît
- Compatible Meshtastic (tu peux connecter tes nœuds existants)

### Matériel de survivant recommandé
- ESP32 LoRa (Heltec V3, LilyGo T-Beam, T-Deck…)
- Arduino Nano/Pro Micro pour la **couche crypto hardware** (gère les clés, signatures, anti-replay)
- Antenne externe + panneau solaire + batterie LiPo = nœud qui tient des semaines
- Boîtier étanche (pour quand il pleut… ou quand les zombies explosent)

### Offload Arduino + DUCO (Nouveau)
Pour économiser la batterie de l'ESP32 (qui gère le Mesh), nous déportons la cryptographie :
1. Connectez un **Arduino Nano** sur les pins RX/TX de l'ESP32.
2. Flashez `firmware/arduino-crypto-miner` sur le Nano.
3. Flashez `firmware/escargot-node` sur l'ESP32.

**Résultat** : Votre nœud mine du Duino-Coin à chaque message envoyé (récompense sociale) et signe cryptographiquement tout trafic sans ralentir le réseau.

### Setup with PlatformIO (Recommandé)
Nous utilisons PlatformIO pour des builds robustes et offline.

```bash
# 1. Initialiser et builder tout (nécessite internet une fois)
./scripts/build-all.sh

# 2. Flasher les modules
cd firmware/escargot-node && pio run -t upload      # ESP32
cd firmware/arduino-crypto-miner && pio run -t upload # Arduino
```
cd client/android
# Build & installe sur ton téléphone (même sans réseau)
