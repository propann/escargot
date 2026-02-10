package com.propann.escargot.core;

import org.roaringbitmap.RoaringBitmap;

/**
 * Wrapper intelligent autour des Bitsets pour l'IA.
 * Représente le domaine d'une variable.
 */
public class SmartDomain {
    private final RoaringBitmap bitmap;

    public SmartDomain() {
        this.bitmap = new RoaringBitmap();
    }

    public SmartDomain(RoaringBitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int size() {
        return bitmap.getCardinality();
    }

    public void add(int value) {
        bitmap.add(value);
    }

    public static SmartDomain intersection(SmartDomain a, SmartDomain b) {
        // RoaringBitmap.and() retourne un nouveau bitmap contenant l'intersection
        RoaringBitmap result = RoaringBitmap.and(a.bitmap, b.bitmap);
        return new SmartDomain(result);
    }
}