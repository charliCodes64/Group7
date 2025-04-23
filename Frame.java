import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//This is where the file should be read in and Gui should be assigned actions
public class Frame extends JFrame implements ActionListener {

    private JPanel panel;
    private JTextArea display;
    private JButton leftDoorButton, rightDoorButton;
    private int currRoomIndex = 0; //used to count number of rooms gone through before quitting or winning
    ArrayList <Rooms> rooms = new ArrayList<>();

    //feel free to change window cosmetics
    public Frame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.setLayout(new BorderLayout());

        display = new JTextArea(10, 40);
        display.setEditable(false);
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        display.setFont(new Font("Arial Black", Font.PLAIN, 18));
        this.add(display, BorderLayout.CENTER);


        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        leftDoorButton = new JButton("Left");
        leftDoorButton.addActionListener(this);

        rightDoorButton = new JButton("Right");
        rightDoorButton.addActionListener(this);

        panel.add(leftDoorButton);
        panel.add(rightDoorButton);
        this.add(panel, BorderLayout.SOUTH);

        this.setVisible(true);

        rooms = loadFromFile("Rooms.txt");
        currentRoom();
    }

    //for left and right buttons and delays time between choices so its easier to follow
    public void actionPerformed(ActionEvent e) {
        Rooms currentRoom = rooms.get(currRoomIndex);
        ArrayList<Doors> doors = currentRoom.getDoors();
        Doors chosenDoor = null;
        boolean success = false;

        if (e.getSource() == leftDoorButton && doors.size() > 0) {
            chosenDoor = doors.get(0);
            success = chosenDoor.DoorAccessibilityCorrect();
        } else if (e.getSource() == rightDoorButton && doors.size() > 1) {
            chosenDoor = doors.get(1);
            success = chosenDoor.DoorAccessibilityCorrect();
        }

        leftDoorButton.setEnabled(false);
        rightDoorButton.setEnabled(false);

        if (success) {
            String description = chosenDoor.getDescription();
            String doorActionP = "";
            int start = description.indexOf('(');
            int end = description.indexOf(')');
            if (start != -1 && end != -1 && end > start) {
                doorActionP = description.substring(start+1, end);
            }

            display.setText(doorActionP);

            Timer nextRoomTimer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    currRoomIndex++;
                    if (currRoomIndex < rooms.size()) {
                        currentRoom();
                    } else {
                        display.setText("You have made it out!");
                    }
                }
            });
            nextRoomTimer.setRepeats(false);
            nextRoomTimer.start();
        } else {
            String description = chosenDoor.getDescription();
            String doorActionF = "";
            int start = description.indexOf('(');
            int end = description.indexOf(')');
            if (start != -1 && end != -1 && end > start) {
                doorActionF = description.substring(start+1, end);
            }

            display.setText(doorActionF);

            Timer timer = new Timer(3000, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    currRoomIndex = 0;
                    currentRoom();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    // Method to show the current room and update the GUI as the player chooses the correct door
    private void currentRoom() {
        Rooms currentRoom = rooms.get(currRoomIndex);
        ArrayList<Doors> doors = currentRoom.getDoors();

        String roomText = currentRoom.getDescription() + "\n \n";

        if (doors.size() > 0) {
            String leftDoorDescription = doors.get(0).getDescription().replaceAll("\\(.*?\\)", "").replaceAll("'(true|false)'", "");
            roomText += "On your left is a " + leftDoorDescription + "\n";
        }

        if (doors.size() > 1) {
            String rightDoorDescription = doors.get(1).getDescription().replaceAll("\\(.*?\\)", "").replaceAll("'(true|false)'", "");
            roomText += "On your right is a " + rightDoorDescription + "\n";
        }

        display.setText(roomText);
        leftDoorButton.setEnabled(true);
        rightDoorButton.setEnabled(true);
    }

    // Example method to load rooms from a file
    //loads the file into the program and assigns lines based on what they are
    /*private ArrayList<Rooms> loadFromFile(String filename) {
        ArrayList<Rooms> rooms = new ArrayList<>();
        Rooms currentRoom = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Room:")) {
                    if (currentRoom != null) {
                        rooms.add(currentRoom);
                    }
                    String roomDescription = line.substring("Room:".length());
                    currentRoom = new Rooms(roomDescription);
                } else if (line.startsWith("DoorL") && currentRoom != null) {
                    String[] parts = line.split(":");
                    String description = parts[1];
                    boolean success = description.toLowerCase().contains("'true'");
                    currentRoom.addDoors(new Doors(description, success));
                }
            }
            if (currentRoom != null) {
                rooms.add(currentRoom);
            }
        } catch(IOException e) {
            System.out.println("OIII WHAT ARE YA DOIN I CANT READ THAT!!!");
        }

        return rooms;
    }*/
    private ArrayList<Rooms> loadFromFile(String filename) {
        ArrayList<Rooms> rooms = new ArrayList<>();
        Rooms currentRoom = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("===")) {
                    continue; // Skip empty lines and separators
                }

                if (line.startsWith("Room:")) {
                    if (currentRoom != null) {
                        rooms.add(currentRoom);
                    }
                    String roomDescription = line.substring("Room:".length()).trim();
                    currentRoom = new Rooms(roomDescription);
                } else if (line.startsWith("DoorL:") && currentRoom != null) {
                    String description = line.substring("DoorL:".length()).trim();
                    boolean success = description.endsWith("'true'");
                    description = description.replaceAll("'true'|'false'", "").trim();
                    currentRoom.addDoors(new Doors("Left", description, success));
                } else if (line.startsWith("DoorR:") && currentRoom != null) {
                    String description = line.substring("DoorR:".length()).trim();
                    boolean success = description.endsWith("'true'");
                    description = description.replaceAll("'true'|'false'", "").trim();
                    currentRoom.addDoors(new Doors("Right", description, success));
                }
            }
            if (currentRoom != null) {
                rooms.add(currentRoom);
            }
        } catch(IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return rooms;
    }

}
