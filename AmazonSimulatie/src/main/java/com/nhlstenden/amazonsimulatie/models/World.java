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
    private Graph graph;

    /*
     * De wereld maakt een lege lijst voor worldObjects aan. Daarin wordt nu één robot gestopt.
     * Deze methode moet uitgebreid worden zodat alle objecten van de 3D wereld hier worden gemaakt.
     */
    public World() {
        graph = new Graph(); 
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
    public void initGraph(){
        Graph g = new Graph(); 
        Node a = new Node("a", 1, 1,1);
        Node b = new Node("b", 2, 2,1);
        Node c = new Node("c", 1, 1,1);
        Node d = new Node("d", 2, 2,1);
        Node e = new Node("e", 1, 1,1);
        Node f = new Node("f", 2, 2,1);

        g.addMap(a);
        g.addMap(b);
        g.addMap(c);
        g.addMap(d);
        g.addMap(e);
        g.addMap(f);

        graph.addEdge(new Edge(a, b,10));
        graph.addEdge(new Edge(a, c,15));
        graph.addEdge(new Edge(b, f,15));
        graph.addEdge(new Edge(b, d,12));
        graph.addEdge(new Edge(d, f,1));
        graph.addEdge(new Edge(d, e,2));
        graph.addEdge(new Edge(c, e,10));
        graph.addEdge(new Edge(f, e,5));
    }

    private void buildGraph(){
        List<Node> nodes = new ArrayList<>();
        
        int count = 0; 
        final int size = 9;
        final int spacing = 3;
        final int offset = 3;
        int stellageCount = 0;
        final int maxStellages = 10;
        boolean filled = true;
        final int nodeValue = 1;
        final int stellageValue = 2;
        final int sourceNode = 8;

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){

                nodes.add(new Node("Node" + count, i*spacing + offset, j*spacing + offset, nodeValue));
                System.out.println("Node-" + count + " x: " + (i*spacing + offset) + " z: " + (j*spacing + offset));
                if(i == sourceNode && j == sourceNode){
                        nodes.add(new Node("Source", i*spacing + offset, j*spacing + offset, nodeValue));
                        continue;
                    }
                if (i % 2 == 0 && j % 2 == 0 && stellageCount < maxStellages){
                
                    stellages.add(new Stellage(i * spacing + offset, j * spacing + offset, "stellage" + stellageCount, filled));
                    nodes.add(new Node("Stellage", i*spacing + offset, j*spacing + offset, stellageValue));

                        if(filled){
                            products.add(new Product(i*spacing + offset,j*spacing + offset, "product" + stellageCount));
                            System.out.println(products.get(stellageCount).getNaam() + i + j);
                        }  
                    stellageCount++;

                      
                }
                count++;
                continue;
                }
            count++;
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