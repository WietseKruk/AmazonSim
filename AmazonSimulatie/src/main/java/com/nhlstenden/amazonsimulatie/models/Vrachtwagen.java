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

    private List<Stellage> stellages;

    private World world;

    public Vrachtwagen(int productCounter, World world, List<Stellage> stellages){
        this.uuid = UUID.randomUUID();
        this.productCounter = productCounter;
        this.stellages = stellages;
        x = 10;
        this.world = world;
    }

    public List<String> getPossibleDestinations(){
        List<String> result = new ArrayList<>();
        for (Stellage s : stellages){
            result.add(s.getNodeName());
        }
        return result;
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

    @Override
    public boolean update() {
        
        if(world.isRobotAvailable()){
                //world.commandRobot(getPossibleDestinations().get(0));
                world.commandRobot("Node0");
                System.out.println("Vrachtwagen commanded robot at Node0");
                return true;
        }
        
        return false;

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