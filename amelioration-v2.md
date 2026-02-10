# 🛠️ Guide de Survie V2 (Dev)

## Environnement
- **VSCode** : L'arme de choix.
- **PlatformIO** : Pour flasher les microcontrôleurs sans internet.
- **Gemini** : L'IA qui remplace StackOverflow quand les serveurs sont HS.

## Prompts Gemini Utiles

1. **Optimisation Low-Power** :
   > "Optimise cette boucle C++ pour un ESP32 sur batterie. Utilise le deep sleep si possible. // Survive EMP: offline opti"

2. **Protocole Radio** :
   > "Génère une structure de paquet binaire compacte pour LoRa (max 200 bytes) incluant une signature ECDSA."

## Workflow
1. `git pull` (si internet dispo).
2. Codez dans `src/`.
3. `pio run -t upload`.