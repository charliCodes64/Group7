import java.io.FileWriter;
import java.io.PrintWriter;

public class Doors {

    private String doorDescription;
    //below if door is 'false' in txt doc they should not be able to continue and must restart
    private Boolean doorAccessibility;

    Doors(String doorDescription, boolean doorAccessibility){
        this.doorDescription = doorDescription;
        this.doorAccessibility = doorAccessibility;
    }
    public String getDescription(){
        return this.doorDescription;
    }

    public boolean DoorAccessibilityCorrect() {
        return this.doorAccessibility;
    }

    //below the method should make a new file and write which room in and what door caused them to fail
    public void failedAttemptWriter(int roomNumber) {
        try (FileWriter failed = new FileWriter("failedDoorsByRoom.txt", true);
             PrintWriter writer = new PrintWriter(failed)) {

            String cleanedDescription = this.getDescription().replaceAll("\\(.*?\\)", "").replaceAll("'true'|'false'", "");
            writer.println("The " + cleanedDescription + " was wrong choice in room " + roomNumber + "!");
        } catch (Exception e) {
            System.out.println("OI I CANT WRITE THAT!!!");
        }
    }

//    public String toString(){
//        return doorDirection + ":" + doorDescription + "'" + doorAccessibility + "'";
//    }

}
