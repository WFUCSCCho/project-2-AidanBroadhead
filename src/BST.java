/*******************************************************************
 ∗ @file: BST.java
 ∗ @description: This program implements an iterator with next() and hasNext() methods as well as methods to
                    operate the BST such as insertNode(), searchNode(), removeNode(), print(), clear(), and size().
 ∗ @author: Aidan Broadhead
 ∗ @date: October 21, 2025
 ********************************************************************/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// BST class that implements comparable and iterable with methods to clear, insert, search, remove, and print in order
public class BST<T extends Comparable<T>> implements Iterable<T> {

    // private fields
    private Node<T> root;
    private int size = 0;

    // iterator class
    private class BSTIterator implements Iterator<T> {

        private Stack<Node<T>> stack = new Stack<>();

        // constructor
        public BSTIterator() {
            Node<T> curr = root;

            // push all left nodes
            while (curr != null) {
                stack.push(curr);
                curr = curr.getLeft();
            }
        }

        // returns the next smallest element in the stack
        @Override
        public T next() {
            //check if hasNext()
            if (!hasNext()) {
                throw new NoSuchElementException("Iter exceeded");
            }

            Node<T> node = stack.pop();
            T value = node.getData();

            Node<T> curr = node.getRight();

            // push all left nodes from the right child
            while(curr != null) {
                stack.push(curr);
                curr = curr.getLeft();
            }

            return value;

        }

        // check if stack is empty
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new BSTIterator();
    }

    // inserts the inputted value into the BST
    public Node<T> insertNode(T data) {
        Node<T> newNode = new Node<>(data);

        // if tree is empty, inserted node becomes the root
        if (root == null) {
            root = newNode;
            size = size + 1;
            return newNode;
        }

        Node<T> curr = root;
        Node<T> parent = null;

        // move down the tree according to the compare values to find insertion spot
        while (curr != null) {
            parent = curr;
            int compare = data.compareTo(curr.getData());
            if (compare < 0) {
                curr = curr.getLeft();
            } else if (compare > 0) {
                curr = curr.getRight();
            } else {
                return curr;
            }
        }

        // compare inputted data value to value of current node to figure out which side to insert new node
        if (data.compareTo(parent.getData()) < 0) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }

        size = size + 1;
        return newNode;

    }

    // searches BST for node with inputted value and returns that node or null
    public Node<T> searchNode(T data) {
        Node<T> curr = root;

        // moves down tree accordingly and returns node if found
        while (curr!= null) {
            int compare = data.compareTo(curr.getData());

            if (compare < 0) {
                curr = curr.getLeft();
            } else if (compare > 0) {
                curr = curr.getRight();
            } else {
                return curr;
            }
        }

        // returns null if not found
        return null;

    }

    // removes the node with the inputted value and adjusts the tree to account for the deletion
    public Node<T> removeNode(T data) {
        Node<T> curr = root;
        Node<T> parent = null;

        // move down tree until node with inputted value is found
        while (curr != null) {
            int compare = data.compareTo(curr.getData());
            if (compare < 0) {
                parent = curr;
                curr = curr.getLeft();
            } else if (compare > 0) {
                parent = curr;
                curr = curr.getRight();
            } else {
                // node found because compare value is 0
                break;
            }
        }

        // node not found
        if (curr == null) {
            return null;
        }

        // case 1: no children
        if (curr.getLeft() == null && curr.getRight() == null) {
            if (parent == null) {
                // node to remove is the root -> make root null (empty tree)
                root = null;

                // removes node by setting it to null
            } else if (parent.getLeft() == curr) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
            size = size - 1;
            return curr;
        }

        // case 2: only child
        // only a right child
        if (curr.getLeft() == null && curr.getRight() != null) {
            if (parent == null) {
                // sets the right child to new root
                root = curr.getRight();
            } else if (parent.getLeft() == curr) {
                // sets parent's left to the removed node's right child
                parent.setLeft(curr.getRight());
            } else {
                // sets the parent's right to the removed node's right child
                parent.setRight(curr.getRight());
            }
            size =  size -1;
            return curr;
        }

        // only a left child
        if (curr.getLeft() != null && curr.getRight() == null) {
            if (parent == null) {
                // sets the left child to new root
                root = curr.getLeft();
            } else if (parent.getLeft() == curr) {
                // sets parent's left to the removed node's left child
                parent.setLeft(curr.getLeft());
            } else {
                // sets parent's right to the removed node's left child
                parent.setRight(curr.getLeft());
            }
            size = size - 1;
            return curr;
        }

        // case 3: two children
        // create successor which will be the smallest node in right subtree
        Node<T> successorParent = curr;
        Node<T> successor = curr.getRight();

        // find leftmost value in right subtree
        while (successor.getLeft() != null) {
            successorParent = successor;
            successor = successor.getLeft();
        }

        // give the current node's data the data from the successor (replaces/removes the node with inputted value)
        curr.setData(successor.getData());

        // rearrange the tree after moving the successor node
        if (successorParent.getLeft() == successor) {
            successorParent.setLeft(successor.getRight());
        } else {
            successorParent.setRight(successor.getRight());
        }

        size = size - 1;
        return curr;

    }

    // prints BST contents in ascending order to the result.txt file
    public void print(String fileName) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            Iterator<T> iter = this.iterator();

            // prints elements with spaces in between
            while (iter.hasNext()) {
                T value = iter.next();
                bw.write(value.toString());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    // clears the tree by setting root to null
    public void clear() {
        root = null;
        size = 0;
    }

    // returns size of BST
    public int size() {
        return size;
    }

}
