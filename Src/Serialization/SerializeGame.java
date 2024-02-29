package Serialization;

import model.GameStateLogic;
import view.ScoreKeeper;
import view.TicTacViewParent;

import java.io.*;

public class SerializeGame{
    private static final String directoryName = "SavedGames"; // Directory name for properties file.

    //Uses string manipulation wizardry to put file in the correct folder
    private static final String fileName = directoryName + File.separator + "savedGame.ser";

    /**
     * Serializes the game state, including the TicTacViewParent, GameStateLogic, and ScoreKeeper objects,
     * into a file specified by the fileName variable.
     * If the file doesn't exist, it creates one. If the file already exists, it overwrites it.
     *
     * @param gameState The GameStateLogic object representing the game state.
     * @param winsRecord The ScoreKeeper object representing the game score.
     */
    public static void serialize(GameStateLogic gameState, ScoreKeeper winsRecord, int height, int length) {
        directoryManagement();

        //add code here to serialize object
        try {
            //This is a generic out-stream to file
            FileOutputStream fileOutStream = new FileOutputStream(fileName);
            //This will write objects to the out-stream
            ObjectOutputStream objOutStream = new ObjectOutputStream(fileOutStream);

            //Store the input in a wrapper
            TicTacWrapper container = new TicTacWrapper(gameState, winsRecord, height, length);

            //Write the objects to the out-stream with the object writer
            objOutStream.writeObject(container);

            //glad to see c standard close file sticks around
            objOutStream.close();
            fileOutStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes the game state from a file.
     *
     * @return The deserialized TicTacWrapper object containing the game state.
     * @throws FileNotFoundException If the file containing the serialized game state is not found.
     */
    public static TicTacWrapper deserialize() {
        File savedGame = new File(fileName);

        if (!savedGame.exists()) {
            System.out.println("File not found");
        } else {
            try (ObjectInputStream objInStream = new ObjectInputStream(new FileInputStream(fileName))) {
                System.out.println("Deserialized success");

                return (TicTacWrapper) objInStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Creates the directory for storing saved games if it doesn't exist.
     */
    private static void directoryManagement() {
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
