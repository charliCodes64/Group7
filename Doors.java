public class Doors {

    private String doorDescription;
    //below if door is 'false' in txt doc they should not be able to continue and must restart
    private Boolean doorAccessibilityCorrect;

    Doors(String doorDescription, boolean doorAccessibilityCorrect){
        this.doorDescription = doorDescription;
        this.doorAccessibilityCorrect = doorAccessibilityCorrect;
    }
    public String getDescription(){
    }
    public boolean DoorAccessibilityCorrect(){
    }

    //below the method should make a new file and write which room the player failed at
    public void failedAttemptWriter(String failedRoomDescription){

    }

}
