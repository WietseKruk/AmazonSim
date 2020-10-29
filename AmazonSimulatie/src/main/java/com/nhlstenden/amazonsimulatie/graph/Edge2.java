package com.nhlstenden.amazonsimulatie.graph;

public class Edge2 {
    private Node2 start;
    private Node2 einde;
    private double kosten; 
    
    public Edge2(Node2 start, Node2 einde, double kosten){
        this.start = start;
        this.einde = einde;
        this.kosten = kosten;
    }


    public Node2 getStart(){
        return start;
    }

    public void setStart(Node2 start){
        this.start = start;
    }
    
    public Node2 getEinde(){
        return einde;
    }

    public void setEinde(Node2 einde){
        this.einde = einde;
    }

    public double getKosten(){
        return kosten;
    }

    public void setKosten(int kosten){
        this.kosten = kosten; 
    }
    
}
