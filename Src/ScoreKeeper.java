import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ScoreKeeper {
    private Properties scores;
    private static final String fileName = "scores.properties";

    public ScoreKeeper(){
        scores = new Properties();
        loadScores();
    }

    private void loadScores() {
        //declaring and initializing the fileInputStream in the try assures that the file will be closed when the try resolves
        try(FileInputStream fis = new FileInputStream(fileName)){
            System.out.println("File has been found to exist");
            scores.load(fis);
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

    public void promptUserForName(){
        String name = JOptionPane.showInputDialog("Enter Your Name");
        if(name != null && !name.isEmpty()){
            System.out.println("well done");
        }
        else{
            System.out.println("we need a real name");
            promptUserForName();
        }
    }
}
