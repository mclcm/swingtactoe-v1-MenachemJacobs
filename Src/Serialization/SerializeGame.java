package Serialization;

import java.io.*;

/**
 * The SerializeGame class handles serialization and deserialization of game states.
 */
public class SerializeGame {

    /**
     * The directory name for saving serialized game files.
     */
    private static final String DIRECTORY_NAME = "SavedGames"; // Directory name for properties file.

    /**
     * Serializes the game state, including the GameStateLogic and ScoreKeeper objects,
     * into a file specified by the fileName variable.
     * If the file doesn't exist, it creates one. If the file already exists, it overwrites it.
     *
     * @param fN       The filename for the serialized game.
     * @param gameRecord The Wrapped elements of the game to be saved
     */
    public static void serialize(String fN, TicTacRecord gameRecord) {
        String fileName = DIRECTORY_NAME + File.separator + fN + ".ser";
        directoryManagement();

        try {
            //Create a file output stream and an object output stream to it
            FileOutputStream fileOutStream = new FileOutputStream(fileName);
            ObjectOutputStream objOutStream = new ObjectOutputStream(fileOutStream);

            //Takes a record and writes it to the object output stream
            objOutStream.writeObject(gameRecord);

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
     * @param fileName The filename of the serialized game state.
     * @return The deserialized TicTacWrapper object containing the game state, or {@code null} if deserialization fails.
     * //TODO how should I handle this
     * @throws FileNotFoundException If the file containing the serialized game state is not found.
     */
    public static TicTacRecord deserialize(String fileName) {
        File savedGame = new File(DIRECTORY_NAME + File.separator + fileName);

        if (!savedGame.exists()) {
            System.out.println("File not found");
        } else {

            try (ObjectInputStream objInStream = new ObjectInputStream(new FileInputStream(savedGame))) {
                return (TicTacRecord) objInStream.readObject();
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
        File directory = new File(DIRECTORY_NAME);

        if (!directory.exists()) directory.mkdir();
    }
}