import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

//This is where the file should be read in and Gui should be assigned actions
public class Frame extends JFrame implements ActionListener {

    private JPanel panel;
    private JTextArea display;
    private JButton leftDoorButton, rightDoorButton, exitButton;
    private int currRoomIndex = 0; //used to count number of rooms gone through before quitting or winning
    ArrayList <Rooms> rooms = new ArrayList<>();

    //feel free to change window cosmetics
    //Constructor used to display text from file after it is read in
    public Frame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.setLayout(new BorderLayout());

        display = new JTextArea(20, 40);
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

    //for left and right buttons and delays time between choices so it's easier to follow
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
                doorActionP = description.substring(start + 1, end);
            }

            display.setText(doorActionP);

            Timer nextRoomTimer = new Timer(2500, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    currRoomIndex++;
                    if (currRoomIndex < rooms.size()) {
                        currentRoom();
                    } else {
                        //used from https://www.messletters.com/en/text-art/
                        display.setText("░░░░░░░░░░░░░░░░░░░░░░█████████░░░░░░░░░\n" +
                                "░░███████░░░░░░░░░░███▒▒▒▒▒▒▒▒███░░░░░░░\n" +
                                "░░█▒▒▒▒▒▒█░░░░░░░███▒▒▒▒▒▒▒▒▒▒▒▒▒███░░░░\n" +
                                "░░░█▒▒▒▒▒▒█░░░░██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██░░\n" +
                                "░░░░█▒▒▒▒▒█░░░██▒▒▒▒▒██▒▒▒▒▒▒██▒▒▒▒▒███░\n" +
                                "░░░░░█▒▒▒█░░░█▒▒▒▒▒▒████▒▒▒▒████▒▒▒▒▒▒██\n" +
                                "░░░█████████████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██\n" +
                                "░░░█▒▒▒▒▒▒▒▒▒▒▒▒█▒▒▒▒▒▒▒▒▒█▒▒▒▒▒▒▒▒▒▒▒██\n" +
                                "░██▒▒▒▒▒▒▒▒▒▒▒▒▒█▒▒▒██▒▒▒▒▒▒▒▒▒▒██▒▒▒▒██\n" +
                                "██▒▒▒███████████▒▒▒▒▒██▒▒▒▒▒▒▒▒██▒▒▒▒▒██\n" +
                                "█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█▒▒▒▒▒▒████████▒▒▒▒▒▒▒██\n" +
                                "██▒▒▒▒▒▒▒▒▒▒▒▒▒▒█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██░\n" +
                                "░█▒▒▒███████████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██░░░\n" +
                                "░██▒▒▒▒▒▒▒▒▒▒████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█░░░░░\n" +
                                "░░████████████░░░█████████████████░░░░░░\n" + "You made it through the maze!!");
                        JButton exitButton = new JButton("Exit");
                        exitButton.addActionListener(ev -> dispose());
                        panel.add(exitButton);
                        panel.revalidate();
                        panel.repaint();}
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
                doorActionF = description.substring(start + 1, end);
            }

            display.setText(doorActionF + "\n" + " ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣠⠤⠤⠴⠶⢦⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⡴⠶⠛⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠑⢤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⠀⠀⠀⠀⠀⠀⠀⢀⡾⠋⠀⠀⠀⠀⠀⠀⠀⣤⣄⠀⢠⣄⡀⠀⠀⢹⣦⣠⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⠀⠀⠀⠀⠀⢀⡴⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠛⠻⣿⡿⠿⢿⡿⠿⣿⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⠀⠀⠀⠀⣠⡟⠀⢠⡾⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⡾⠋⠀⠀⠈⠳⣄⢺⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⠀⠀⢀⡼⠋⠀⠀⣼⣃⣀⣀⡀⠀⠀⠀⠀⠀⠀⢰⠋⠀⠀⢠⣞⣻⠀⠙⣧⠹⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⠀⢀⣾⠁⠀⣠⣾⠏⠁⠀⠉⠙⠳⣤⡀⠀⠀⠀⢿⡀⠀⠀⠀⠉⠉⠀⠀⠘⣇⢹⣧⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⢀⣿⡏⠀⢺⣿⡿⢀⡀⠀⠀⠀⠀⢨⡇⠀⠀⠀⠈⠻⣄⠀⠀⠀⠀⠀⠀⢀⡿⠀⢻⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⢸⣿⠃⠀⠿⢳⠇⣯⠿⠀⠀⠀⢠⡟⢀⡤⠖⢻⡛⠲⡾⣷⣶⠤⠤⠤⠶⠛⠁⠈⣿⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⢸⣿⠀⠀⠀⣿⣆⠀⠀⠀⢀⡴⠋⣰⣯⠀⠀⠀⣷⠞⠁⠈⢿⣻⣶⣿⠶⣄⠀⠀⠀⢹⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⢸⡇⠀⠀⠛⢡⡉⠑⠒⠒⠋⠀⢠⠇⢸⣧⡤⠞⠁⠀⠀⠀⠀⠉⠉⠁⠀⠙⣆⠀⠀⠈⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⠘⣇⠀⠀⠀⠀⠁⠀⠀⠀⠀⠀⡼⠀⣸⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⠟⠀⠀⠀⠀⢻⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡿⠚⠁⠀⠀⠀⠀⢀⣀⣤⡤⠤⠤⠔⠒⠋⠁⠀⠀⠀⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⠀⢹⣧⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⢀⣠⠶⠚⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⠀⠘⣿⡆⠀⠀⠀⠀⠀⠀⠀⡼⢀⡤⠞⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣀⣀\n" +
                    "⠀⠀⠘⣿⡀⠀⠀⠀⠀⠀⣼⡧⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣠⣄⡀⠀⠀⣿⠀⣤⣤⡀⠀⠀⠀⠀⢠⠉⠙⣿⡏⠈⣿⠀⠙\n" +
                    "⠀⠀⠀⠈⠳⡄⠀⠀⠀⠘⠋⠀⠀⣠⣤⣤⡀⠀⠀⠀⢰⡟⠙⢿⡟⠉⢻⡁⠉⢷⠀⢀⡟⣸⠃⠀⢸⡄⠀⠀⢀⣿⠀⠀⣾⠁⢠⣿⠀⠀\n" +
                    "⠀⠀⠀⠀⠀⠈⠐⠤⣀⠀⠀⠀⢸⡟⠁⠘⣧⠀⠀⠀⣸⠃⠀⢸⡇⠀⣼⠇⠀⢸⣇⣼⠇⡇⠀⠀⢸⣇⠀⢀⡼⠃⠀⣾⠏⢠⡟⠁⠀⠀\n" +
                    "⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠀⠀⢸⠀⠀⠀⣿⠀⢀⣴⡟⠀⣠⡟⠀⣼⠏⠀⠀⢸⡟⠉⠀⣧⠀⠀⠈⠛⠋⠉⠀⠀⠈⠁⠀⠉⠀⠀⢀⣰\n" +
                    "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⠀⠀⠻⠛⠋⠉⠀⠀⠋⠀⠸⠃⠀⠀⢠⠟⠀⠀⠀⣿⡄⠀⠀⠀⠀⠀⠀⣀⡀⠀⠀⠀⣠⠞⠋⠀\n" +
                    "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡰⠞⠁⠀⢀⣤⠞⠋⠀⠀⠀⠀⢀⣤⡾⠋⠀⠀⣠⡾⠋⠀⠀⠀\n" +
                    "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣴⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡴⠋⠀⠀⠀⠀⠉⠀⠀⠀⠀⠀⢠⡾⠛⢁⣠⣴⠶⠛⠉⠀⠀⠀⠀⠀\n" +
                    "⠀⠀⠀⠀⠀⠀⠀⠀⢚⠋⠉⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣴⠾⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");

            chosenDoor.failedAttemptWriter(currRoomIndex + 1);

            Timer timer = new Timer(2500, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    currRoomIndex = 0;
                    currentRoom();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    // Method to show the current room and update the GUI as the player chooses the correct door to the next room
    private void currentRoom() {
        Rooms currentRoom = rooms.get(currRoomIndex);
        ArrayList<Doors> doors = currentRoom.getDoors();

        String roomText = currentRoom.getDescription() + "\n \n \n";

        if (doors.size() > 0) {
            //learned this from "https://stackoverflow.com/questions/5636048/regular-expression-to-replace-content-between-parentheses"
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
    private ArrayList<Rooms> loadFromFile(String filename) {
        ArrayList<Rooms> rooms = new ArrayList<>();
        Rooms currentRoom = null;

        try (BufferedReader read = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = read.readLine()) != null) {
                if (line.startsWith("Room:")) {
                    if (currentRoom != null) {
                        rooms.add(currentRoom);
                    }
                    String roomDescription = line.substring("Room:".length());
                    currentRoom = new Rooms(roomDescription);
                } else if (line.startsWith("Door") && currentRoom != null) {
                    String[] parts = line.split(":");
                    String description = parts[1];
                    boolean correct = description.toLowerCase().contains("'true'");
                    currentRoom.addDoors(new Doors(description, correct));
                }
            }
            if (currentRoom != null) {
                rooms.add(currentRoom);
            }
        }catch(Exception e) {
            System.out.println("OIII WHAT ARE YA DOIN I CANT READ THAT!!!");
        }
        return rooms;
    }

}
