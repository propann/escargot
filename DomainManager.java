package com.propann.escargot.optimizer;

import org.roaringbitmap.RoaringBitmap;
import java.util.HashSet;
import java.util.Set;

/**
 * Démonstration de l'optimisation des domaines de variables pour Java 21.
 */
public class DomainManager {

    // --- APPROCHE CLASSIQUE (Lente & Gourmande) ---
    // Utilisation de Set<Integer> (Boxing/Unboxing, pointeurs objets, Hash overhead)
    public Set<Integer> intersectionClassic(Set<Integer> domainA, Set<Integer> domainB) {
        Set<Integer> result = new HashSet<>(domainA);
        // O(min(N, M)) dans le meilleur des cas, mais avec un overhead constant élevé
        result.retainAll(domainB); 
        return result;
    }

    // --- APPROCHE MODERNE (RoaringBitmap) ---
    // Utilisation de Bitsets compressés.
    // Avantages :
    // 1. Cache CPU friendly (données contiguës).
    // 2. Opérations bitwise (AND/OR) ultra-rapides.
    // 3. Pas d'overhead d'objet Integer.
    public RoaringBitmap intersectionOptimized(RoaringBitmap domainA, RoaringBitmap domainB) {
        // RoaringBitmap.and() est souvent 10x à 100x plus rapide que HashSet.retainAll()
        // Il utilise des instructions SIMD si disponibles et évite les allocations inutiles.
        return RoaringBitmap.and(domainA, domainB);
    }

    public void demonstratePropagation() {
        // Scénario : Variable X peut valoir {1..10000}, Variable Y peut valoir {5000..15000}
        // Contrainte : X == Y (Intersection)

        // Setup RoaringBitmap
        RoaringBitmap domainX = new RoaringBitmap();
        domainX.add(1L, 10000L); // Range add est très efficace

        RoaringBitmap domainY = new RoaringBitmap();
        domainY.add(5000L, 15000L);

        long start = System.nanoTime();
        
        // Propagation de contrainte (Intersection)
        RoaringBitmap result = RoaringBitmap.and(domainX, domainY);
        
        long end = System.nanoTime();

        System.out.println("Intersection size: " + result.getCardinality()); // Devrait être 5000
        System.out.println("Temps d'exécution (ns): " + (end - start));
        
        // Avec Java 21, le JIT (Just-In-Time compiler) peut encore mieux optimiser 
        // ces appels grâce aux améliorations intrinsèques.
    }
    
    // Note sur l'API Vector (Incubator Java 21) :
    // RoaringBitmap utilise déjà des optimisations bas niveau. 
    // Si nous devions écrire notre propre moteur de contraintes sur des tableaux `int[]`,
    // nous utiliserions `jdk.incubator.vector.IntVector` pour effectuer des 
    // opérations AND/OR sur 8 entiers (256 bits) ou 16 entiers (512 bits) simultanément.
}