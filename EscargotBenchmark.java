package com.propann.escargot.benchmarks;

import com.propann.escargot.optimizer.DomainManager;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.roaringbitmap.RoaringBitmap;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Harnais de benchmark professionnel pour le projet Escargot.
 * <p>
 * Objectif : Prouver la supériorité de l'approche Bitset (RoaringBitmap)
 * sur les structures Java standard et mesurer l'overhead de Choco Solver.
 * </p>
 * Usage : Exécuter la méthode main() pour lancer le benchmark.
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1, warmups = 1)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
public class EscargotBenchmark {

    private DomainManager domainManager;
    
    // Données pour Scénario B (Classic)
    private Set<Integer> setA;
    private Set<Integer> setB;

    // Données pour Scénario C (Bitsets)
    private RoaringBitmap bitmapA;
    private RoaringBitmap bitmapB;

    // Taille des domaines pour le test
    @Param({"1000", "10000"})
    private int domainSize;

    @Setup(Level.Trial)
    public void setup() {
        domainManager = new DomainManager();
        
        // Initialisation des structures
        setA = new HashSet<>();
        setB = new HashSet<>();
        bitmapA = new RoaringBitmap();
        bitmapB = new RoaringBitmap();

        // Remplissage avec des données semi-aléatoires pour créer des intersections
        for (int i = 0; i < domainSize; i++) {
            setA.add(i);
            bitmapA.add(i);
            
            // On crée une intersection partielle (1 élément sur 2)
            if (i % 2 == 0) {
                setB.add(i);
                bitmapB.add(i);
            }
        }
    }

    /**
     * Scénario A : Choco Solver Standard.
     * Mesure le coût de la création de variables et d'une propagation basique.
     * C'est notre "Baseline" lourde.
     */
    @Benchmark
    public void scenarioA_ChocoSolverStandard(Blackhole bh) {
        Model model = new Model("Bench");
        // Domaines énumérés [0, domainSize]
        IntVar v1 = model.intVar("v1", 0, domainSize, false);
        IntVar v2 = model.intVar("v2", 0, domainSize, false);
        
        // Contrainte d'égalité (force l'intersection des domaines)
        model.arithm(v1, "=", v2).post();
        
        try {
            // Propagation initiale
            model.getSolver().propagate();
        } catch (Exception e) {
            // Ignore contradiction
        }
        bh.consume(model);
    }

    /**
     * Scénario B : PropAnn avec heuristiques actuelles (Set<Integer>).
     * Représente l'implémentation naïve.
     */
    @Benchmark
    public void scenarioB_PropAnnClassic(Blackhole bh) {
        Set<Integer> result = domainManager.intersectionClassic(setA, setB);
        bh.consume(result);
    }

    /**
     * Scénario C : PropAnn avec Bitsets (Nouvelle implémentation).
     * Doit montrer un gain massif en ops/s et une réduction du GC.
     */
    @Benchmark
    public void scenarioC_PropAnnBitsets(Blackhole bh) {
        RoaringBitmap result = domainManager.intersectionOptimized(bitmapA, bitmapB);
        bh.consume(result);
    }

    // Point d'entrée pour lancer le benchmark directement
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.runner.Runner runner = new org.openjdk.jmh.runner.Runner(new org.openjdk.jmh.runner.options.OptionsBuilder()
                .include(EscargotBenchmark.class.getSimpleName())
                .build());
        runner.run();
    }
}