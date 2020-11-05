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
    
    private boolean isDelivering;

    private World world;
    private String source = "Node40";

    public Robot(Graph graph, World world) {
        this.uuid = UUID.randomUUID();
        this.graph = graph; 
        this.world = world;
        //this.graph.printAllNodes();
        dijkstra = new DijkstraAlgorithm(this.graph);
        //dijkstra.execute(this.graph.getNodeById("Node0"));
        //path = dijkstra.getPath(this.graph.getNodeById("Node40"));
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
        if(isAtSource() && !isCarryingProduct() && hasDestination() && !hasPath && !isDelivering){
            dijkstra.execute(graph.getNodeById(source));
            path = dijkstra.getPath(graph.getNodeById(destination));
            hasPath = true;
            return true;
        }

        if(!isAtDestination() && !isCarryingProduct() && hasDestination() && hasPath && !isDelivering){
            //move to destination

            //temp
            System.out.println("teleporting to destination");
            x = graph.getNodeById(destination).getX();
            z = graph.getNodeById(destination).getZ();
            //temp

            return true;
        }

        if(isAtDestination() && !isCarryingProduct() && hasDestination() && hasPath && !isDelivering){
            world.pickUpProduct(this, destination);
            //destination = source;
            //path = null;
            hasPath = false;
            System.out.println(destination);
            return true;
        }

        if(isAtDestination() && isCarryingProduct() && hasDestination() && !hasPath && !isDelivering){
            //path = new LinkedList<Node>();
            dijkstra.execute(graph.getNodeById(destination));
            path = dijkstra.getPath(graph.getNodeById(source));
            hasPath = true;
            return true;
        }

        if(!isAtSource() && isCarryingProduct() && hasDestination() && hasPath && !isDelivering){
            //move to source and update product

                //temp
                System.out.println("teleporting to source");
                x = graph.getNodeById(source).getX();
                z = graph.getNodeById(source).getZ();
                currentProduct.setX(graph.getNodeById(source).getX());
                currentProduct.setZ(graph.getNodeById(source).getZ());
                //temp

            return true;
        }

        if(isAtSource() && isCarryingProduct() && hasDestination() && hasPath && !isDelivering){
            destination = "";
            //path = null;
            hasPath = false;
            currentProduct.setY(0);
            currentProduct = null;
            world.addProductToTruck();
            return true;
        }

        // if(isAtSource() && isCarryingProduct() && hasDestination() && hasPath && !isDelivering && world.checkVrachtwagenProducts() == 10){
        //     isDelivering = true;
        //     return true;
        // }

        
        //einde ophalen

        //bezorgen
        if(isAtSource() && !isCarryingProduct() && hasDestination() && !hasPath && isDelivering){
            //path = new LinkedList<Node>();
            dijkstra.execute(graph.getNodeById(source));
            path = dijkstra.getPath(graph.getNodeById(destination));
            world.pickUpProduct(this, destination);
            world.removeProductFromTruck();
            hasPath = true;
            System.out.println("robot has product and path and is moving to " + destination);
            return true;
        }

        if(!isAtDestination() && isCarryingProduct() && hasDestination() && hasPath && isDelivering){
            //move to destination and update product

            //temp
            System.out.println("teleporting to destination with product");
            x = graph.getNodeById(destination).getX();
            z = graph.getNodeById(destination).getZ();
            currentProduct.setX(x);
            currentProduct.setZ(z);
            //temp
   
            return true;
        }

        if(isAtDestination() && isCarryingProduct() && hasDestination() && hasPath && isDelivering){
            world.addProductToTruck();
            currentProduct.setY(0);
            currentProduct = null;
            //destination = source;
            //path = null;
            hasPath = false;
            
            System.out.println("arrived at " + destination);
            return true;
        }

        if(isAtDestination() && !isCarryingProduct() && hasDestination() && !hasPath && isDelivering){
            //path = new LinkedList<Node>();

            System.out.println("generating new path to: " + source + " from: " + destination);
            dijkstra.execute(graph.getNodeById(destination));
            path = dijkstra.getPath(graph.getNodeById(source));
            hasPath = true;
            return true;
        }

        if(!isAtSource() && !isCarryingProduct() && hasDestination() && hasPath && isDelivering){
            //move to source

            //temp
            System.out.println("teleporting to source");
            x = graph.getNodeById(source).getX();
            z = graph.getNodeById(source).getZ();
            //temp

            return true;
        }

        if(isAtSource() && !isCarryingProduct() && hasDestination() && hasPath && isDelivering){
            destination = "";
            //path = null;
            hasPath = false;
            return true;
        }
        //einde bezorgen
        return false;







        // //het creeeren van een pad als de robot een bestemming heeft binnengekregen en op de sourceNode staat
        // if(hasDestination() && !hasPath && isAtSource()){
        //     dijkstra.execute(graph.getNodeById(source));
        //     path = dijkstra.getPath(graph.getNodeById(destination));
        //     hasPath = true;
        // }

        // //bewegen naar de destination
        // if(hasDestination() && hasPath && !isAtDestination()){
        //     //TODO
        //     if(isCarryingProduct()){

        //     }
        //     else{

        //     }
        // }

        // //bewegen naar de source
        // if(!hasDestination() && !hasPath && !isAtSource()){
        //     //terugbewegen naar de source
        // }

        // //als de robot op de destination is maar niet aan het deliveren is (oftewel aan het ophalen is)
        // if(isAtDestination() && !isDelivering){
        //     world.pickUpProduct(this, destination);
        //     dijkstra.execute(graph.getNodeById(destination));
        //     path = dijkstra.getPath(graph.getNodeById(source));
        // }

        // //als de robot op de source is, aan het ophalen is en een product bij zich heeft
        // if(isAtSource() && !isDelivering && isCarryingProduct()){
        //     currentProduct = null; //product loskoppelen van de robot
        //     hasPath = false;    //de robot heeft geen pad meer
        //     destination = "";   //destination resetten hierdoor wordt de world.isRobotAvailable == true zodat hij een nieuwe destination kan ontvangen
        //     world.addProductToTruck();  //er wordt een product toegevoegd aan de vrachtwagen
        // }

        // //als de robot een pad heeft, aan het deliveren is, een product bij zich heeft, en op de destination is
        // if(hasPath && isDelivering && isCarryingProduct() && isAtDestination()){
        //     currentProduct.setY(0); //product iets omhoog zodat het lijkt alsof deze op de stellage staat
        //     currentProduct = null; //product loskoppelen van de robot
        //     hasPath = false;    //de robot heeft nu geen pad meer
        //     destination = "";
        // }

        // if(hasPath && !isDelivering && isCarryingProduct() && isAtDestination()){
        //     currentProduct.setY(0); //product iets omhoog zodat het lijkt alsof deze op de stellage staat
        //     currentProduct = null; //product loskoppelen van de robot
        //     hasPath = false;    //de robot heeft nu geen pad meer
        //     destination = "";
        // }

        







        //   if(destination != "" && !hasPath){
        //     dijkstra.execute(this.graph.getNodeById(source));

        //     path = dijkstra.getPath(this.graph.getNodeById(destination));    
        //     System.out.println("destination: " + destination);
        //     hasPath = true;
        //     return true;
        //     }
        //     else if (hasPath){
                
        //         if (x == path.getFirst().getX() && z == path.getFirst().getZ() && path.size() > 0){

        //             path.remove(path.getFirst());
        //             x = path.getFirst().getX();
        //             z = path.getFirst().getZ();
        //             System.out.println("Moving to Node:" + path.getFirst().getId());

                   

        //             if (currentProduct != null){

        //                     currentProduct.setX(x);
        //                     currentProduct.setY(-1.25);
        //                     currentProduct.setZ(z);
        //                     System.out.println(currentProduct.getNaam() + " being carried at " + currentProduct.getX() + " " + currentProduct.getZ());
        //             }  
        //             if(path.size() == 1){
        //                 System.out.println("Destination reached");
        //                 currentProduct.setY(0);
        //                 source = destination;
        //                 path = null;
        //                 hasPath = false;
        //                 currentProduct = null;
        //                 destination = "";
        //                 // x = graph.getNodeById("Node40").getX();
        //                 // z = graph.getNodeById("Node40").getZ();
        //                 return true;
                        
        //             }
        //             return true;
        //         } 
        //         else{
        //             return false; 
        //         }
        //  }
        //  else{
        //      System.out.println("Destination reached");
        //      hasPath = false;
        //     //  x = graph.getNodeById("Node40").getX();
        //     //  z = graph.getNodeById("Node40").getZ();
        //     dijkstra.execute(this.graph.getNodeById(destination));
        //     path = dijkstra.getPath(this.graph.getNodeById("Node40"));
        //      return true;
        //  }
        
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