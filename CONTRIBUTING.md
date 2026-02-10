# Guide de Contribution du Bunker

Halte là, Voyageur.

Vous voulez modifier le code source d'**Escargot** ? Bienvenue. Mais ici, on a des règles. On ne gaspille pas les cycles CPU et on ne laisse pas traîner de fuites de mémoire.

## 1. La Loi de la Performance
Toute modification qui ralentit le solveur de plus de 1% sans justification majeure sera rejetée, et son auteur sera banni dans la Zone Irradiée (le dossier `tmp`).

* Utilisez **JMH** pour prouver que votre code est rapide.
* "Ça marche sur ma machine" n'est pas une excuse valide. Votre machine n'existera peut-être plus demain.

## 2. Hygiène du Code
* **Pas de `System.out.println` :** Utilisez le Logger. Le papier pour les logs est précieux.
* **Java 21 Strict :** Utilisez `var`, les `Records`, et les `Switch Expressions`. Soyez modernes, même dans la boue.
* **Commentaires :** Expliquez le *pourquoi*, pas le *comment*. Si votre logique est tordue, dites-nous quelle substance vous aviez consommée en l'écrivant.

## 3. Pull Requests (Les Ravitaillements)
1.  **Forkez** le projet.
2.  Créez une branche : `feature/turbo-boost` ou `fix/radiation-leak`.
3.  Poussez votre code.
4.  Ouvrez une PR avec un titre épique. Les titres tièdes seront rationnés.

## 4. Tests
Si vous ajoutez une fonctionnalité sans test unitaire, c'est comme sortir sans compteur Geiger. C'est suicidaire.
Les tests doivent passer même si le réseau est coupé.

---
*"Codez comme si le dernier mainteneur était un psychopathe violent qui connaît votre adresse GPS."*
