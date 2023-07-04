/*
 * Sam Polyakov
 * Vertex.java
 * 05/02/2023
 * Project 8
 * CS231 B
 * 
 * Purpose: Creates a vertex
 */

import java.util.ArrayList;

public class Vertex {
    int distanceToSource;
    ArrayList<Edge> edges;
    ArrayList<Vertex> adjacentVertexs;


    public Vertex(){
        // initializes a vertex
        distanceToSource = 0;
        edges = new ArrayList<Edge>();
        adjacentVertexs = new ArrayList<Vertex>();
    }

    public Edge getEdgeTo(Vertex vert){
        //Returns the Edge which connects this vertex and the given vertex
        for (Edge edge : edges){
            if (vert == edge.other(this)){
                return edge;
            }
        }  return null;
    }

    public void addEdge(Edge edge){
        //adds the specified Edge edge to the collection of Edges incident to this Vertex.
        edges.add(edge);
        Vertex otherVertex = edge.other(this);
        adjacentVertexs.add(otherVertex);
        otherVertex.adjacentVertexs.add(this);
    }

    public boolean removeEdge(Edge edge){
        //removes this Edge from the collection of Edges incident to this Vertex. 
        // Returns true if this Edge was connected to this Vertex, otherwise returns false.
        if (edges.contains(edge)) {
            edges.remove(edge);
            Vertex otherVertex = edge.other(this);
            adjacentVertexs.remove(otherVertex);
            otherVertex.adjacentVertexs.remove(this);
    
            // Remove the edge from the other vertex's edges list
            otherVertex.edges.remove(edge);
    
            return true;
        }
        return false;
    }

    public ArrayList<Vertex> adjacentVertices(){
        //returns a collection of all the Vertices adjacent to this Vertex 
        return adjacentVertexs;
    }

    public ArrayList<Edge> incidentEdges(){
        //returns a collection of all the Edges incident to this Vertex
        return edges;
    }


}
