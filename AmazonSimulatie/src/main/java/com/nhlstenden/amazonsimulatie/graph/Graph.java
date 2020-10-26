package com.nhlstenden.amazonsimulatie.graph;

import java.util.*;

public class Graph {
    private Map<Node, List> buren; 
    
    public Graph(){
        buren = new LinkedHashMap();
    }
    
    public Map<Node, List> getBuren(){
        return buren;
    }
    
    public void setBuren(Map<Node, List> buren){
        this.buren = buren;
    }

    public List getList(Node node){
        return buren.get(node);
    }

    public void addVerbinding(Node node){
        buren.putIfAbsent(node, new ArrayList());
    }

    // public void addEdge(List<Edge> edges){
    //     for(Edge e : edges){
    //     buren.get(e.getStart()).add(e.getEinde());
    //     buren.get(e.getEinde()).add(e.getEinde());
    //     }

    // }
    public void addEdge(Edge edge){
        buren.get(edge.getStart()).add(edge.getEinde());
        buren.get(edge.getEinde()).add(edge.getEinde());
    }

    // public void addEdge(Edge edge, Edge edge2){
    //     buren.get(edge.getStart()).add(edge.getEinde());
    //     buren.get(edge.getEinde()).add(edge.getEinde());
    //     buren.get(edge2.getStart()).add(edge2.getEinde());
    //     buren.get(edge2.getEinde()).add(edge2.getEinde());
    // }

    
    List<Node> nodes = new ArrayList<>();

    public void addNode(Node node){
        nodes.add(node);
    }

    public List<Node> getGraph(){
        return nodes;
    }

    public Node getNode(int x, int z){
        Node result = null;
        for (Node n : nodes){
            if(n.getX() == x && n.getZ() == z){
                result = n;
                break;
            }    
        }
        return result;
    }

    public Node getNodeByName(String name){
        Node result = null;
        for (Node n : nodes){
            if (n.getNaam().equals(name)){
                result = n;
            }
        }
        return result;
    }
}
