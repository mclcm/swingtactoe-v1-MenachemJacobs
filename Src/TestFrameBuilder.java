import javax.swing.*;

public class TestFrameBuilder {
    public static void main(String[] args){
        System.out.println("Hello World!");

        SerializeGame.serialize(new JFrame(), new GameStateLogic(3, 3));
    }
}
