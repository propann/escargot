package com.propann.escargot.graph;

/**
 * Représente une étape dans le raisonnement (une "Pensée").
 * Cela peut être une décision ("Je tente X=1") ou un constat d'échec ("Backtrack").
 */
public class SearchNode {
    private static int ID_COUNTER = 0;
    
    public final int id;
    public final String label;
    public final boolean isFail;
    public final int depth;

    public SearchNode(String label, int depth, boolean isFail) {
        this.id = ++ID_COUNTER;
        this.label = label;
        this.depth = depth;
        this.isFail = isFail;
    }

    @Override
    public String toString() {
        return label + " (d=" + depth + ")";
    }
    
    @Override
    public int hashCode() { return id; }
    
    @Override
    public boolean equals(Object o) {
        return (o instanceof SearchNode) && ((SearchNode) o).id == this.id;
    }
}