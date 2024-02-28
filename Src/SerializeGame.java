import javax.swing.*;
import java.io.*;

public class SerializeGame implements Serializable {
    public static void serialize(JFrame view, GameStateLogic gameState) {
        //add code here to serialize object
        try {
            //This is a generic out-stream to file
            FileOutputStream fileOutStream = new FileOutputStream("gameState.ser");
            //This will write objects to the out-stream
            ObjectOutputStream objOutStream = new ObjectOutputStream(fileOutStream);

            //Write the objects to the out-stream with the object writer
            objOutStream.writeObject(view);
            objOutStream.writeObject(gameState);

            //glad to see c standard close file sticks around
            objOutStream.close();
            fileOutStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
