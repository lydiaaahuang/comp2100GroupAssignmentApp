package com.example.tutorapp.BackEnd;

import java.util.ArrayList;

/**
 * An AVL tree is actually an extension of a Binary Search Tree
 * with self balancing properties. Hence, our AVL trees will 'extend'
 * this Binary Search tree data structure.
 */
public class BinarySearchTree<T extends Comparable<T>> extends Tree<T> {

    public BinarySearchTree(T value, ArrayList<T> extra) {
        super(value, extra);
        this.leftNode = new EmptyBST<>();
        this.rightNode = new EmptyBST<>();
    }

    public BinarySearchTree(T value, ArrayList<T> extra, Tree<T> leftNode, Tree<T> rightNode) {
        super(value, extra, leftNode, rightNode);
    }

    @Override
    public T min() {
        return (leftNode instanceof EmptyTree) ? value : leftNode.min();
    }

    @Override
    public T max() {
        return (rightNode instanceof EmptyTree) ? value : rightNode.max();
    }

    @Override
    public Tree<T> find(T element) {

        // Ensure input is not null.
        if (element == null)
            throw new IllegalArgumentException("Input cannot be null");

        if (element.compareTo(value) == 0) {
            return this;
        } else if (element.compareTo(value) < 0) {
            return leftNode.find(element);
        } else {
            return rightNode.find(element);
        }
    }

    @Override
    public BinarySearchTree<T> insert(T element, ArrayList<T> extra) {
        // Ensure input is not null.
        if (element == null)
            throw new IllegalArgumentException("Input cannot be null");

        if (element.compareTo(value) > 0) {
            return new BinarySearchTree<>(value, this.extra, leftNode, rightNode.insert(element, extra));
        } else if (element.compareTo(value) < 0) {
            return new BinarySearchTree<>(value, this.extra, leftNode.insert(element, extra), rightNode);
        } else {
            return this;
        }
    }

    public static class EmptyBST<T extends Comparable<T>> extends EmptyTree<T> {
        @Override
        public Tree<T> insert(T element, ArrayList<T> extra) {
            // The creation of a new Tree, hence, return tree.
            return new BinarySearchTree<T>(element, extra);
        }
    }
}
