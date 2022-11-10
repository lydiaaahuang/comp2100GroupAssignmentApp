package com.example.tutorapp.BackEnd;

import java.util.ArrayList;


public abstract class EmptyTree<T extends Comparable<T>> extends Tree<T> {
    @Override
    public abstract Tree<T> insert(T element, ArrayList<T> extra);

    @Override
    public T min() {
        // No minimum.
        return null;
    }

    @Override
    public T max() {
        // No maximum.
        return null;
    }

    @Override
    public Tree<T> find(T element) {
        // Was unable to find the item. Hence, return null.
        return null;
    }

    @Override
    public int getHeight() {
        /*
         return -1 as this is a leaf node.
         -1 instead of 0 as this is inline with our definition of height as 'the number of edges between
             the current node and the leaf node'. Furthermore, returning 0 will not cause rotations where they
             should occur.
         */
        return -1;
    }

    @Override
    public String toString() {
        return "{}";
    }
}
