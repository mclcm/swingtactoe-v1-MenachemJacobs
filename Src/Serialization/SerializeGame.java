package Serialization;

import model.GameStateLogic;
import view.ScoreKeeper;

import javax.swing.*;
import java.io.*;

public class SerializeGame implements Serializable {
    private static final String directoryName = "SavedGames"; // Directory name for properties file.

    //Uses string manipulation wizardry to put file in the correct folder
    private static final String fileName = directoryName + File.separator + "savedGame.ser";

    public static void serialize(JFrame view, GameStateLogic gameState, ScoreKeeper winsRecord) {
        directoryManagement();

        //add code here to serialize object
        try {
            //This is a generic out-stream to file
            FileOutputStream fileOutStream = new FileOutputStream(fileName);
            //This will write objects to the out-stream
            ObjectOutputStream objOutStream = new ObjectOutputStream(fileOutStream);

            //Write the objects to the out-stream with the object writer
            objOutStream.writeObject(view);
            objOutStream.writeObject(gameState);
            objOutStream.writeObject(winsRecord);

            //glad to see c standard close file sticks around
            objOutStream.close();
            fileOutStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deserialize(){
        File savedGame = new File(fileName);

        if(!savedGame.exists()){
            System.out.println("File not found");
            return;
        }
        else{
            System.out.println("File located");
        }
    }
    private static void directoryManagement() {
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
