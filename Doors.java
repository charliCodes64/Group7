public class Doors {

    private String doorDescription;
    private String doorDirection;
    //below if door is 'false' in txt doc they should not be able to continue and must restart
    private Boolean doorAccessibility;

    Doors(String direction, String doorDescription, boolean doorAccessibility){
        this.doorDirection = direction;
        this.doorDescription = doorDescription;
        this.doorAccessibility = doorAccessibility;
    }
    public String getDirection(){
        return this.doorDirection;
    }
    public String getDescription(){
        return this.doorDescription;
    }
    public boolean getDoorAccessibility(){
        return this.doorAccessibility;
    }

    public boolean DoorAccessibilityCorrect() {
        return this.doorAccessibility;
    }

    //below the method should make a new file and write which room the player failed at
    public void failedAttemptWriter(String failedRoomDescription){

    }
    public String toString(){
        return doorDirection + ":" + doorDescription + "'" + doorAccessibility + "'";
    }

}
