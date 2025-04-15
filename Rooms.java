import java.util.ArrayList;

public class Rooms {

    private String roomDescription;
    private ArrayList<Doors> doors;

    public Rooms (String roomDescription){
        this.roomDescription = roomDescription;
    }
    public static void addDoors(Doors door){

    }
    public String getDescription(){
        return this.roomDescription;
    }
    public ArrayList<Doors> getDoors(){
        return this.doors;//this isnt right just a temp holding to not throw return error
    }
    public String toString(){
        String temp = "temp";
        return temp;//this isnt right just a temp holding to not throw return error
    }

}
