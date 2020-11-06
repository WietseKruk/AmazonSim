package com.nhlstenden.amazonsimulatie.models;

import java.util.UUID;
// import com.nhlstenden.amazonsimulatie.graph.*;

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
    //private List<Node> nodes;
    private double speed = 0.5;
    // private String node = "Node40";
    //private int counter = 0;
    private String destination = "";
    DijkstraAlgorithm dijkstra; 
    private Product currentProduct = null;

    private boolean hasPath = false;

    private LinkedList<Node> path; 
    
    private boolean isDelivering;

    private World world;
    private String source = "Node40";

    public Robot(Graph graph, World world) {
        this.uuid = UUID.randomUUID();
        this.graph = graph; 
        this.world = world;
        dijkstra = new DijkstraAlgorithm(this.graph);
        x = this.graph.getNodeById(source).getX();
        z = this.graph.getNodeById(source).getZ();
    }

    public void setDestination(String destination){
        this.destination = destination;
        System.out.println(this.destination);
    }

    public String getDestination(){
        return destination;
    }

    private boolean hasDestination(){
        if(destination != ""){
            return true;
        }
        return false;
    }

    private boolean isAtSource(){
        if(x == graph.getNodeById(source).getX() && z == graph.getNodeById(source).getZ()){
            return true;
        }
        return false;
    }

    private boolean isAtDestination(){
        if(hasDestination()){
            if(x == graph.getNodeById(destination).getX() && z == graph.getNodeById(destination).getZ()){
            return true;
        }
        }
        
        return false;
    }

    public void setIsDelivering(boolean isDelivering){
        this.isDelivering = isDelivering;
    }

    public boolean getIsDelivering(){
        return isDelivering;
    }

    public boolean isCarryingProduct(){
        if(currentProduct != null){
            return true;
            
        }
        else{ return false;}
    }

    public void setProduct(Product product){
        this.currentProduct = product;
        currentProduct.setY(-1.25);
        System.out.println(currentProduct.getNaam() + " set successfully");
    }

    private void moveAcrossPath(){
        if(this.x < path.getFirst().getX()){
            this.x += speed;
            //System.out.println("dit is x -----------------" + x);
        }
        if(this.x >  path.getFirst().getX()){
            this.x -= speed;
            //System.out.println("dit is x -----------------" + x);
        }
        if(this.z < path.getFirst().getZ()){
            this.z += speed;
            //System.out.println("dit is z -----------------" + z);
        }
        if(this.z >  path.getFirst().getZ()){
            this.z -= speed;
            //System.out.println("dit is z -----------------" + z);
        }
        if( this.z == path.getFirst().getZ() && this.x == path.getFirst().getX()){
            path.remove(path.getFirst());
        }
    }

    private void updateProductPos(){
        if(currentProduct != null){
            currentProduct.setX(x);
            currentProduct.setZ(z);
        }
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

        //ophalen
        if(isAtSource() && !isCarryingProduct() && hasDestination() && !hasPath && !getIsDelivering()){
            dijkstra.execute(graph.getNodeById(source));
            path = dijkstra.getPath(graph.getNodeById(destination));
            hasPath = true;
            return true;
        }

        if(!isAtDestination() && !isCarryingProduct() && hasDestination() && hasPath && !getIsDelivering()){        
            moveAcrossPath();
            return true;
        }

        if(isAtDestination() && !isCarryingProduct() && hasDestination() && hasPath && !getIsDelivering()){
            world.pickUpProduct(this, destination);
            hasPath = false;
            System.out.println(destination);
            return true;
        }

        if(isAtDestination() && isCarryingProduct() && hasDestination() && !hasPath && !getIsDelivering()){
            dijkstra.execute(graph.getNodeById(destination));
            path = dijkstra.getPath(graph.getNodeById(source));
            hasPath = true;
            return true;
        }

        if(!isAtSource() && isCarryingProduct() && hasDestination() && hasPath && !getIsDelivering()){
            moveAcrossPath();
            updateProductPos();
            return true;
        }

        if(isAtSource() && isCarryingProduct() && hasDestination() && hasPath && !getIsDelivering()){
            destination = "";
            hasPath = false;
            currentProduct.setY(-10);
            currentProduct = null;
            world.addProductToTruck();
            return true;
        } 
        //einde ophalen

        //bezorgen
        if(this.isAtSource() && !this.isCarryingProduct() && this.hasDestination() && !hasPath && getIsDelivering()){
            if(world.pickUpProduct(this, destination)){
                world.removeProductFromTruck();
            }
            dijkstra.execute(graph.getNodeById(source));
            path = dijkstra.getPath(graph.getNodeById(destination));
            hasPath = true;
            System.out.println("robot has product and path and is moving to " + destination);
            return true;
        }

        if(!isAtDestination() && isCarryingProduct() && hasDestination() && hasPath && getIsDelivering()){
            moveAcrossPath();
            updateProductPos();
            return true;
        }

        if(isAtDestination() && this.isCarryingProduct() && hasDestination() && hasPath && getIsDelivering()){
            currentProduct.setY(0);
            currentProduct = null;
            hasPath = false;
            System.out.println("arrived at " + destination);
            return true;
        }

        if(isAtDestination() && !isCarryingProduct() && hasDestination() && !hasPath && getIsDelivering()){
            System.out.println("generating new path to: " + source + " from: " + destination);
            dijkstra.execute(graph.getNodeById(destination));
            path = dijkstra.getPath(graph.getNodeById(source));
            hasPath = true;
            return true;
        }

        if(!isAtSource() && !isCarryingProduct() && hasDestination() && hasPath && getIsDelivering()){
            moveAcrossPath();
            return true;
        }

        if(isAtSource() && !isCarryingProduct() && hasDestination() && hasPath && getIsDelivering()){
            destination = "";
            hasPath = false;
            return true;
        }
        //einde bezorgen

        return false;     
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