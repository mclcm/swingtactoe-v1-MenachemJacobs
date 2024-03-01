package view;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

/**
 * The view.ScoreKeeper class manages player scores in a Tic Tac Toe game.
 */
public class ScoreKeeper implements Serializable {
    private Properties scores; // Properties object to store player wins for this session
    private static final String directoryName = "Properties Folder"; // Directory name for properties file.

    //Uses string manipulation wizardry to put file in the correct folder
    private final String fileName = directoryName + File.separator + "scores.properties";
    private boolean hasBeenAVictory = false;

    String xPlayer = "Player X Default Name"; // Default name for Player X
    String oPlayer = "Player O Default Name"; // Default name for Player O

    /**
     * Constructs a new view.ScoreKeeper object.
     * Loads scores from the properties file and displays a popup message.
     */
    public ScoreKeeper() {
        //init prop obj
        scores = new Properties();
        //find out stats from last game
        loadScores();
        //build window for prompting player names
        newPopUp();
    }

    /**
     * Loads scores from the properties file.
     * If the file doesn't exist, creates a new one.
     */
    private void loadScores() {
        //declaring and initializing the fileInputStream in the try assures that the file will be closed when the try resolves
        try (FileInputStream fis = new FileInputStream(fileName)) {
            scores.load(fis);
        } catch (FileNotFoundException e) {
            // If the file doesn't exist, create a new one
            createNewScoresFile();
        } catch (IOException e) {
            System.out.println("connection seems to have been interrupted");
            e.printStackTrace();
        }
    }

    /**
     * Creates a new scores file if it doesn't yet exist.
     */
    private void createNewScoresFile() {
        File directory = new File(directoryName);

        //create the directory if it doesn't exist. That should be unnecessary
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //define an output stream
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            //Create new properties file. This might not be necessary based on how the control flow works.
            scores = new Properties();
            //save all values from score to the output stream, which will write them to the new file
            scores.store(fos, "Scores");
        } catch (IOException ex) {
            System.out.println("connection seems to have been interrupted");
            ex.printStackTrace();
        }
    }

    /**
     * Displays the overall winner of the last session with a winner and prompts for player names.
     */
    private void newPopUp() {
        // Check if the scores file is not blank
        if (!scores.isEmpty()) {
            // Find the key with the highest value and return the key in a printable message
            String returnMessage = findHighestKey();
            JOptionPane.showMessageDialog(null, returnMessage);
        }

        miniPopUpLooper();
    }

    /**
     * Prompts the user for player names and handles them recursively until valid names are provided.
     * If both names are legal, they are assigned to xPlayer and oPlayer variables, and the handleNames method is called.
     * If one or more names are illegal (empty or null), the user is prompted again.
     * If no names are provided (both inputs are null), default names are used.
     */
    private void miniPopUpLooper() {
        String input1 = JOptionPane.showInputDialog("Enter name for X player:");

        //if the first input prompt is cancelled out or is blank, the default names are used
        if (input1 == null || input1.trim().isEmpty()) {
            return;
        }

        String input2 = JOptionPane.showInputDialog("Enter name for O player:");

        //Where both names are legal, handle them. If either input is illegal, prompt again.
        if (input2 != null && !input2.trim().isEmpty()) {
            xPlayer = input1;
            oPlayer = input2;
        } else {
            JOptionPane.showMessageDialog(null, "If you are going to use names, they need to not be blank");
            miniPopUpLooper();
        }
    }

    /**
     * Handles provided player names.
     * Clears existing scores and sets initial scores for provided players.
     */
    private void handleNames() {
        scores.clear();

        //init player scores to zero
        scores.setProperty(xPlayer, "0");
        scores.setProperty(oPlayer, "0");

        saveScores();
    }

    /**
     * Finds which player from the last session had the greater score.
     *
     * @return A message indicating the player with the greatest score in the last session that had a winner.
     */
    private String findHighestKey() {
        String returnMessage = "In the last session, the scores were tied.";

        int highestFound = -1;
        int currentScore;
        String returnKey = null;

        for (String key : scores.stringPropertyNames()) {
            currentScore = Integer.parseInt(scores.getProperty(key));

            if (currentScore == highestFound) returnKey = null;

            if (currentScore > highestFound) {
                highestFound = currentScore;
                returnKey = key;
            }
        }

        if (returnKey != null) returnMessage = "In the last session, " + returnKey + " had the most wins.";

        return returnMessage;
    }

    /**
     * Increments the score for the passed player.
     *
     * @param xTurn A boolean indicating if it's Player X's turn.
     */
    public void incrementScore(Boolean xTurn) {
        if (!hasBeenAVictory) {
            hasBeenAVictory = true;
            handleNames();
        }

        String currentPlayer = !xTurn ? xPlayer : oPlayer;
        int currentScore = Integer.parseInt(scores.getProperty(currentPlayer));
        scores.setProperty(currentPlayer, String.valueOf(++currentScore));

        saveScores();
    }

    /**
     * Saves scores to the properties file.
     */
    private void saveScores() {
        //Updated info is already in scores, it is now necessary to save them to the file
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            scores.store(fos, "Scores Updated");
        } catch (IOException ex) {
            System.out.println("Error occurred while saving scores: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
