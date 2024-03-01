package Serialization;

import model.GameStateLogic;
import view.ScoreKeeper;

import java.io.*;

/**
 * The SerializeGame class handles serialization and deserialization of game states.
 */
public class SerializeGame {
    private static final String directoryName = "SavedGames"; // Directory name for properties file.


    /**
     * Serializes the game state, including the GameStateLogic and ScoreKeeper objects,
     * into a file specified by the fileName variable.
     * If the file doesn't exist, it creates one. If the file already exists, it overwrites it.
     *
     * @param fN         The filename for the serialized game.
     * @param gameState  The GameStateLogic object representing the game state.
     * @param winsRecord The ScoreKeeper object representing the game score.
     * @param height     The height of the game board.
     * @param length     The length of the game board.
     */
    public static void serialize(String fN, GameStateLogic gameState, ScoreKeeper winsRecord, int height, int length) {
        String fileName = directoryName + File.separator + fN + ".ser";
        directoryManagement();

        //add code here to serialize object
        try {
            //Create a file output stream and an object output stream
            FileOutputStream fileOutStream = new FileOutputStream(fileName);
            ObjectOutputStream objOutStream = new ObjectOutputStream(fileOutStream);

            //Create a wrapper object and write it to the object output stream
            TicTacWrapper container = new TicTacWrapper(gameState, winsRecord, height, length);
            objOutStream.writeObject(container);

            //Close the streams
            objOutStream.close();
            fileOutStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes the game state from a file.
     *
     * @param fN The filename of the serialized game state.
     * @return The deserialized TicTacWrapper object containing the game state, or {@code null} if deserialization fails.
     * @throws FileNotFoundException If the file containing the serialized game state is not found.
     */
    public static TicTacWrapper deserialize(String fN) {
        File savedGame = new File(directoryName + File.separator + fN);

        if (!savedGame.exists()) {
            System.out.println("File not found");
        } else {
            System.out.println("File found");

            try (ObjectInputStream objInStream = new ObjectInputStream(new FileInputStream(savedGame))) {
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
