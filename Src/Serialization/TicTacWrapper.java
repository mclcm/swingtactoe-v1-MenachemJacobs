package Serialization;

import model.GameStateLogic;
import view.ScoreKeeper;

import java.io.*;

public record TicTacWrapper(GameStateLogic model, ScoreKeeper currentScore, int height,
                            int length, int lightInterval, String warningMessage) implements Serializable {
}