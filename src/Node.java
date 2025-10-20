/*******************************************************************
 ∗ @file: Node.java
 ∗ @description: This program implements a node class with a private fields, a constructor, and getters/setters
                    for data, left, and right.
 ∗ @author: Aidan Broadhead
 ∗ @date: October 21, 2025
 ********************************************************************/

public class Node<T extends Comparable<T>> {

    // private fields
    private T data;
    private Node<T> left;
    private Node<T> right;

    // constructor
    public Node(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    // getters
    public T getData() {
        return data;
    }

    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getRight() {
        return right;
    }

    // setters
    public void setData(T data) {
        this.data = data;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    // isLeaf method
    public boolean isLeaf() {
        return (getLeft() == null && getRight() == null);
    }

}
