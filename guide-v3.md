# 🧟 Guide de Survie V3 (Dev & AI)

## Environnement
- **VSCode** : QG de développement.
- **PlatformIO** : Gestionnaire de munitions (firmware).
- **Ollama** : Le cerveau qui filtre les zombies.

## Workflow AI
1. Le message arrive sur l'ESP32.
2. Transmis au RPi via USB.
3. Script Python interroge Ollama : "Is this message zombie spam?"
4. Si OK -> Broadcast.

## Commandes Utiles
- `pio run -e heltec_wifi_lora_32_V3` : Compiler le nœud.
- `ollama run tinyllama` : Lancer l'IA locale.

## 🐛 Debugging Post-Apocalyptique

Si votre code plante :
1. **Ne criez pas.** Ça attire les rôdeurs.
2. Vérifiez les logs série (`pio device monitor`).
3. Si l'IA répond en binaire, redémarrez le Raspberry Pi.
4. Si l'IA vous demande "Êtes-vous vivant ?", débranchez tout et courez.

// v3 foufou: zombies + AI = win !