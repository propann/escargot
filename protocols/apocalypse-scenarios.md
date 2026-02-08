# Scénarios d'Utilisation Post-Apocalyptique

## 1. Le Black-out Total
- **Situation** : Plus d'internet, plus de grille électrique.
- **Escargot** : Les nœuds sur batterie continuent de relayer les messages locaux.
- **Mining** : Les shares DUCO sont stockés en file d'attente (queue) sur l'ESP32. Dès qu'un nœud "Bridge" est croisé (qui a une connexion sat ou 4G temporaire), les shares sont uploadés en bulk.

## 2. La Horde (Congestion Réseau)
- **Situation** : Tout le monde crie à l'aide sur la fréquence 868MHz.
- **Escargot** : Le protocole limite les sauts (Hop=3) et priorise les messages signés par des clés de confiance (Web of Trust).
- **Anti-Spam** : Pour poster, il faut fournir un petit Proof-of-Work (calculé par l'Arduino). Ça empêche un bot zombie de flooder le réseau.

## 3. Capture d'un Nœud
- **Situation** : Des pillards volent un nœud.
- **Sécurité** : Les clés privées sont dans l'Arduino. Si l'ESP est dumpé, pas de clés. L'Arduino peut être configuré pour s'effacer (Wipe) si une séquence de "Panic PIN" est détectée.