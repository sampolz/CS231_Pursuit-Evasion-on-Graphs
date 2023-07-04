/*
 * Sam Polyakov
 * AbstractPlayerAlgorithm.java
 * 05/02/2023
 * Project 8
 * CS231 B
 * 
 * Purpose: Abstract class for movement algorithms
 */

public abstract class AbstractPlayerAlgorithm {
    public Graph graph;
    public Vertex curVertex;

    public AbstractPlayerAlgorithm(Graph graph){
        // Constructs the necessary fields of the class.
        graph = new Graph();
        curVertex = new Vertex();
    }

    public Graph getGraph(){
        //returns the underyling Graph.
        return graph;
    }

    public Vertex getCurrentVertex(){
        //returns the current Vertex of the player.
        return curVertex;
    }

    public void setCurrentVertex(Vertex vertex){
        // updates the current Vertex of the player to be the given Vertex vertex.
        curVertex = vertex;
    }

    public abstract Vertex chooseStart();
    // returns a Vertex for the player to start at and updates the current Vertex to that location.

    public abstract Vertex chooseStart(Vertex other);
    //returns a Vertex for the player to start at based on where the other player chose to start. 
    //Updates the current Vertex to the chosen location.

    public abstract Vertex chooseNext(Vertex otherPlayer);
    //returns a Vertex for the player to move to based on where the other player currently is. 
    //Updates the current Vertex to the given next location.

}
