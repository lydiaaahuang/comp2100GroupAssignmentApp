package com.example.tutorapp.BackEnd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> implements Serializable {

    public AVLTree(T value, ArrayList<T> extra) {
        super(value, extra);
        // Set left and right children to be of EmptyAVL
        this.leftNode = new EmptyAVL<>();
        this.rightNode = new EmptyAVL<>();
    }

    /**
     * Constructor for AVL Tree
     * @param value the main value of the node used for comparisons (e.g. tutor name)
     * @param extra arraylist holding extra information related to the value (e.g. tutor name, fee, courses)
     * @param leftNode left node of tree
     * @param rightNode right node of tree
     */
    public AVLTree(T value, ArrayList<T> extra, Tree<T> leftNode, Tree<T> rightNode) {
        super(value, extra, leftNode, rightNode);
    }

    /**
     * @return balance factor of the current node obtained by subtracting height of left subtree and
     * right subtree
     */
    public int getBalanceFactor() {
        return leftNode.getHeight() - rightNode.getHeight();
    }

    @Override
    public AVLTree<T> insert(T element, ArrayList<T> extra) {

        // Ensure input is not null.
        if (element == null)
            throw new IllegalArgumentException("Input cannot be null");

        //Initialise new AVLTree
        AVLTree<T> avlTree;

        if (element.compareTo(value) > 0) {
            //Insert in right sub tree
            avlTree = new AVLTree<>(value, this.extra, leftNode, rightNode.insert(element, extra));
        } else if (element.compareTo(value) < 0) {
            //Insert in left subtree
            avlTree = new AVLTree<>(value, this.extra, leftNode.insert(element, extra), rightNode);
        } else {
            //If same (e.g. same course), then extend the extra info (e.g. other tutors teaching that course)
            this.extra.addAll(extra);
            //No extra node added, so tree must be already balanced; hence return directly.
            return this;

            //Implementation 2:

            //If same, add to left node.
            //avlTree = new AVLTree<>(value, this.extra, leftNode.insert(element, extra), rightNode);
        }

        //(Self) Balance tree after insertion
        if (avlTree.getBalanceFactor() < -1) {
            if (element.compareTo(avlTree.rightNode.value) < 0) {
                AVLTree<T> right = (AVLTree<T>) avlTree.rightNode;
                right = right.rightRotate();
                avlTree.rightNode = right;
            }
            avlTree = avlTree.leftRotate();
        }
        else if (avlTree.getBalanceFactor() > 1) {
            if (element.compareTo(avlTree.leftNode.value) > 0) {
                AVLTree<T> left = (AVLTree<T>)  avlTree.leftNode;
                left = left.leftRotate();
                avlTree.leftNode = left;
            }
            avlTree = avlTree.rightRotate();
        }

        return avlTree; // Change to return something different
    }

    /**
     * This function checks if the input string, 'name' is partially in a value of the nodes of the
     * AVL Tree or if it is equal to a node in the AVL Tree. It checks the relevant subtrees for all
     * occurrences of 'name'.
     * @param name input string to find in tree
     * @return hashmap containing the value and extra information of all nodes with values
     * containing or equal to 'name'
     */
    public HashMap<T, ArrayList<T>> myFind(String name) {
        HashMap<T, ArrayList<T>> moreInfo = new HashMap<>();

        //Ensure input is not null
        if (name == null) throw new IllegalArgumentException("Input cannot be null");

        //Check if 'name' is contained in the value of the node, but it is not equal to the value
        if (value.toString().toLowerCase().contains(name.toLowerCase()) &&
                !(name.toLowerCase().compareTo(value.toString().toLowerCase()) == 0)) {
            //all.add(value.toString());
            moreInfo.put(this.value, this.extra);
            if (!(rightNode instanceof AVLTree.EmptyAVL)) {
                AVLTree<T> newRight = (AVLTree<T>) this.rightNode;
                moreInfo.putAll(newRight.myFind(name));
            }
            if (!(leftNode instanceof AVLTree.EmptyAVL)) {
                moreInfo.putAll(((AVLTree<T>) leftNode).myFind(name));
            }
        }

        //check if 'name' is equal to the value of the node
        if (name.toLowerCase().compareTo(value.toString().toLowerCase()) == 0) {
            moreInfo.put(this.value, this.extra);
            //Recurse through left and right subtrees for further occurrences of 'name'
            if (!(rightNode instanceof AVLTree.EmptyAVL)) {
                AVLTree<T> newRight = (AVLTree<T>) this.rightNode;
                moreInfo.putAll(newRight.myFind(name));
            }
            if (!(leftNode instanceof AVLTree.EmptyAVL)) {
                moreInfo.putAll(((AVLTree<T>) leftNode).myFind(name));
            }
            //Look through leftnode if 'name' is less than value of current node
        } else if (name.toLowerCase().compareTo(value.toString().toLowerCase()) < 0) {
            if (!(leftNode instanceof AVLTree.EmptyAVL)) {
                moreInfo.putAll(((AVLTree<T>) leftNode).myFind(name));
            }
        } else {
            //Look through rightnode if 'name' is greater than value of current node
            if (!(rightNode instanceof AVLTree.EmptyAVL)) {
                moreInfo.putAll(((AVLTree<T>) rightNode).myFind(name));
            }
        }
        return moreInfo;
    }

    /**
     * Similar functionality to myFind(String name) but does not check for partial queries. This
     * method only checks for all occurrences of exact equality and will be used for searching for
     * fee like input in the implementation.
     * @param name = input string to check if it occurs in the tree
     * @return the first occurrence of input name
     */
    public HashMap<T, ArrayList<T>> findFee(String name) {
        HashMap<T, ArrayList<T>> moreInfo = new HashMap<>();

        //Ensure input is not null
        if (name == null) throw new IllegalArgumentException("Input cannot be null");

        //check if 'name' is equal to the value of the node
        if (name.compareTo(value.toString()) == 0) {
            moreInfo.put(this.value, this.extra);
            //Recurse through left and right subtrees for further occurrences of 'name'
            if (!(rightNode instanceof AVLTree.EmptyAVL)) {
                AVLTree<T> newRight = (AVLTree<T>) this.rightNode;
                moreInfo.putAll(newRight.findFee(name));
            }
            if (!(leftNode instanceof AVLTree.EmptyAVL)) {
                moreInfo.putAll(((AVLTree<T>) leftNode).findFee(name));
            }
            //Look through leftnode if 'name' is less than value of current node
        } else if (name.toLowerCase().compareTo(value.toString().toLowerCase()) < 0) {
            if (!(leftNode instanceof AVLTree.EmptyAVL)) {
                moreInfo.putAll(((AVLTree<T>) leftNode).findFee(name));
            }
        } else {
            moreInfo.put(this.value, this.extra);
            if (!(rightNode instanceof AVLTree.EmptyAVL)) {
                moreInfo.putAll(((AVLTree<T>) rightNode).findFee(name));
            }
        }
        return moreInfo;
    }

    /**
     * Conducts a left rotation on the current node.
     *
     * @return the new 'current' or 'top' node after rotation.
     */
    public AVLTree<T> leftRotate() {

        Tree<T> newParent = this.rightNode;
        //Tree<T> newRightOfCurrent = newParent.leftNode;

        this.rightNode = newParent.leftNode;
        newParent.leftNode = this;

        return (AVLTree<T>) newParent; // Change to return something different
    }

    /**
     * Conducts a right rotation on the current node.
     *
     * @return the new 'current' or 'top' node after rotation.
     */
    public AVLTree<T> rightRotate() {

        Tree<T> newParent = this.leftNode;
        this.leftNode = newParent.rightNode;
        newParent.rightNode = this;

        return (AVLTree<T>) newParent;
    }

    /**
     * Note that this is not within a file of its own... WHY?
     * The answer is: this is just a design decision. 'insert' here will return something specific
     * to the parent class inheriting Tree from BinarySearchTree. In this case an AVL tree.
     */
    public static class EmptyAVL<T extends Comparable<T>> extends EmptyTree<T> {
        @Override
        public Tree<T> insert(T element, ArrayList<T> extra) {
            // The creation of a new Tree, hence, return tree.
            return new AVLTree<T>(element, extra);
        }
    }
}

