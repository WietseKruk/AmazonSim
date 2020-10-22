package com.nhlstenden.amazonsimulatie.graph;


public class Node {
    private String naam;
    private int x;
    private int z;

    public Node(String naam, int x, int z){
        this.naam = naam;
        this.x = x;
        this.z = z;
    }

    public String getNaam(){
        return naam;
    }

    public int getX(){
        return x;
    }
    
    public int getZ(){
        return z;
    }

}
