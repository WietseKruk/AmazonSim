package com.nhlstenden.amazonsimulatie.models;

import java.util.UUID;
// import com.nhlstenden.amazonsimulatie.graph.*;

import java.util.List;
import java.util.*;


/*
 * Deze class stelt een robot voor. Hij impelementeerd de class Object3D, omdat het ook een
 * 3D object is. Ook implementeerd deze class de interface Updatable. Dit is omdat
 * een robot geupdate kan worden binnen de 3D wereld om zich zo voort te bewegen.
 */
class Robot implements Object3D, Updatable {
    private UUID uuid;

    private double x = 0;
    private double y = 0;
    private double z = 0;

    private double rotationX = 0;
    private double rotationY = 0;
    private double rotationZ = 0;
    private Graph graph;
    private List<Node> nodes;
    private double speed = 0.5;
    // private String node = "Node40";
    private int counter = 0;
    private String destination = "";
    DijkstraAlgorithm dijkstra; 
    private Product currentProduct = null;

    private boolean hasPath = false;

    private LinkedList<Node> path;    

    public Robot(Graph graph) {
        this.uuid = UUID.randomUUID();
        this.graph = graph; 
        //this.graph.printAllNodes();
        dijkstra = new DijkstraAlgorithm(this.graph);
        //dijkstra.execute(this.graph.getNodeById("Node0"));
        //path = dijkstra.getPath(this.graph.getNodeById("Node40"));
        x = this.graph.getNodeById("Node40").getX();
        z = this.graph.getNodeById("Node40").getZ();

    }

    public void setDestination(String destination){
        this.destination = destination;
        System.out.println(this.destination);
    }

    public String getDestination(){
        return destination;
    }

    public void setProduct(Product product){
        this.currentProduct = product;
        System.out.println(currentProduct.getNaam() + " set successfully");
    }

    /*
     * Deze update methode wordt door de World aangeroepen wanneer de
     * World zelf geupdate wordt. Dit betekent dat elk object, ook deze
     * robot, in de 3D wereld steeds een beetje tijd krijgt om een update
     * uit te voeren. In de updatemethode hieronder schrijf je dus de code
     * die de robot steeds uitvoert (bijvoorbeeld positieveranderingen). Wanneer
     * de methode true teruggeeft (zoals in het voorbeeld), betekent dit dat
     * er inderdaad iets veranderd is en dat deze nieuwe informatie naar de views
     * moet worden gestuurd. Wordt false teruggegeven, dan betekent dit dat er niks
     * is veranderd, en de informatie hoeft dus niet naar de views te worden gestuurd.
     * (Omdat de informatie niet veranderd is, is deze dus ook nog steeds hetzelfde als
     * in de view)
     */
    @Override
    public boolean update() {
          if(destination != "" && !hasPath){
            dijkstra.execute(this.graph.getNodeById("Node40"));
            path = dijkstra.getPath(this.graph.getNodeById(destination));    
            System.out.println("destination: " + destination);
            hasPath = true;
            return true;
            }
            else if (hasPath){
                
                if (x == path.getFirst().getX() && z == path.getFirst().getZ() && path.size() > 0){

                    path.remove(path.getFirst());
                    x = path.getFirst().getX();
                    z = path.getFirst().getZ();
                    System.out.println("Moving to Node:" + path.getFirst().getId());

                   

                    if (currentProduct != null){

                            currentProduct.setX(x);
                            currentProduct.setY(-1.25);
                            currentProduct.setZ(z);
                            System.out.println(currentProduct.getNaam() + " being carried at " + currentProduct.getX() + " " + currentProduct.getZ());
                    }  
                    if(path.size() == 1){
                        System.out.println("Destination reached");
                        currentProduct.setY(0);
                        path = null;
                        hasPath = false;
                        currentProduct = null;
                        destination = "";
                        x = graph.getNodeById("Node40").getX();
                        z = graph.getNodeById("Node40").getZ();
                        return true;
                        
                    }
                    return true;
                } 
                else{
                    return false; 
                }
         }
         else{
             System.out.println("Destination reached");
             hasPath = false;
             x = graph.getNodeById("Node40").getX();
             z = graph.getNodeById("Node40").getZ();
             //dijkstra.execute(graph.getNodeById("Node0"));
            //path = dijkstra.getPath(graph.getNodeById("Node40"));
             return true;
         }
        
    }

    @Override
    public String getUUID() {
        return this.uuid.toString();
    }

    @Override
    public String getType() {
        /*
         * Dit onderdeel wordt gebruikt om het type van dit object als stringwaarde terug
         * te kunnen geven. Het moet een stringwaarde zijn omdat deze informatie nodig
         * is op de client, en die verstuurd moet kunnen worden naar de browser. In de
         * javascript code wordt dit dan weer verder afgehandeld.
         */
        return Robot.class.getSimpleName().toLowerCase();
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }

    @Override
    public double getRotationX() {
        return this.rotationX;
    }

    @Override
    public double getRotationY() {
        return this.rotationY;
    }

    @Override
    public double getRotationZ() {
        return this.rotationZ;
    }
}