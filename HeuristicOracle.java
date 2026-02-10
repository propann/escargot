package com.propann.escargot.ai;

import com.propann.escargot.core.SmartDomain;

/**
 * L'Oracle est le composant qui guide la recherche.
 * Il peut être simple (aléatoire) ou complexe (Réseau de Neurones).
 */
public interface HeuristicOracle {

    /**
     * Prédit quelle variable est la plus susceptible de causer un échec (Fail-First)
     * ou de mener à une solution.
     * @param variables L'état actuel des variables (ex: IDs ou domaines)
     * @return Un tableau de scores (plus le score est haut, plus la variable est prioritaire)
     */
    double[] predictVariableOrdering(SmartDomain[] variables);
    
    /**
     * Charge le modèle en mémoire (si nécessaire).
     */
    void loadModel(String modelPath);
}