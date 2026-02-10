package com.propann.escargot.benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import com.propann.escargot.core.SmartDomain; // Votre classe optimisée (Mission 1)

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput) // On mesure le nombre d'opérations par seconde
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1) // Nombre d'exécutions isolées
@Warmup(iterations = 3, time = 1) // On "chauffe" la JVM avant de mesurer
@Measurement(iterations = 5, time = 1) // Les vraies mesures
public class PropagationBenchmark {

    // --- DONNÉES DE TEST ---
    private Set<Integer> oldDomainA;
    private Set<Integer> oldDomainB;
    
    private SmartDomain smartDomainA;
    private SmartDomain smartDomainB;

    @Setup(Level.Trial)
    public void setup() {
        // Initialisation : On remplit deux domaines avec 10000 valeurs
        // On simule un cas typique de propagation (intersection)
        oldDomainA = new HashSet<>();
        oldDomainB = new HashSet<>();
        smartDomainA = new SmartDomain();
        smartDomainB = new SmartDomain();

        for (int i = 0; i < 10000; i++) {
            if (i % 2 == 0) { // Nombres pairs
                oldDomainA.add(i);
                smartDomainA.add(i);
            }
            if (i % 3 == 0) { // Multiples de 3
                oldDomainB.add(i);
                smartDomainB.add(i);
            }
        }
    }

    // --- LE MATCH : ANCIENNE MÉTHODE ---
    @Benchmark
    public void testLegacyIntersection(Blackhole bh) {
        // La méthode classique avec HashSet (LENTE)
        Set<Integer> intersection = new HashSet<>(oldDomainA);
        intersection.retainAll(oldDomainB);
        
        // Le Blackhole empêche la JVM de supprimer ce code (optimisation de code mort)
        bh.consume(intersection);
    }

    // --- LE MATCH : NOUVELLE MÉTHODE (ESCARGOT TURBO) ---
    @Benchmark
    public void testSmartIntersection(Blackhole bh) {
        // La méthode optimisée avec BitSets (RAPIDE)
        SmartDomain intersection = SmartDomain.intersection(smartDomainA, smartDomainB);
        
        bh.consume(intersection);
    }
}