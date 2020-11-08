//package com.nhlstenden.amazonsimulatie.tests1;

import com.nhlstenden.amazonsimulatie.models.DijkstraAlgorithm;
import com.nhlstenden.amazonsimulatie.models.Edge;
import com.nhlstenden.amazonsimulatie.models.Graph;
import com.nhlstenden.amazonsimulatie.models.Node;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DijkstraAlgorithmTest {

    private List<Node> nodes;
    private List<Edge> edges;

    @Test
    public void testExcute() {
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        for (int i = 0; i < 11; i++) {
            Node location = new Node("Node_" + i, i, i, false);
            nodes.add(location);
        }

        addLane("Edge_0", 0, 1, 5);
        addLane("Edge_1", 0, 2, 10);
        addLane("Edge_2", 0, 4, 8);
        addLane("Edge_3", 2, 6, 7);
        addLane("Edge_4", 2, 7, 10);
        addLane("Edge_5", 3, 7, 11);
        addLane("Edge_6", 5, 8, 4);
        addLane("Edge_7", 8, 9, 3);
        addLane("Edge_8", 7, 9, 8);
        addLane("Edge_9", 4, 9, 10);
        addLane("Edge_10", 9, 10, 5);
        addLane("Edge_11", 1, 10, 6);

        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(nodes.get(0));
        LinkedList<Node> path = dijkstra.getPath(nodes.get(10));

        assertNotNull(path);
        assertTrue(path.size() > 0);
    }

    private void addLane(String id, int source, int destination, int weight) {
        Edge lane = new Edge(id,nodes.get(source), nodes.get(destination), weight );
        edges.add(lane);
    }

    
}