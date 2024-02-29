package Serialization;

import model.GameStateLogic;
import view.ScoreKeeper;
import view.TicTacViewParent;

import java.io.*;

public record TicTacWrapper(GameStateLogic model, ScoreKeeper currentScore, int height,
                            int length) implements Serializable {
}