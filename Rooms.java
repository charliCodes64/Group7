import java.util.ArrayList;

public class Rooms {

    private String roomDescription;
    private ArrayList<Doors> doors;

    public Rooms (String roomDescription){
        this.roomDescription = roomDescription;
        this.doors = new ArrayList<>();
    }
    public void addDoors(Doors door){
    }

    public String getDescription(){
    }

    public ArrayList<Doors> getDoors(){
    }

}
