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
        return this.doorDirection;
    }
    public boolean getDoorAccessibility(){
        boolean temp = true;
        return temp;//this isnt right just a temp holding to not throw return error
    }

    //below the method should make a new file and write which room the player failed at
    public void failedAttemptWriter(String failedRoomDescription){

    }
    public String toString(){
        String temp = "temp";
        return temp;//this isnt right just a temp holding to not throw return error
    }

}
