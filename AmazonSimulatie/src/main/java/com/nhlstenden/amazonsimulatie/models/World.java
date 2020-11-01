package com.nhlstenden.amazonsimulatie.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/*
 * Deze class is een versie van het model van de simulatie. In dit geval is het
 * de 3D wereld die we willen modelleren (magazijn). De zogenaamde domain-logic,
 * de logica die van toepassing is op het domein dat de applicatie modelleerd, staat
 * in het model. Dit betekent dus de logica die het magazijn simuleert.
 */
public class World implements Model {
    /*
     * De wereld bestaat uit objecten, vandaar de naam worldObjects. Dit is een lijst
     * van alle objecten in de 3D wereld. Deze objecten zijn in deze voorbeeldcode alleen
     * nog robots. Er zijn ook nog meer andere objecten die ook in de wereld voor kunnen komen.
     * Deze objecten moeten uiteindelijk ook in de lijst passen (overerving). Daarom is dit
     * een lijst van Object3D onderdelen. Deze kunnen in principe alles zijn. (Robots, vrachrtwagens, etc)
     */
    private List<Object3D> worldObjects;
    /*
     * Dit onderdeel is nodig om veranderingen in het model te kunnen doorgeven aan de controller.
     * Het systeem werkt al as-is, dus dit hoeft niet aangepast te worden.
     */
    PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    private List<Node> nodes;
    private List<Edge> edges;
    List<Product> products = new ArrayList<>();
    List<Stellage> stellages = new ArrayList<>();
    Graph graph;
    private Robot robot1;
    private Robot robot2;
    private Vrachtwagen vrachtwagen;
    // List<Node> nodes = new ArrayList<>();

    /*
     * De wereld maakt een lege lijst voor worldObjects aan. Daarin wordt nu één robot gestopt.
     * Deze methode moet uitgebreid worden zodat alle objecten van de 3D wereld hier worden gemaakt.
     */
    public World() {
        // graph = new Graph(); 
        this.worldObjects = new ArrayList<>();
        // buildGraph();
        // initGraph();
        testExcute();
        
        vrachtwagen = new Vrachtwagen(0, this, stellages);
        robot1 = new Robot(graph, this);
        robot2 = new Robot(graph, this);
        this.worldObjects.add(vrachtwagen);
        this.worldObjects.add(robot1);
        this.worldObjects.add(robot2);
        };
        
    public boolean isRobotAvailable(){
       if(robot1.getDestination().isEmpty()){
           return true;
       }
       else if(robot2.getDestination().isEmpty()){
           return true;
       }
       else{
           return false;
       }
    }

    private Robot checkWhichRobotAvailable(){
        if(robot1.getDestination().isEmpty()){
            return robot1;
        }
        else if(robot2.getDestination().isEmpty()){
            return robot2;
        }
        return null;
    }

    public void addProductToTruck(){
        if(vrachtwagen.getProductCount() < 10){
            vrachtwagen.addProduct();
        }
    }

    public boolean removeProductFromTruck(){
        if(vrachtwagen.hasProducts()){
            vrachtwagen.removeProduct();
            return true;
        }
        else { return false;}
        
    }

    public void commandRobot(String nodeName, boolean isDelivering){
        if(isRobotAvailable()){
            assignStellage(checkWhichRobotAvailable(), nodeName, isDelivering);
            // robot1.setDestination(nodeName);
            System.out.println("Robot  will be assigned " + nodeName + " and isDelivering = " + isDelivering);
        } else{
            System.out.println("No available robot: waiting...");
        }
    }

    public void pickUpProduct(Robot robot, String nodeName){
        System.out.println("pickUpProduct method entered");
        for(Product p : products){
            if(p.getNodeName().equals(nodeName)){
                robot.setProduct(p);
                System.out.println("product picked up");
            }
        }
    }

