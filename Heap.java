/*
 * Sam Polyakov
 * Heap.java
 * 05/02/2023
 * Project 8
 * CS231 B
 * 
 * Purpose: Creates a heap
 */

import java.util.Comparator;

public class Heap<T> implements PriorityQueue<T> {
    public static class Node<T>{
        Node<T> left, right, parent;
        T data;

        public Node(T data, Node<T> left, Node<T> right, Node<T> parent){
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public String toString(){
            return data.toString();
        }
    }

    private int size;
    private Node<T> root, last;
    private Comparator<T> comparator;


    public Heap(){
        // Heap constructor
        this(null, false);
    }

    public Heap(boolean maxHeap){
        // Heap constructor
        this(null, maxHeap);
    }

    public Heap(Comparator<T> comparator){
        // Heap constructor
        this(comparator, false);
    }

    public Heap(Comparator<T> comparator, boolean maxHeap){
        // Heap constructor
        if(comparator != null){
            this.comparator = comparator;
        }
        else{
            this.comparator = new Comparator<T>(){
                public int compare(T o1, T o2){
                    return((Comparable<T>) o1).compareTo(o2);
                }
            };
        } if(maxHeap == true){
            this.comparator = new Comparator<T>() {
                public int compare(T o1, T o2){
                    return((Comparable<T>) o2).compareTo(o1);
                }
            };
        }
    }


    private void bubbleUp(Node<T> curNode){
        ////Moves a node upwards comparing order of priority and swaping objects of lower priority with high priority objects
        if(curNode == root || curNode == null || curNode.parent == null){
            return;
        }

        T myData = curNode.data;
        T parentData = curNode.parent.data;
        if(comparator.compare(myData, parentData) < 0){
            swap(curNode,curNode.parent);
            bubbleUp(curNode.parent);
        }
    }

    private void swap(Node<T> node1, Node<T> node2){
        // swaps 2 nodes
        T temp = node1.data;
        node1.data = node2.data;
        node2.data = temp;
    }


    private void bubbleDown(Node<T> curNode){
        //Moves a node downwards comparing order of priority and swaping objects of lower priority with high priority objects
        if (curNode.left == null){
            return;
        } else if (curNode.right == null){
            if(comparator.compare(curNode.left.data, curNode.data) < 0){
                swap(curNode, curNode.left);
                bubbleDown(curNode.left);
            }
        }
        else{
            if(comparator.compare(curNode.left.data, curNode.right.data) < 0){
                if (comparator.compare(curNode.left.data, curNode.data) < 0){
                    swap(curNode, curNode.left);
                    bubbleDown(curNode.left);
                }
            } else {
                if (comparator.compare(curNode.right.data, curNode.data) < 0){
                swap(curNode, curNode.right);
                bubbleDown(curNode.right);
                }
            }
       }
    }

    

    @Override
    public int size() {
        //returns the number of items in the Heap.
        return size;
    }

    @Override
    public T peek() {
        //returns the item of highest priority in the Heap. This should be the item stored in the root.
        return root.data;
    }

    @Override
    public void offer(T item) {
        //adds an item to the heap
        if (size == 0) {
            root = new Node<T>(item, null, null, null);
            last = root;
        } else if (size % 2 == 0) {
            if (last.parent != null) {
                last.parent.right = new Node<T>(item, null, null, last.parent);
                last = last.parent.right;
            }
        } else {
            Node<T> curNode = last;
            System.out.println(curNode.parent);
            System.out.println("A: " + curNode);
            while (curNode.parent != null && curNode.equals(curNode.parent.right)) {
                curNode = curNode.parent;
            }
            System.out.println("B: " + curNode);
            if (curNode.parent != null) {
                curNode = curNode.parent.right;
            }
            System.out.println("C: " + curNode);

            while (curNode.left != null) {
                curNode = curNode.left;
            }

            curNode.left = new Node<T>(item, null, null, curNode);
            last = curNode.left;
        }
        size++;
        bubbleUp(last);
    }

    public String toString(){
        //Returns a String that represents the BSTMap accurately depicting the levels
        if(size == 0){
            return "Empty Tree";
        }
        else{
            return toString(root,0,"root");
        }
    }

    private String toString(Node<T> curNode, int depth, String direction) {
        //Returns a String that represents the BSTMap accurately depicting the levels
        if (curNode == null) {
            return "";
        }
    
        String myself = curNode.toString();

        String right = toString(curNode.right, depth + 1, "right");
        String left = toString(curNode.left, depth + 1, "left");
        // String right = toString(curNode.right, depth + 1, "right");
    
        String result = "";
        if (!right.isEmpty()) {
            result += right + '\n';
        }
        result += "  ".repeat(depth) + direction + ": " + myself;
        if (!left.isEmpty()) {
            result += '\n' + left;
        }
        return result;
    }

    

    public T poll() {
        // removes the item of highest priority from the heap
        if (size == 0) {
            return null;
        }
        
        T removed = root.data;
    
        if (size == 1) {
            root = null;
            last = null;
            size--;
            return removed;
        }
    
        if (size % 2 != 0) {
            root.data = last.data;
            last.parent.right = null;
            last = last.parent.left;
        } else {
            Node<T> curNode = last;
            root.data = last.data;
    
            if (size == 2) {
                last = root;
                root.left = null;
            } else {
                while (curNode != root && curNode.equals(curNode.parent.left)) {
                    curNode = curNode.parent;
                }
    
                if (curNode != root) {
                    curNode = curNode.parent.left;
                }
    
                while (curNode.right != null) {
                    curNode = curNode.right;
                }
                last.parent.left = null;
                last = curNode;
            }
        }
        size--;
        bubbleDown(root);
        return removed;
    }
    
    

    @Override
    public void updatePriority(T item) {
        // updates the priority of a given node
        Node<T> targetNode = findNode(root, item);
    
        if (targetNode != null) {
            if (targetNode.parent != null && comparator.compare(item, targetNode.parent.data) < 0) {
                bubbleUp(targetNode);
            } else {
                bubbleDown(targetNode);
            }
        }
    }
    
    private Node<T> findNode(Node<T> curNode, T item) {
        // finds a node
        if (curNode == null) {
            return null;
        }
    
        if (curNode.data.equals(item)) {
            return curNode;
        }
    
        Node<T> leftResult = findNode(curNode.left, item);
        if (leftResult != null) {
            return leftResult;
        }
    
        return findNode(curNode.right, item);
    }
    
}
