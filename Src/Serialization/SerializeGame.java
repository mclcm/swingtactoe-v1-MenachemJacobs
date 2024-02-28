package Serialization;

import model.GameStateLogic;
import view.ScoreKeeper;
import view.TicTacViewParent;

import javax.swing.*;
import java.io.*;
import java.nio.channels.ScatteringByteChannel;

public class SerializeGame implements Serializable {
    private static final String directoryName = "SavedGames"; // Directory name for properties file.

    //Uses string manipulation wizardry to put file in the correct folder
    private static final String fileName = directoryName + File.separator + "savedGame.ser";

    public static void serialize(TicTacViewParent view, GameStateLogic gameState, ScoreKeeper winsRecord) {
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
        }
        else{
            try(ObjectInputStream objInStream = new ObjectInputStream(new FileInputStream(fileName))){
                TicTacViewParent view = (TicTacViewParent) objInStream.readObject();
                GameStateLogic model = (GameStateLogic) objInStream.readObject();
                ScoreKeeper scoreKeeper = (ScoreKeeper) objInStream.readObject();

                view.deSerializeGame(model, scoreKeeper);
                view.setVisible(true);

                System.out.println("Deserialized success");
            }
            catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }

        }
    }
    private static void directoryManagement() {
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
