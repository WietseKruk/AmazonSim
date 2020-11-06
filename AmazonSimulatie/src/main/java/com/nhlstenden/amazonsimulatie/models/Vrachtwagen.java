package com.nhlstenden.amazonsimulatie.models;
import java.util.UUID;
import java.util.*;

public class Vrachtwagen implements Object3D, Updatable{
    private UUID uuid;

    //Position variables
    private double x = 0;
    private double y = 0;
    private double z = 0;

    private double rotationX = 0;
    private double rotationY = -Math.PI/2;
    private double rotationZ = 0;

    private int productCounter;
    private boolean isDelivering = false;
    private final double speed = 1;
    private final double end = 100;
    private final double laadStationX = 10;
    private final double laadStationZ = -100;
    private List<Stellage> stellages;

    private int stellageTarget = 0;

    private World world;

    public Vrachtwagen(int productCounter, World world, List<Stellage> stellages){
        this.uuid = UUID.randomUUID();
        this.productCounter = productCounter;
        this.stellages = stellages;
        shuffleStellages();
        x = laadStationX;
        z = laadStationZ;
        this.world = world;
    }

    public void shuffleStellages(){
        Collections.shuffle(stellages);
    }

    public List<String> getPossibleDestinations(){
        List<String> result = new ArrayList<>();
        for (Stellage s : stellages){
            result.add(s.getNodeName());
        }
        return result;
    }

    public int getProductCount(){
        return productCounter;
    }

    public boolean hasProducts(){
        if(productCounter > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void setNrOfProducts(int nrOfProducts){
        productCounter = nrOfProducts;
    }    
    public boolean isDelivering(){
        return isDelivering;
    }

    public void setDelivering(boolean isDelivering){
        this.isDelivering = isDelivering;
    }

    public void removeProduct(){
        productCounter--;
        System.out.println("ProductCounter: " + productCounter);
    }

    public void addProduct(){
        productCounter++;
        System.out.println("ProductCounter: " + productCounter);
    }

    @Override
    public boolean update() {
        
    //als de vrachtwagen spullen aan het afhalen is totdat deze vol is. wordt meerdere keren uitgevoerd
    if(productCounter < 10 && x == laadStationX && z == laadStationZ && !isDelivering() && world.isRobotAvailable()){

	//extra check om timingsproblemen te voorkomen		
        if(stellageTarget < 10){
            world.commandRobot(getPossibleDestinations().get(stellageTarget), isDelivering); //isRobotDelivering moet in world meegegeven worden aan de robot zodat deze daar gebruikt kan worden in Update();  
        }
	stellageTarget++;
	System.out.println("Truck is waiting for deliveries");
	return true;
    }

    //als de vrachtwagen aan het afhalen is en deze is nu vol. wordt maar 1 keer uitgevoerd
    if(productCounter == 10 && /*x == laadStationX && z == laadStationZ && */!isDelivering()){
    System.out.println(" dit is de x van de vrachtwagen " + this.x);

        if(this.x < end){
            this.x += speed;
        }
        if(this.x == end){
            isDelivering = true;
        }

    System.out.println("stellageTarget reset: isDelivering = " + isDelivering);
	return true;	
    }

    if(isDelivering == true && x != laadStationX){
        this.x -= speed;
        stellageTarget = 0;
    }

    //als de vrachtwagen aan het bezorgen is en deze heeft nog producten, wordt meerdere keren uitgevoerd
    if(productCounter > 0 && x == laadStationX && z == laadStationZ && isDelivering() && world.isRobotAvailable()){
	world.commandRobot(getPossibleDestinations().get(stellageTarget), isDelivering);	//in commandRobot() code aanpassen gebaseerd op isRobotDelivering;
	stellageTarget++;
	System.out.println("Truck is unloading");
	return true;
    }

    //als de vrachtwagen spullen aan het afleveren is maar deze is nu leeg, wordt maar 1 keer uitgevoerd
    if(productCounter == 0 && x == laadStationX && z == laadStationZ && isDelivering()){
	isDelivering = false;
    stellageTarget = 0;
    shuffleStellages();
	
	return true;
    }
        return true;     
    }    
    
    @Override
    public String getUUID() {
        return this.uuid.toString();
    }

    @Override
    public String getType() {

        return Vrachtwagen.class.getSimpleName().toLowerCase();
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