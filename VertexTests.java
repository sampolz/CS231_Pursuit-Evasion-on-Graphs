/*
 * Sam Polyakov
 * VertexTests.java
 * 05/02/2023
 * Project 8
 * CS231 B
 * 
 * Purpose: Creates tests for vertex
 */

public class VertexTests {
    public static void main(String[] args) {

        // case 1: testing getEdgeTo() with existing edge
        {
            // setup
            Vertex v1 = new Vertex();
            Vertex v2 = new Vertex();
            Edge e = new Edge(v1, v2, 2.0);

            // verify
            System.out.println(v1.getEdgeTo(v2) == e);

            // test
            assert v1.getEdgeTo(v2) == e : "Error in Vertex::getEdgeTo()";
        }

        // case 2: testing getEdgeTo() with non-existing edge
        {
            // setup
            Vertex v1 = new Vertex();
            Vertex v2 = new Vertex();

            // verify
            System.out.println(v1.getEdgeTo(v2) == null);

            // test
            assert v1.getEdgeTo(v2) == null : "Error in Vertex::getEdgeTo()";
        }

        // case 3: testing addEdge()
        {
            // setup
            Vertex v1 = new Vertex();
            Vertex v2 = new Vertex();
            Edge e = new Edge(v1, v2, 2.0);

            // test
            v1.addEdge(e);
            for (Vertex v : v1.adjacentVertexs){
                System.out.println(v == v2);
            }
            for (Edge e2 : v1.incidentEdges()){
                System.out.println(e2 == e);
            }

            // verify
            assert v1.adjacentVertices().iterator().next() == v2 : "Error in Vertex::addEdge()";
            assert v1.incidentEdges().iterator().next() == e : "Error in Vertex::addEdge()";
        }

        // case 4: testing removeEdge() with existing edge
        {
            // setup
            Vertex v1 = new Vertex();
            Vertex v2 = new Vertex();
            Edge e = new Edge(v1, v2, 2.0);
            v1.addEdge(e);

            // test
            boolean success = v1.removeEdge(e);
            System.out.println(success);

            // verify
            assert success : "Error in Vertex::removeEdge()";
        }
    }
}