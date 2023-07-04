/*
 * Sam Polyakov
 * Graph.java
 * 05/02/2023
 * Project 8
 * CS231 B
 * 
 * Purpose: creates a graph
 */

import java.util.ArrayList;
import java.util.Comparator;

public class Graph {
    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;

    public Graph(){
        this(0);
    }

    public Graph(int n){
        this(n, 0.0);
    }

    public Graph(int n, double probability){
        vertices = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        for (int i = 0; i < n; i++){
            addVertex();
            for (int j = 0; j < i; j++) {
                if (Math.random() < probability){
                    addEdge(vertices.get(i), vertices.get(j), 1.0);
                }
            }
        }
    }

    public int size(){
        //returns the number of vertices
        return vertices.size();
    }

    public ArrayList<Vertex> getVertices(){
        // returns an Iterable object that iterates over the vertices
        return vertices;
    }

    public ArrayList<Edge> getEdges(){
        // returns an Iterable object that iterates over the edges.
        return edges;
    }

    public Vertex addVertex(){
        //Creates a new Vertex, adds it to the Graph, and returns it.
        Vertex vertex = new Vertex();
        vertices.add(vertex);
        return vertex;
    }

    public Edge addEdge(Vertex u, Vertex v, double distance){
        // Creates a new Edge, adds it to the Graph
        Edge edge = new Edge(u, v, distance);
        edges.add(edge);
        u.addEdge(edge);
        v.addEdge(edge);
        return edge;
    }

    public Edge getEdge(Vertex u, Vertex v){
        //returns the Edge between u and v if such an Edge exists, otherwise returns null.
        return u.getEdgeTo(v);
    }

    public boolean remove(Vertex vertex){
        //if the given Vertex vertex is in this Graph, removes it and returns true. Otherwise, returns false.
        if(vertices.contains(vertex)){
            edges = vertex.edges;
            for(Edge edge : edges){
                vertex.removeEdge(edge);
            }
            vertices.remove(vertex);
            return true;
        } return false;
    }

    public boolean remove(Edge edge){
        // if the given Edge is in the Graph, removes it and returns true. Otherwise, returns false.
        if(edges.contains(edge)){
            Vertex[] vertexs = edge.vertices();
            vertexs[0].removeEdge(edge);
            edges.remove(edge);
            return true;
        } return false;
    }

    public HashMap<Vertex, Double> distanceFrom(Vertex source){
        // uses Djikstra's algorithm to compute the minimal distance in this Graph from the given Vertex source to all other Vertices in the graph. 
        // The HashMap returned maps each Vertex to its distance from the source.
        HashMap<Vertex, Double> distances = new HashMap<>();
        for(Vertex vertex : vertices){
            if(vertex.equals(source)){
                distances.put(vertex, 0.0);
            }
            else{
                distances.put(vertex, Double.POSITIVE_INFINITY);
            }
        }

        Comparator<Vertex> comparator = new Comparator<Vertex>() {
            public int compare(Vertex v1, Vertex v2){
                return(distances.get(v1)).compareTo(distances.get(v2));
            }
        };
        PriorityQueue<Vertex> priorityQueue = new Heap<>(comparator);


        for(Vertex vertex : vertices){
            priorityQueue.offer(vertex);
        }

        while(priorityQueue.size() > 0){
            Vertex next = priorityQueue.poll();
            for(Edge edge : next.incidentEdges()){
                Vertex neighbor = edge.other(next);
                double newDistance = distances.get(next) + edge.distance();
                if(newDistance < distances.get(neighbor)){
                    distances.put(neighbor, newDistance);
                    priorityQueue.updatePriority(next);
                }
            }
        } return distances;
    }
}
