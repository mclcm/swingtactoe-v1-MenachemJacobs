import Serialization.SerializeGame;
import model.GameStateLogic;
import view.ScoreKeeper;
import view.TicTacViewParent;

import javax.swing.*;

public class TestFrameBuilder {
    public static void main(String[] args){
        System.out.println("Hello World!");

        SerializeGame.serialize(new GameStateLogic(3, 3), new ScoreKeeper(), 3, 3);
    }
}
