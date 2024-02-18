import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class ScoreKeeper {
    private Properties scores;
    private static final String directoryName = "Properties Folder";
    //This is some dark magiks. String manipulations. Spooky.
    private static final String fileName = directoryName + File.separator + "scores.properties";

    String xPlayer = "Player X Default Name";
    String oPlayer= "Player O Default Name";

    public ScoreKeeper(){
        scores = new Properties();
        loadScores();
        newPopUp();
    }

    private void loadScores() {
        //declaring and initializing the fileInputStream in the try assures that the file will be closed when the try resolves
        try(FileInputStream fis = new FileInputStream(fileName)){
            System.out.println("File has been found to exist");
            scores.load(fis);
        }
        catch (FileNotFoundException e){
            // If the file doesn't exist, create a new one
            System.out.println("file has been found to not exist");
            createNewScoresFile();
        }
        catch (IOException e){
            System.out.println("connection seems to have been interrupted");
            e.printStackTrace();
        }
    }

    private void createNewScoresFile(){
        System.out.println("createNewScoresFile() reached");
        File directory = new File(directoryName);
        if(!directory.exists()){
            directory.mkdirs();
        }

        //define an output stream
        try(FileOutputStream fos = new FileOutputStream(fileName)){
            //Create new properties file
            scores = new Properties();
            //save all values from score to the output stream, which will write them to the new file
            scores.store(fos, "Scores");
            System.out.println("New scores.properties file created successfully.");
        }
        catch (IOException ex){
            System.out.println("connection seems to have been interrupted");
            ex.printStackTrace();
        }
    }

    private void newPopUp(){
        // Check if the scores file is not blank
        if (!scores.isEmpty()) {
            // Find the key with the highest value
            String returnMessage = findHighestKey();
            JOptionPane.showMessageDialog(null, returnMessage);
        }

        xPlayer = JOptionPane.showInputDialog("Enter name for X player:");
        oPlayer = JOptionPane.showInputDialog("Enter name for O player:");

        if(xPlayer != null && !xPlayer.isEmpty() && oPlayer != null && !oPlayer.isEmpty()){
            handleNames(xPlayer, oPlayer);
        } else if (xPlayer != null || oPlayer != null) {
            System.out.println("we need a real names, the both of yous");
            newPopUp();
        } else{
            // If the user cancels or closes the window, do nothing
            System.out.println("Window closed or cancelled");
        }
    }

    private void handleNames(String playerX, String playerO){
        System.out.println("handleNames() reached");

        scores.clear();

        scores.setProperty(playerX, "0");
        scores.setProperty(playerO, "0");

        saveScores();
    }

    private String findHighestKey() {
        int xScore = Integer.parseInt(scores.getProperty(xPlayer));
        int oScore = Integer.parseInt(scores.getProperty(oPlayer));

        String returnMessage = "As of last session, the scores were tied.";

        if(xScore > oScore)
            returnMessage = "As of last session, " + xPlayer + "had the most wins.";

        if(oScore > xScore)
            returnMessage = "As of last session, " + oPlayer + "had the most wins.";

        return returnMessage;
    }

    public void incrementScore(Boolean xTurn) {
        String currentPlayer = xTurn ? xPlayer : oPlayer;
        int currentScore = Integer.parseInt(scores.getProperty(currentPlayer));
        scores.setProperty(currentPlayer, String.valueOf(++currentScore));

        saveScores();
    }

    private void saveScores(){
        //Updated info is already in scores, it is now necessary to save them to the file
        try(FileOutputStream fos = new FileOutputStream(fileName)){
            scores.store(fos, "Scores Updated");
        } catch (IOException ex){
            System.out.println("Error occurred while saving scores: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
