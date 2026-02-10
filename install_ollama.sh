#!/bin/bash

# 🐌 ESCARGOT - INSTALLATION IA DE SURVIE
# Ce script installe le cerveau local (Ollama) et le modèle léger (TinyLlama).

echo "🐌 [INIT] Démarrage de l'installation du Cerveau..."

# 1. Installation d'Ollama
if ! command -v ollama &> /dev/null; then
    echo "🧠 [INSTALL] Ollama non détecté. Téléchargement..."
    curl -fsSL https://ollama.com/install.sh | sh
else
    echo "🧠 [CHECK] Ollama est déjà installé."
fi

# 2. Démarrage du service
# On tente de démarrer le serveur en arrière-plan si ce n'est pas déjà fait via systemd
if ! pgrep -x "ollama" > /dev/null; then
    echo "🔌 [START] Démarrage du serveur Ollama..."
    # Lancement en background, redirige les logs pour ne pas polluer
    nohup ollama serve > ollama.log 2>&1 &
    # On laisse le temps au serveur de s'initialiser
    echo "⏳ [WAIT] Attente de 10 secondes pour l'initialisation..."
    sleep 10
else
    echo "🔌 [CHECK] Le serveur Ollama tourne déjà."
fi

# 3. Téléchargement du modèle TinyLlama
echo "📥 [DOWNLOAD] Récupération du modèle 'tinyllama'..."
ollama pull tinyllama

echo "✅ [SUCCESS] Le Cerveau est prêt. L'IA peut maintenant juger vos messages de survie."