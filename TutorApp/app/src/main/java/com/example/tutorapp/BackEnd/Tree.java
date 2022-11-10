package com.example.tutorapp.BackEnd;

import java.util.ArrayList;

/**
 * The following interface defines required methods of any Tree.
 */
public abstract class Tree<T extends Comparable<T>> {

    public final T value;       // element stored in this node of the tree.
    public ArrayList<T> extra;
    public Tree<T> leftNode;    // less than the node.
    public Tree<T> rightNode;   // greater than the node.

    /**
     * Constructor to allow for empty trees
     */
    public Tree() {
        value = null;
        extra = null;
    }

    /**
     * Constructor for creating a new child node.
     * Note that the left and right nodes must be set by the subclass.
     *
     * @param value to set as this node's value.
     */
    public Tree(T value, ArrayList<T> extra) {
        // Ensure input is not null.
        if (value == null)
            throw new IllegalArgumentException("Input cannot be null");

        this.value = value;
        this.extra = extra;
    }

    /**
     * Constructor for creating a new node.
     * Note that this is what allows our implementation to be immutable.
     *
     * @param value     to set as this node's value.
     * @param leftNode  left child of current node.
     * @param rightNode right child of current node.
     */
    public Tree(T value, ArrayList<T> extra, Tree<T> leftNode, Tree<T> rightNode) {
        // Ensure inputs are not null.
        if (value == null || leftNode == null || rightNode == null)
            throw new IllegalArgumentException("Inputs cannot be null");

        this.value = value;
        this.extra = extra;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public abstract T min();                     // Finds the minimum.

    public abstract T max();                     // Finds the maximum.

    public abstract Tree<T> find(T element);     // Finds the element and returns the node.

    public abstract Tree<T> insert(T element, ArrayList<T> extra);   // Inserts the element and returns a new instance of itself with the new node.

    /**
     * Height of current node.
     * @return The maximum height of either children.
     */


    public int getHeight() {
        // Check whether leftNode or rightNode are EmptyTree
        int leftNodeHeight = leftNode instanceof EmptyTree ? 0 : 1 + leftNode.getHeight();
        int rightNodeHeight = rightNode instanceof EmptyTree ? 0 : 1 + rightNode.getHeight();
        return Math.max(leftNodeHeight, rightNodeHeight);
    }

    @Override
    public String toString() {
        return "{" +
                "value info = " + value + " " + extra +
                ", leftNode=" + leftNode +
                ", rightNode=" + rightNode +
                '}';
    }
}
