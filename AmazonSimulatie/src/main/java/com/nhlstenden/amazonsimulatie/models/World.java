package com.nhlstenden.amazonsimulatie.models;


import com.nhlstenden.amazonsimulatie.graph.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
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
    List<Product> products = new ArrayList<>();
    List<Stellage> stellages = new ArrayList<>();
    Graph graph = new Graph();

    /*
     * De wereld maakt een lege lijst voor worldObjects aan. Daarin wordt nu één robot gestopt.
     * Deze methode moet uitgebreid worden zodat alle objecten van de 3D wereld hier worden gemaakt.
     */
    public World() {
        
        this.worldObjects = new ArrayList<>();
        buildGraph();
        this.worldObjects.add(new Robot(graph));
        this.worldObjects.add(new Robot(graph));

        
        };
        
    

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


    private void buildGraph(){
        List<Node> nodes = new ArrayList<>();
        

        final int rowSize = 5;
        final int columnSize = 9;
        final int rowSpacing = 5;
        final int columnSpacing = 3;
        final int offset = 5;
        int stellageCount = 0;
        final int maxStellages = 10;
        boolean filled = true;
        final int nodeValue = 1;
        final int stellageValue = 2;
        final int sourceNode = 1;

        for (int i = 0; i < rowSize; i++){
            for (int j = 0; j < columnSize; j++){
                nodes.add(new Node("Node", i*rowSpacing + offset, j*columnSpacing + offset, nodeValue));
                System.out.println("Node-" + i + "," + j + " x: " + (i*rowSpacing + offset) + " z: " + (j*columnSpacing + offset));
                if(i == 4 && j == 8){
                        nodes.add(new Node("Source", i*rowSpacing + offset, j*columnSpacing + offset, nodeValue));
                        continue;
                    }
                if (i % 2 == 0 && j % 2 == 0 && stellageCount < maxStellages){
                
                    stellages.add(new Stellage(i * rowSpacing + offset, j * columnSpacing + offset, "stellage" + stellageCount, filled));
                    nodes.add(new Node("Stellage", i*rowSpacing + offset, j*columnSpacing + offset, stellageValue));

                        if(filled){
                            products.add(new Product(i*rowSpacing + offset,j*columnSpacing + offset, "product" + stellageCount));
                            System.out.println(products.get(stellageCount).getNaam());
                        }  
                    stellageCount++;

                       
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

            for(Node n : nodes){
                graph.addNode(n);
            }
    }

}