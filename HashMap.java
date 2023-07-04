/*
 * Sam Polyakov
 * HashMap.java
 * 04/16/2023
 * Project 6
 * CS231 B
 * 
 * Purpose: Creates a HashMap
 */

import java.util.ArrayList;
import java.util.LinkedList;

public class HashMap<K,V> implements MapSet<K,V>{
    private static class Node<K,V> extends KeyValuePair<K,V>{
        // Creates nodes
        Node<K,V> next;
        public Node(K key, V value, Node<K,V> next){
            super(key, value);
            this.next = next;
        }
    }
    
    private Node<K,V>[] buckets;
    private int size;
    private double maxLoadFactor;

    public HashMap(int initialCapacity, double loadFactor){
        // Constructor for HashMap
        size = 0;
        maxLoadFactor = loadFactor;
        buckets = (Node<K,V> []) new Node[initialCapacity];
    }

    public HashMap(int initialCapacity){
        // Constructor for HashMap
        this(initialCapacity,.75);
    }

    public HashMap(){
        this(16);
        // Constructor for HashMap
    }

    public int capacity(){
        //returns the length of the array
        return buckets.length;
    }

    private int hash(K key){
        //return the index of the underlying array in which the given key should be stored
        return Math.abs(key.hashCode() % capacity());
    }

    @Override
    public V put(K key, V value) {
        // Associates the specified value with the specified key in this map. 
        // If the map previously contained a mapping for the key, the old value is replaced. 
        // Does nothing if value is null. Returns the previous value associated with key, or null if there was no mapping for key.
        int index = hash(key);

        if(buckets[index] == null){
            buckets[index] = new Node<K,V>(key, value, null);
        }else{
            for(Node<K,V> curNode = buckets[index]; curNode != null; curNode = curNode.next){
                if(curNode.getKey().equals(key)){
                    V oldVal = curNode.getValue();
                    curNode.setValue(value);
                    return oldVal;
                }
            }
            buckets[index] = new Node<K,V>(key, value, buckets[index]);
        }

        size++;
        if(size > maxLoadFactor*capacity()){
            resize(capacity()*2);
        }

        return null;
    }

    @Override
    public boolean containsKey(K key) {
        //Returns true if this map contains a mapping for the specified key to a value.
        int index = hash(key);
        if(buckets[index] == null){
            return false;
        }
        for(Node<K,V> curNode = buckets[index]; curNode != null; curNode = curNode.next){
            if(curNode.getKey().equals(key)){
                return true;
            }
        } 
        return false;
    }


    @Override
    public V get(K key) {
        //Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
        if(!containsKey(key)){
            return null;
        } 
        else{
            int index = hash(key);
            for(Node<K,V> curNode = buckets[index]; curNode != null; curNode = curNode.next){
                if(curNode.getKey().equals(key)){
                    return curNode.getValue();
                }
            } 
        } return null;
    }


    @Override
    public V remove(K key) {
        //Removes the specified key (and its associated value) from this mapping and returns the value it was mapped to.
        if(!containsKey(key)){
            return null;
        } else{
            int index = hash(key);
            Node<K, V> prevNode = null;
            for (Node<K, V> curNode = buckets[index]; curNode != null; curNode = curNode.next) {
                if (curNode.getKey().equals(key)) {
                    V value = curNode.getValue();
                    if (prevNode == null) {
                        buckets[index] = curNode.next;
                    } else {
                        prevNode.next = curNode.next;
                    }
                    size--;
                    if (size < capacity() * maxLoadFactor){
                        resize(capacity() / 2);
                    }
                    return value;
                }
                prevNode = curNode;
            }
        }
        return null;
    }

    public ArrayList<K> keySet() {
        //Returns an ArrayList of all the keys in the map
        ArrayList<K> listOfKs = new ArrayList<K>();
        for (int i = 0; i < capacity(); i++) {
            for(Node<K,V> curNode = buckets[i]; curNode != null; curNode = curNode.next){
                K key = curNode.getKey();
                listOfKs.add(key);
            }
        }
        return listOfKs;
    }

    @Override
    public ArrayList<V> values() {
        //Returns an ArrayList of all the values in the map in the same order returned by keySet()
        ArrayList<K> listOfKs = keySet();
        ArrayList<V> listOfVs = new ArrayList<V>();
        for(K key : listOfKs){
            listOfVs.add(get(key));
        }
        return listOfVs;
    }

    @Override
    public ArrayList<MapSet.KeyValuePair<K, V>> entrySet() {
        //Returns an ArrayList of each KeyValuePair in the map in the same order as the keys as returned by keySet().
        ArrayList<K> listOfKs = keySet();
        ArrayList<MapSet.KeyValuePair<K, V>> listOfKeyValuePairs = new ArrayList<MapSet.KeyValuePair<K, V>>();
        for(K key : listOfKs){
            KeyValuePair<K, V> kvp = new KeyValuePair<K,V>(key, get(key));
            listOfKeyValuePairs.add(kvp);
        } return listOfKeyValuePairs;
    }

