/*
 * Sam Polyakov
 * MoveTowardsPlayerAlgorithm.java
 * 05/02/2023
 * Project 8
 * CS231 B
 * 
 * Purpose: Movement algorithm for random player
 */

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class RandomPlayer extends AbstractPlayerAlgorithm{
    Random rand = new Random();

    public RandomPlayer(Graph graph) {
        super(graph);
    }

    @Override
    public Vertex chooseStart() {
        // returns a random Vertex for the player to start at and updates the current Vertex to that location.
        ArrayList<Vertex> vertices = graph.getVertices();
        int verticesSize = vertices.size();
        Vertex randVertex = vertices.get(rand.nextInt(verticesSize));

        setCurrentVertex(randVertex);

        return randVertex;
        
    }

    @Override
    public Vertex chooseStart(Vertex other) {
        //returns a Vertex for the player to start at based on where the other player chose to start. 
        //Updates the current Vertex to the chosen location.
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
        ArrayList<Vertex> neighbors = curVertex.adjacentVertexs;
        int randVert = rand.nextInt(neighbors.size());
        Vertex next = neighbors.get(randVert);
        if(next == otherPlayer){
            next = neighbors.get(randVert + 1);
        }
        setCurrentVertex(next);
        return next;
    }
    
}
