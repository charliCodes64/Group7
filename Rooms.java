import java.util.ArrayList;

public class Rooms {

    private String roomDescription;
    private ArrayList<Doors> doors;

    public Rooms (String roomDescription){
        this.roomDescription = roomDescription;
        this.doors = new ArrayList<>();
    }
    public void addDoors(Doors door){
        doors.add(door);
    }

    public String getDescription(){
        return roomDescription;
    }

    public ArrayList<Doors> getDoors(){
        return doors;
    }

}
