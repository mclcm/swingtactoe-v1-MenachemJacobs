package Serialization;

import model.GameStateLogic;
import view.ScoreKeeper;

import java.io.*;

/**
 * The SerializeGame class handles serialization and deserialization of game states.
 */
public class SerializeGame {

    /**
     * The directory name for saving serialized game files.
     */
    private static final String directoryName = "SavedGames"; // Directory name for properties file.

    /**
     * Serializes the game state, including the GameStateLogic and ScoreKeeper objects,
     * into a file specified by the fileName variable.
     * If the file doesn't exist, it creates one. If the file already exists, it overwrites it.
     *
     * @param fN         The filename for the serialized game.
     * @param gamePill   The Wrapped elements of the game to be saved
     */
    public static void serialize(String fN, TicTacWrapper gamePill) {
        String fileName = directoryName + File.separator + fN + ".ser";
        directoryManagement();

        try {
            //Create a file output stream and an object output stream
            FileOutputStream fileOutStream = new FileOutputStream(fileName);
            ObjectOutputStream objOutStream = new ObjectOutputStream(fileOutStream);

            //Takes a wrapper object and writes it to the object output stream
            objOutStream.writeObject(gamePill);

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
