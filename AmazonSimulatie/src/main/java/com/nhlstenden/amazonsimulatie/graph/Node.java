package com.nhlstenden.amazonsimulatie.graph;

import java.util.*;

public class Node {

    private String naam;
    private int x;
    private int z;
    private int value;
    private Map<Node, Integer> connectedNodes = new HashMap<>();
    private boolean isStellage;

    public Node(String naam, boolean isStellage, int x, int z, int value){
        this.naam = naam;
        this.x = x;
        this.z = z;
        this.value = value;
        this.isStellage = isStellage;
    }

    public boolean isStellage(){
        return isStellage;
    }

    public String getNaam(){
        return naam;
    }

    public int getValue(){
        return value;
    }

    public int getX(){
        return x;
    }
    
    public int getZ(){
        return z;
    }

    public Map<Node, Integer> getConnectedNodes(){
        return connectedNodes;
    }

    public void addConnectedNode(Node connectedNode, int value){
        connectedNodes.put(connectedNode, value);
    }  
}
