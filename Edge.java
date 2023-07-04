/*
 * Sam Polyakov
 * Edge.java
 * 05/02/2023
 * Project 8
 * CS231 B
 * 
 * Purpose: creates edges for a graph
 */

public class Edge {
    Vertex endPoint1, endPoint2;
    Double distance;
    public Edge(Vertex u, Vertex v, double distance){
        endPoint1 = u;
        endPoint2 = v;
        this.distance = distance;
        u.addEdge(this);
        v.addEdge(this);
    }

    public double distance(){
        // returns distance
        return distance;
    }

    public Vertex other(Vertex vertex){
        // if vertex is one of the endpoints of this edge, returns the other end point. Otherwise returns null.
        if(vertex == endPoint1){
            return endPoint2;
        }
        if(vertex == endPoint2){
            return endPoint1;
        }
        else{
            return null;
        }
    }

    public Vertex[] vertices(){
        // returns an array of the two Vertices comprising this Edge
        Vertex[] vArray = {endPoint1, endPoint2};
        return vArray;
    }


}