    @Override
    public int size() {
        //returns the size.
        return size;
    }

    @Override
    public void clear() {
        //resets fields to default values
        buckets = (Node<K,V> []) new Node[capacity()];
        size = 0;
    }


    public int maxDepth() {
        //returns the size of the biggest bucket (the number of items in the bucket with the most items)
        if (size == 0) {
            return 0;
        } else {
            int largestBucket = 0;
            for (int i = 0; i < capacity(); i++) {
                int count = 0;
                for (Node<K, V> curNode = buckets[i]; curNode != null; curNode = curNode.next) {
                    count++;
                }
                if (count > largestBucket) {
                    largestBucket = count;
                }
            }
            return largestBucket;
        }
    }


    public String toString(){
        //Returns a String that represents the HashMap.
        if(size == 0){
            return "Empty";
        }
        else{
            String string = "";
            for(K key : keySet()){
                string += "Key: " + key + " Value: " + get(key) +"\n";
            }
            return string;
        } 
    }

    private void resize(int newSize) {
        // resizes the array, updates all of the buckets
        ArrayList<MapSet.KeyValuePair<K, V>> oldBuckets = entrySet();
        buckets = (Node<K,V> []) new Node[newSize];
        size = 0;

        for (KeyValuePair<K, V> kvp : oldBuckets) {
            put(kvp.getKey(), kvp.getValue()); 
        }   
    }

    public static void main(String[] args) {
        // System.out.println("Hello: " + "Hello ".hashCode());
        // Integer x = 5;
        // System.out.println(x + ": " + x.hashCode());
        // System.out.println("a".hashCode());
        // LinkedList<Integer> list = new LinkedList<>();
        // for(int i = 0; i<5; i++){
        //     list.add(i);
        // } System.out.println(list + ": " + list.hashCode());

        // HashMap hashMap = new HashMap<>();

        // for(int i = 0; i<5; i++){
        //     hashMap.put(i, i+1);
        // }

        // System.out.println(hashMap.toString());
        
        // HashMap<Integer, String> hashMap = new HashMap<>();

        // hashMap.put(1, "One");
        // hashMap.put(2, "Two");
        // hashMap.put(3, "Three");
    
        // System.out.println("HashMap contents: \n" + hashMap.toString());
        // System.out.println(hashMap.maxDepth());
    
        // hashMap.remove(1);
        // System.out.println("HashMap contents after removing an element: \n" + hashMap.toString());
        // System.out.println(hashMap.maxDepth());

        // hashMap.clear();
        // System.out.println("HashMap contents after clearing: \n" + hashMap.toString());

        // System.out.println(hashMap.maxDepth());

        // HashMap<Integer, String> hashMap = new HashMap<>(4, 0.75);

        // hashMap.put(1, "One");
        // hashMap.put(2, "Two");
        // hashMap.put(3, "Three");

        // System.out.println("Before resize: \n" + hashMap.toString());
        // System.out.println("Capacity before resize: " + hashMap.capacity());

        // hashMap.put(4, "Four");
        // hashMap.put(5, "Five");
        // hashMap.put(6, "Six");
        // hashMap.put(7, "Seven");

        // System.out.println("\nAfter resize: \n" + hashMap.toString());
        // System.out.println("Capacity after resize: " + hashMap.capacity());

        // System.out.println("Size: " + hashMap.size());

        // hashMap.remove(4);

        // System.out.println("\nAfter remove: \n" + hashMap.toString());
        // System.out.println("Size: " + hashMap.size());

        // hashMap.remove(7);
        // hashMap.remove(6);
        // hashMap.remove(5);

        // System.out.println("\nAfter remove: \n" + hashMap.toString());
        // System.out.println("Capacity after resize: " + hashMap.capacity());

        // System.out.println("Size: " + hashMap.size());

        // hashMap.clear();

        // System.out.println("\nAfter clear: \n" + hashMap.toString());
        // System.out.println("Size: " + hashMap.size());
        HashMap<Integer, String> hashMap = new HashMap<>(4, 0.75);

        hashMap.put(1, "One");
        hashMap.put(2, "Two");
        hashMap.put(3, "Three");

        System.out.println("Before resize: \n" + hashMap.toString());
        System.out.println("Capacity before resize: " + hashMap.capacity());

        System.out.println("Size: " + hashMap.size());
        System.out.println();

        hashMap.put(4, "Four");
        hashMap.put(5, "Five");
        hashMap.put(6, "Six");
        hashMap.put(7, "Seven");

        System.out.println("\nAfter resize: \n" + hashMap.toString());
        System.out.println("Capacity after resize: " + hashMap.capacity());

        System.out.println("Size: " + hashMap.size());

        //Test Remove
        hashMap.remove(6);
        System.out.println("Capacity after removal: " + hashMap.capacity());

        System.out.println("Size after removal: " + hashMap.size());


        //Test Clear
        hashMap.clear();
        
        System.out.println("\nAfter clear: \n" + hashMap.toString());
        System.out.println("Size: " + hashMap.size());
    }

}
