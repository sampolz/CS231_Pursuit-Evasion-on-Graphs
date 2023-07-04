/*
 * Sam Polyakov
 * GraphTests.java
 * 05/02/2023
 * Project 8
 * CS231 B
 * 
 * Purpose: Creates tests for graph
 */

import java.util.*;

 public class GraphTests {
    public static void main(String[] args) {
        // case 1: testing size() on empty graph
        {
            // setup
            Graph graph = new Graph();

            // verify
            System.out.println(graph.size() + " == 0");

            // test
            assert graph.size() == 0 : "Error in Graph::size() on empty graph";
        }

        // case 2: testing size() on non-empty graph
        {
            // setup
            Graph graph = new Graph(5);

            // verify
            System.out.println(graph.size() + " == 5");

            // test
            assert graph.size() == 5 : "Error in Graph::size() on non-empty graph";
        }

        // case 3: testing getVertices()
        {
            // setup
            Graph graph = new Graph(3);
            Iterable<Vertex> vertices = graph.getVertices();

            // verify
            int count = 0;
            for (Vertex vertex : vertices) {
                count++;
            }
            System.out.println(count + " == 3");

            // test
            assert count == 3 : "Error in Graph::getVertices()";
        }

        // case 4: testing getEdges()
        {
            // setup
            Graph graph = new Graph(3, 1);
            Iterable<Edge> edges = graph.getEdges();

            // verify
            int count = 0;
            for (Edge edge : edges) {
                count++;
            }
            System.out.println(count + " == 3");

            // test
            assert count == 3 : "Error in Graph::getEdges()";
        }

        // case 5: testing addVertex()
        {
            // setup
            Graph graph = new Graph();
            Vertex vertex = graph.addVertex();

            // verify
            System.out.println(graph.size() + " == 1");
            // System.out.println(vertex.getId() + " == 0");

            // test
            assert graph.size() == 1 : "Error in Graph::addVertex()";
            // assert vertex.getId() == 0 : "Error in Graph::addVertex()";
        }

        // case 6: testing addEdge()
        {
            // setup
            Graph graph = new Graph(3,0);
            Vertex v1 = graph.vertices.get(0);
            Vertex v2 = graph.vertices.get(1);
            Edge edge = graph.addEdge(v1, v2, 2.0);


            // verify
            System.out.println(graph.edges.size());
            System.out.println(graph.edges.get(0).distance + " == 2.0");

            // test
            assert graph.edges.get(0).distance == 2.0 : "Error in Graph::addEdge()";
        }

        // case 7: testing distanceFrom()
        {
            // setup
            Graph graph = new Graph(3, 0);
            Vertex source = graph.vertices.get(0);
            Vertex dest = graph.vertices.get(1);
            graph.addEdge(source, dest, 2.0);

            // test
            HashMap<Vertex, Double> distances = graph.distanceFrom(source);
            System.out.println(distances.get(source) + " == 0.0");
            System.out.println(distances.get(dest) + " == 2.0");
            assert distances.get(source) == 0.0 : "Error in Graph::distanceFrom";
        }
    }
}
