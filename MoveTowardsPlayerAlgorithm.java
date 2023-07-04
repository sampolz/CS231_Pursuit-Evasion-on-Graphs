/*
 * Sam Polyakov
 * MoveTowardsPlayerAlgorithm.java
 * 05/02/2023
 * Project 8
 * CS231 B
 * 
 * Purpose: Movement algorithm for pursuer
 */

import java.util.ArrayList;

public class MoveTowardsPlayerAlgorithm extends AbstractPlayerAlgorithm{

    public MoveTowardsPlayerAlgorithm(Graph graph) {
        super(graph);
        this.graph = graph;
    }

    @Override
    public Vertex chooseStart() {
        // returns a Vertex for the player to start at and updates the current Vertex to that location.
        // Chooses the vertex with the minimum average edge length
        double minAverage = Double.POSITIVE_INFINITY;
        Vertex tempVertex = new Vertex();
        for(Vertex vertex : graph.getVertices()){
            ArrayList<Edge> edges = vertex.edges;
            double totalDistance = 0.0;
            for(Edge edge : edges){
                totalDistance += edge.distance();
            }
            double averageDistance = totalDistance/edges.size();
            if(averageDistance < minAverage){
                minAverage = averageDistance;
                tempVertex = vertex;
            }
        }
        setCurrentVertex(tempVertex);
        return tempVertex;
    }

    @Override
    public Vertex chooseStart(Vertex other) {
        // chooses the closest vertex to the other player to start at
        return other;
    }

    @Override
    public Vertex chooseNext(Vertex otherPlayer) {
        // moves towards the other player
        HashMap<Vertex, Double> distances = graph.distanceFrom(otherPlayer);
        Vertex cloesestVertex = new Vertex();
        Double closestDistance = Double.POSITIVE_INFINITY;

        for(Vertex vertex : curVertex.adjacentVertexs){
            if(distances.get(vertex) < closestDistance){
                closestDistance = distances.get(vertex);
                cloesestVertex = vertex;
            }
        }
        setCurrentVertex(cloesestVertex);
        return(cloesestVertex);
    }
    
}
