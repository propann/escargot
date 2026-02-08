# Scénarios Apocalypse

## Tableau des scénarios
| Scénario | Objectif | Nœuds | Résultat attendu |
|---|---|---:|---|
| Black-out Paris | Mesh sans Internet | 5 | Posts relayed, offline OK |
| Horde 10km | Portée LoRa | 8 | Gossip stable 10 km |
| EMP | Reprise après reset | 6 | Auto-rejoin mesh |
| Mine DUCO offline | Reward sans relais | 4 | Shares stockées localement |

## Scénario: Black-out Paris
1. Déployer 5 nœuds LoRa (rayon 3 km).
2. Couper toute connectivité externe.
3. Envoyer 10 posts `#SafeZone`.
4. Vérifier propagation gossip et stockage local.

## Scénario: Horde 10km
1. Placer 8 nœuds en ligne (1.5 km d’écart).
2. Envoyer post `#HordeSeine`.
3. Mesurer délai jusqu’au dernier nœud.

## Scénario: EMP
1. Déployer 6 nœuds.
2. Simuler redémarrage brutal (power cycle).
3. Vérifier rejoin mesh et replay des posts.

## Scénario: Mine DUCO offline
1. Déployer 4 nœuds, aucun relais Internet.
2. Envoyer un post `#Ravitaillement`.
3. Vérifier qu’un `MINE_SHARE` est créé localement.
4. Activer un relais et vérifier sync des shares.
