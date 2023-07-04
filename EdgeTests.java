/*
 * Sam Polyakov
 * EdgeTests.java
 * 05/02/2023
 * Project 8
 * CS231 B
 * 
 * Purpose: Creates tests for edge
 */

public class EdgeTests {
    public static void main(String[] args) {
        
        // case 1: testing constructor and distance
        {
        // setup 
        Vertex v1 = new Vertex();
        Vertex v2 = new Vertex();
        Edge e1 = new Edge(v1, v2, 0.0);
        Edge e2 = new Edge(v1, v2, 3.0);

        //verify
        System.out.println(e1.distance + " == 0.0");
        System.out.println(e2.distance + " == 3.0");

        //test
        assert e1.distance == 0.0 : "Error in Edge::constructor()";
        assert e2.distance == 3.0 : "Error in Edge::constructor()";
        }

        //case 2 : testing other()
        {
            // setup
            Vertex v1 = new Vertex();
            Vertex v2 = new Vertex();

            Edge e = new Edge(v1, v2, 2.0);

            // verify
            System.out.println(e.other(v1) == v2);

            //test
            assert e.other(v1)== v2 : "Error in Edge::other()";
        }

        //case 3 : testing vertices()
        {
                    //case 2 : testing other()
        {
            // setup
            Vertex v1 = new Vertex();
            Vertex v2 = new Vertex();

            Edge e = new Edge(v1, v2, 2.0);

            // verify
            // System.out.println(e.vertices());

            //test
            assert e.vertices() != null : "Error in Edge::vertices()";
        }
        }
    }
}
