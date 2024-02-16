import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class ScoreKeeper {
    private Properties scores;
    private static final String directoryName = "Properties Folder";
    //This is some dark magiks. String manipulations. Spooky.
    private static final String fileName = directoryName + File.separator + "scores.properties";

    public ScoreKeeper(){
        scores = new Properties();
        loadScores();
    }

    private void loadScores() {
        //declaring and initializing the fileInputStream in the try assures that the file will be closed when the try resolves
        try(FileInputStream fis = new FileInputStream(fileName)){
            System.out.println("File has been found to exist");
            scores.load(fis);

            //prompt user for name after opening file
            promptUserForName();
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

            //prompt user for name after creating the file
            promptUserForName();
        }
        catch (IOException ex){
            System.out.println("connection seems to have been interrupted");
            ex.printStackTrace();
        }
    }

    private void promptUserForName(){
        String name = JOptionPane.showInputDialog("Enter Your Name");

        if(name != null && !name.isEmpty()){
            handleName(name);
        } else if (name != null) {
            System.out.println("we need a real name");
            promptUserForName();
        } else{
            // If the user cancels or closes the window, do nothing
            System.out.println("Window closed or cancelled");
        }
    }

    private void handleName(String passedName){
        System.out.println("handleName() reached");
        if(scores.contains(passedName)){
            int currentScore = Integer.parseInt(scores.getProperty(passedName));
            scores.setProperty(passedName, String.valueOf(currentScore + 1));
            System.out.println(passedName + " has won " + currentScore++ + " times");
        } else {
            scores.setProperty(passedName, "1");
            System.out.println(passedName + " has never won before");
        }

        saveScore();
    }

    private void saveScore(){
        return;
    }
}