    private void assignStellage(Robot robot, String nodename, boolean isDelivering){
        if(!isDelivering){
            for(Product p : products){
            if(p.getNodeName().equals(nodename)){
                System.out.println("Robot succesfully assigned " + p.getNaam());
                System.out.println("Robot destination: " + p.getNodeName());
                robot.setIsDelivering(isDelivering);
                robot.setDestination(p.getNodeName());           
            }
            else{
                System.out.println("checking for " + nodename + " at " + p.getNodeName());
            }
            }
        } else if (isDelivering){
            for(Product p : products){
                if(p.getNodeName().equals(nodename)){
                    System.out.println("Robot destination: " + p.getNodeName());
                    robot.setIsDelivering(isDelivering);
                    //robot.setProduct(p);
                    robot.setDestination(p.getNodeName());           
                }
                else{
                    //System.out.println("checking for " + nodename + " at " + p.getNodeName());
                }
                }
        }
        
    }
    /*
     * Deze methode wordt gebruikt om de wereld te updaten. Wanneer deze methode wordt aangeroepen,
     * wordt op elk object in de wereld de methode update aangeroepen. Wanneer deze true teruggeeft
     * betekent dit dat het onderdeel daadwerkelijk geupdate is (er is iets veranderd, zoals een positie).
     * Als dit zo is moet dit worden geupdate, en wordt er via het pcs systeem een notificatie gestuurd
     * naar de controller die luisterd. Wanneer de updatemethode van het onderdeel false teruggeeft,
     * is het onderdeel niet veranderd en hoeft er dus ook geen signaal naar de controller verstuurd
     * te worden.
     */
    @Override
    public void update() {
        for (Object3D object : this.worldObjects) {
            if(object instanceof Updatable) {
                if (((Updatable)object).update()) {
                    pcs.firePropertyChange(Model.UPDATE_COMMAND, null, new ProxyObject3D(object));
                }
            }
        }
    }

    /*
     * Standaardfunctionaliteit. Hoeft niet gewijzigd te worden.
     */
    @Override
    public void addObserver(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    /*
     * Deze methode geeft een lijst terug van alle objecten in de wereld. De lijst is echter wel
     * van ProxyObject3D objecten, voor de veiligheid. Zo kan de informatie wel worden gedeeld, maar
     * kan er niks aangepast worden.
     */
    @Override
    public List<Object3D> getWorldObjectsAsList() {
        ArrayList<Object3D> returnList = new ArrayList<>();

        for(Object3D object : this.worldObjects) {
            returnList.add(new ProxyObject3D(object));
        }

        return returnList;
    }
    
    public void testExcute() {

        final int rowSize = 5;
        final int columnSize = 9;
        final int rowSpacing = 5;
        final int columnSpacing = 3;
        final int offset = 5;
        int stellageCount = 0;
        final int maxStellages = 10;
        boolean filled = true;
        int counter = 0;

        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        for (int i = 0; i < rowSize; i++){
            for (int j = 0; j < columnSize; j++){
                if (i % 2 == 0 && j % 2 == 0 && stellageCount < maxStellages){
                
                    stellages.add(new Stellage(i * rowSpacing + offset, j * columnSpacing + offset, "stellage" + stellageCount, filled, "Node" + counter));
                    nodes.add(new Node("Node" + counter, i*rowSpacing + offset, j*columnSpacing + offset, false));
                    System.out.println("Node" + counter + " Stellage" + " x: " + (i*rowSpacing + offset) + " z: " + (j*columnSpacing + offset));
                    counter++;

                        if(filled){
                            products.add(new Product(i*rowSpacing + offset,j*columnSpacing + offset, "product" + stellageCount, "Node" + (counter - 1)));
                            System.out.println(products.get(stellageCount).getNaam());
                        }  
                    stellageCount++;    
                }
                else{
                    nodes.add(new Node("Node" + counter, i*rowSpacing + offset, j*columnSpacing + offset, false));
                    System.out.println("Node" + counter + " Node" +" x: " + (i*rowSpacing + offset) + " z: " + (j*columnSpacing + offset));
                    counter++;
                }
                continue;
            }
        }

        for(Product p : products){
            this.worldObjects.add(p); 
        }

        for(Stellage s : stellages){
            this.worldObjects.add(s); 
        }

        initGraph();
        

        graph = new Graph(nodes, edges);
        // DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        // dijkstra.execute(nodes.get(0));
        // LinkedList<Node> path = dijkstra.getPath(nodes.get(33));
       

        
        // for (Node vertex : path) {
        //     System.out.println(vertex);
        // }

    }
    private void addLane(String laneId, int sourceLocNo, int destLocNo,int duration) {
        Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration );
        edges.add(lane);

        Edge lane1 = new Edge(laneId,nodes.get(destLocNo), nodes.get(sourceLocNo), duration );
        edges.add(lane1);
    }
    
    
    
