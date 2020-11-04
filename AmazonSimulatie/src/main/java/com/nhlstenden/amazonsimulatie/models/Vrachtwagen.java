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
    private final double speed = 0.5;
    private final double source = -35;
    private final double end = -100;
    private final double laadStationX = 10;
    private final double laadStationZ = -100;
    private List<Stellage> stellages;

    private int stellageTarget = 0;

    private World world;

    public Vrachtwagen(int productCounter, World world, List<Stellage> stellages){
        this.uuid = UUID.randomUUID();
        this.productCounter = productCounter;
        this.stellages = stellages;
        x = laadStationX;
        z = laadStationZ;
        this.world = world;
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
    }

    public void addProduct(){
        productCounter++;
        System.out.println(productCounter);
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

    //robot rijdt naar stellageTarget, pakt stellage op, rijdt terug naar vrachtwagen, laat stellage los bij vrachtwagen vrachtwagen.productCounter++;
    //misschien mogelijk om robot na elke opdracht automatisch terug te laten rijden naar de sourceNode?


    //als de vrachtwagen aan het afhalen is en deze is nu vol. wordt maar 1 keer uitgevoerd
    if(productCounter == 10 && x == laadStationX && z == laadStationZ && !isDelivering()){
	stellageTarget = 0;
	//eventueel heen en weer rijden, extra
	isDelivering = true;
    System.out.println("stellageTarget reset: isDelivering = " + isDelivering);
	return true;	
    }

    //als de vrachtwagen aan het bezorgen is en deze heeft nog producten, wordt meerdere keren uitgevoerd
    if(productCounter > 0 && x == laadStationX && z == laadStationZ && isDelivering() && world.isRobotAvailable()){
	world.commandRobot(getPossibleDestinations().get(stellageTarget), isDelivering);	//in commandRobot() code aanpassen gebaseerd op isRobotDelivering;
	removeProduct();
	stellageTarget++;
	System.out.println("Truck is unloading");
	return true;
    }

    //als de vrachtwagen spullen aan het afleveren is maar deze is nu leeg, wordt maar 1 keer uitgevoerd
    if(productCounter == 0 && x == laadStationX && z == laadStationZ && isDelivering()){
	isDelivering = false;
	stellageTarget = 0;
	
	return true;
    }








        // if(world.isRobotAvailable() && stellageCount < 10 && isDelivering()){
        //         world.commandRobot(getPossibleDestinations().get(stellageCount));
        //         //world.commandRobot("Node0");
        //         System.out.println("Vrachtwagen commanded robot at Node: " + getPossibleDestinations().get(stellageCount) + " - stellageCount:" + stellageCount);
        //         stellageCount++;
        //         return true;
        // }
        
        return true;

        // if(!isDelivering() && !hasProducts() && z < end){
        //     z += speed; return true;}
        // if(!isDelivering() && productCounter < 10 && z == end){
        //     return false;}
        // if(!isDelivering() && productCounter == 10 && z <= end && z > source){
        //     z -= speed;}
        // if(!isDelivering() && productCounter == 10 && z == source){ 
        //     setDelivering(true); 
        //     return true;}
    
    
        // if(isDelivering() && productCounter == 10 && z < end){
        //     z += speed; 
        //     return true;}
        // if(isDelivering() && hasProducts() && z == end){
        //     return false;}
        // if(isDelivering() && !hasProducts() && z <= end && z > source){
        //     z -= speed; 
        //     return true;}
        // if(isDelivering() && !hasProducts() && z == source){
        //     setDelivering(false); 
        //     return true;}
        // return true;
        
        
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