import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//This is where the file should be read in and Gui should be assigned actions
public class Frame extends JFrame implements ActionListener {

    private JPanel panel;
    private JTextArea display;
    private JButton leftDoorButton, rightDoorButton;
    private ArrayList<Rooms> room;
    private int currRoomIndex; //used to count number of rooms gone through before quitting or winning

    public Frame(){

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
    public void currRoom(){

    }
    public void loadFile(String fileName){

    }

}