    public void initGraph(){


        int columnCounter = 1;
        int rowCounter = 1;
        int nodeCounter= 0;
        final int down = 9;
        final int right = 1;

        for(Node n: nodes){   
            
            boolean actionPerformed = false;

            if((columnCounter < 9 && rowCounter < 5) && !actionPerformed){
                if(columnCounter % 2 == 0 && (rowCounter == 1 || rowCounter == 3)){
                    addLane(n.getId(), nodeCounter, nodeCounter + right, 4);
                    addLane(n.getId(), nodeCounter, nodeCounter + down, 1);
                    System.out.print("Rechts " +  4 + " Onder " + 1 + " ");
                }
                if ((rowCounter == 1 || rowCounter == 3) && columnCounter % 2 != 0){
                    addLane(n.getId(), nodeCounter, nodeCounter + right, 4);
                    addLane(n.getId(), nodeCounter, nodeCounter + down, 4);
                    System.out.print("Rechts " +  4 + " Onder " + 4 + " ");
                }
                if (rowCounter == 2 && columnCounter % 2 != 0){
                    addLane(n.getId(), nodeCounter, nodeCounter + right, 1);
                    addLane(n.getId(), nodeCounter, nodeCounter + down, 4);
                    System.out.print("Rechts " +  1 + " Onder " + 4 + " ");
                }
                else {
                    addLane(n.getId(), nodeCounter, nodeCounter + right, 1);
                    addLane(n.getId(), nodeCounter, nodeCounter + down, 1);
                    System.out.print("Rechts " +  1 + " Onder " + 1 + " ");
                }
                //System.out.print("Rechts & Onder " + columnCounter + " " + rowCounter + " ");
                columnCounter++;
                //System.out.print("Updated: " + columnCounter + " " + rowCounter + " ");
                actionPerformed = true;
            }

            if(columnCounter == 9 && rowCounter < 5 && !actionPerformed){
                if (rowCounter < 4){
                    addLane(n.getId(), nodeCounter, nodeCounter + down, 4);
                    System.out.print("Onder " + 4 + " ");
                }
                else{
                    addLane(n.getId(), nodeCounter, nodeCounter + down, 1);
                    System.out.print("Onder " + 1 + " ");
                }
                
                //System.out.print("Onder " + columnCounter + " " + rowCounter + " ");
                columnCounter = 1;
                rowCounter++;
                actionPerformed = true;
            }

            if(rowCounter == 5 && columnCounter < 9 && !actionPerformed){
                addLane(n.getId(), nodeCounter, nodeCounter + right, 1);
                System.out.print("Rechts " + 1 + " ");
                //System.out.print("Rechts " + columnCounter + " " + rowCounter + " ");
                columnCounter++;
                actionPerformed = true;
            }  
            System.out.println("Node " + nodeCounter);   
                nodeCounter++;
        }
    }    



    // private void buildGraph(){
        
        

    //     final int rowSize = 5;
    //     final int columnSize = 9;
    //     final int rowSpacing = 5;
    //     final int columnSpacing = 3;
    //     final int offset = 5;
    //     int stellageCount = 0;
    //     final int maxStellages = 10;
    //     boolean filled = true;
    //     final int nodeValue = 1;
    //     final int stellageValue = 2;
    //     final int sourceNode = 40;
    //     int counter = 0;

    //     for (int i = 0; i < rowSize; i++){
    //         for (int j = 0; j < columnSize; j++){
                
    //             if(counter == sourceNode){
    //                     nodes.add(new Node("Source", counter, false, i*rowSpacing + offset, j*columnSpacing + offset, nodeValue));
    //                     System.out.println("Node" + counter + " Source" + " x: " + (i*rowSpacing + offset) + " z: " + (j*columnSpacing + offset));
    //                     counter++;
    //                 }
    //             else if (i % 2 == 0 && j % 2 == 0 && stellageCount < maxStellages){
                
    //                 stellages.add(new Stellage(i * rowSpacing + offset, j * columnSpacing + offset, "stellage" + stellageCount, filled));
    //                 nodes.add(new Node("Node" + counter, counter, true, i*rowSpacing + offset, j*columnSpacing + offset, stellageValue));
    //                 System.out.println("Node" + counter + " Stellage" + " x: " + (i*rowSpacing + offset) + " z: " + (j*columnSpacing + offset));
    //                 counter++;

    //                     if(filled){
    //                         products.add(new Product(i*rowSpacing + offset,j*columnSpacing + offset, "product" + stellageCount));
    //                         System.out.println(products.get(stellageCount).getNaam());
    //                     }  
    //                 stellageCount++;
                    
                      
    //             }
    //             else{
    //                 nodes.add(new Node("Node" + counter, counter, false, i*rowSpacing + offset, j*columnSpacing + offset, nodeValue));
    //                 System.out.println("Node" + counter + " Node" +" x: " + (i*rowSpacing + offset) + " z: " + (j*columnSpacing + offset));
    //                 counter++;
    //             }
    //             continue;
    //             }
    //         }
            
    //         for(Product p : products){
    //             this.worldObjects.add(p); 
    //         }

    //         for(Stellage s : stellages){
    //             this.worldObjects.add(s); 
    //             }

    //         for(Node n : nodes){
    //             graph.addNode(n);
            
    //     }
    // }
}