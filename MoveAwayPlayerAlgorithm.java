/*
 * Sam Polyakov
 * MoveAwayPlayerAlgorithm.java
 * 05/02/2023
 * Project 8
 * CS231 B
 * 
 * Purpose: Movement algorithm for evader
 */

import java.util.ArrayList;

public class MoveAwayPlayerAlgorithm extends AbstractPlayerAlgorithm{

    public MoveAwayPlayerAlgorithm(Graph graph) {
        super(graph);
        this.graph = graph;
    }

    @Override
    public Vertex chooseStart() {
        // returns a Vertex for the player to start at and updates the current Vertex to that location.
        // Chooses the vertex with the maximum average edge length
        double maxAverage = Double.NEGATIVE_INFINITY;
        Vertex tempVertex = new Vertex();
        for(Vertex vertex : graph.getVertices()){
            ArrayList<Edge> edges = vertex.edges;
            double totalDistance = 0.0;
            for(Edge edge : edges){
                totalDistance += edge.distance();
            }
            double averageDistance = totalDistance/edges.size();
            if(averageDistance > maxAverage){
                maxAverage = averageDistance;
                tempVertex = vertex;
            }
        }
        setCurrentVertex(tempVertex);
        return tempVertex;
    }

    @Override
    public Vertex chooseStart(Vertex other) {
        //chooses the farthest vertex from the player to start at
        HashMap<Vertex, Double> distance = graph.distanceFrom(other);
        double furthestDst = 0.0;
        Vertex furthestVtx = new Vertex();
        for (MapSet.KeyValuePair<Vertex, Double> vertex : distance.entrySet()){
            if(vertex.getValue() > furthestDst){
                furthestDst = vertex.getValue();
                furthestVtx = vertex.getKey();
            }
        }
        setCurrentVertex(furthestVtx);
        return furthestVtx;
    }

    @Override
    public Vertex chooseNext(Vertex otherPlayer) {
        // moves away from the other player
        HashMap<Vertex, Double> distances = graph.distanceFrom(otherPlayer);
        Vertex farthestVertex = new Vertex();
        Double farthestDistance = Double.NEGATIVE_INFINITY;

        for(Vertex vertex : curVertex.adjacentVertexs){
            if(distances.get(vertex) > farthestDistance){
                farthestDistance = distances.get(vertex);
                farthestVertex = vertex;
            }
        }
        setCurrentVertex(farthestVertex);
        return(farthestVertex);
    }
    
}
