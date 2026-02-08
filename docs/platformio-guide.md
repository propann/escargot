# PlatformIO — Guide de Survie 🧟‍♂️🛠️

## Pourquoi PlatformIO ?
- **Indépendance** : Plus besoin de l'IDE Arduino lourd et lent.
- **Offline** : Une fois les libs téléchargées, vous pouvez compiler au fond d'un bunker sans internet.
- **Multi-Cible** : Gère l'ESP32 et l'Arduino Nano dans le même projet.

## Installation Rapide
1. Installez **VS Code**.
2. Installez l'extension **PlatformIO IDE**.
3. Ouvrez le dossier `escargot`.

## Commandes de Survie

### 1. Flasher le Cerveau (ESP32)
Connectez l'ESP32 en USB.
```bash
cd firmware/escargot-node
pio run -t upload
```

### 2. Flasher le Muscle (Arduino Nano)
Connectez l'Arduino en USB.
```bash
cd firmware/arduino-crypto-miner
pio run -t upload
```

### 3. Mode Black-out (Offline)
Si vous savez que l'internet va couper :
1. Lancez un build complet quand vous avez encore du réseau :
   `./scripts/build-all.sh`
2. PlatformIO mettra en cache toutes les toolchains et librairies.
3. Vous êtes maintenant autonome.

## Dépannage
- **Port introuvable ?** Vérifiez le câble USB (beaucoup de câbles "charge only" traînent).
- **Permission denied ?** Sous Linux, ajoutez votre user au groupe `dialout` : `sudo usermod -a -G dialout $USER`.