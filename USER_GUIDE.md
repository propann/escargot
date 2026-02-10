# 💻 MANUEL DE L'OPÉRATEUR - Survivre Connecté

Vous n'êtes pas ingénieur ? Pas grave. Si vous savez lire ceci, vous pouvez opérer un nœud Escargot. (Si vous savez l'écrire, vous êtes déjà promu.)

## 1. Démarrage Rapide (Panic Mode)

La horde approche ? Faites ça :

1.  **Branchez** l'alimentation USB.
2.  **Attendez** que la LED verte du Raspberry Pi cesse de clignoter frénétiquement (environ 45s).
3.  **Connectez** votre téléphone au WiFi nommé `Escargot-Reseau-Secours`.
4.  **Ouvrez** votre navigateur et tapez : `http://escargot.local` (ou scannez le QR Code collé sur le boîtier). Oui, c'est plus simple que réparer l'internet.

## 2. L'Interface X-Mesh

C'est comme Twitter, mais sans les pubs et sans Elon.

*   **Le Fil (Feed) :** Les messages locaux et relayés. Si c'est écrit en rouge, c'est une alerte prioritaire.
*   **Composer :** Pour écrire. Soyez bref. Chaque caractère coûte de l'énergie et votre futur vous remerciera.
*   **La Carte (Map) :** Affiche les positions approximatives des autres nœuds. Ne l'utilisez pas pour tendre des embuscades, on est entre gentlemen ici (même en apocalyspe).

## 3. Le Salaire de la Peur (Minage)

Votre Escargot travaille pour vous.

*   **Badge "Pioche" ⛏️ :** Indique que l'Arduino mine du DUCO.
    *   *Vert :* Ça mine dur. Vous gagnez des crédits.
    *   *Orange :* En pause (batterie faible ou surchauffe).
    *   *Gris :* Mineur HS ou déconnecté.
*   **Solde DUCO :** Votre monnaie pour envoyer des messages prioritaires. Si vous êtes à zéro, vous ne pouvez qu'écouter.

## 4. En cas de Pépin (Troubleshooting)

Le système est robuste, mais pas immortel.

### La LED Rouge clignote (SOS)
*   **Lentement :** Batterie faible (< 20%). Coupez les services non essentiels (IA).
*   **Rapidement :** Erreur critique (Disque plein ou IA plantée). Redémarrez.
*   **Code Morse (... --- ...) :** L'IA a développé une conscience et demande de l'aide. (Je plaisante. Probablement.)

### "Réseau Indisponible"
*   Vérifiez l'antenne.
*   Montez sur un point haut. Les ondes LoRa n'aiment pas le béton armé, elles préfèrent l'air frais.

### L'IA rejette mes messages
*   Arrêtez d'écrire comme un zombie ("Grrr... Argh...").
*   L'IA filtre le spam et le bruit. Soyez humain, écrivez des phrases complètes.

---
*Restez connectés. Restez vivants.*
