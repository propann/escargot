package com.propann.escargot.ai;

import com.propann.escargot.optimizer.ConstraintNetwork;
import java.util.Random;

/**
 * Implémentation "Dummy" (Bouchon) de l'oracle.
 * <p>
 * Retourne des poids aléatoires. Utile pour tester le pipeline sans charger
 * de modèle lourd, ou comme baseline (random variable ordering).
 * </p>
 */
public class DummyHeuristicOracle implements HeuristicOracle {

    private final Random random = new Random();

    @Override
    public double[] predictVariableOrdering(ConstraintNetwork net) {
        int variableCount = net.getVariableCount();
        double[] weights = new double[variableCount];

        for (int i = 0; i < variableCount; i++) {
            // Génère un score aléatoire entre 0.0 et 1.0
            weights[i] = random.nextDouble();
        }
        return weights;
    }
}