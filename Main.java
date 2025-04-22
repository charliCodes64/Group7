public class Main {

    public static void main(String[] args){
        // frame only needs to be called in here
        new Frame();
    }
}
//The first line in the document is the room the individual starts in
//The second line is the door that appears on the left "DoorL"
//The third line is the door that appears on the right "DoorR"
//After the ":" is the description for both the doors and rooms
//The text inside "()" for the doors is for the action that is printed once they player chooses the door
//The word "true" or "false" at the end of the DoorL or DoorR line indicates if it is the right door
//If the door is false they cannot continue if the door is true they can
//File format below is not the game only an example
//Attended file format below

//Room:You are in the first room of the maze
//DoorL:oak plank door (The Door catches fire once touched)'false'
//DoorR:purple door appears (A new room appears dark and damp)'true'
//==========================================================================
//Room:You have made it to the second room
//DoorL:Stone door (A lonely cellar appears)'true'
//DoorR:Glass door (The door shatters)'false'