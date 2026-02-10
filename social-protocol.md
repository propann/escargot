# 📜 Protocoles de la Horde (V3)

## Structure des Messages
Format : `TYPE|PAYLOAD|SIGNATURE`

### Types
- **POST** : Message public (Gossip).
- **DM** : Message privé chiffré.
- **TX** : Transaction DUCO (`TX|TO:UserB|AMT:10.5|SIG:...`).

## Économie Zombie (DUCO)
- Chaque message coûte du "Mana" (DUCO).
- L'Arduino mine pour recharger le Mana.
- Pas de Mana = Pas de parole (Silence radio pour éviter le spam).

## Gossip Protocol
- Les nœuds relayent les messages "frais" (< 1h).
- TTL (Time To Live) : 3 sauts max.

## 🤝 Étiquette de Fin du Monde
1. **Pas de spoil** sur la saison 12 de The Walking Dead.
2. **Pas de TikTok**. La bande passante est précieuse (et on s'en fout de votre danse).
3. **Soyez bref**. "Zombies au nord" est mieux que "Alors voilà, je marchais tranquillement vers le nord quand soudain..."