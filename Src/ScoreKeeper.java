import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ScoreKeeper {
    private Properties scores;

    public ScoreKeeper(){
        scores = new Properties();
        loadScores();
    }

    private void loadScores() {
        //declaring and initializing the fileInputStream in in the try assures that the file will be closed when the try resolves
        try(FileInputStream fis = new FileInputStream("scores.properties")){
            System.out.println("File has been found to exist");
            scores.load(fis);
        }
        catch (IOException e){
            System.out.println("file has been found to not exist");
            e.printStackTrace();
            // If the file doesn't exist, create a new one
            //createNewScoresFile();
        }
    }

    private void createNewScoresFile(){
        System.out.println("file does not exist");
    }
}
