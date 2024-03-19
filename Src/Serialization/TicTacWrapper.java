package Serialization;

import model.GameStateLogic;
import view.ScoreKeeper;
import view.TicTacToe;

import java.io.*;

/**
 * The TicTacWrapper class serves as a wrapper for serializing and deserializing the game state,
 * current score, board dimensions, light interval, and warning message of a Tic Tac Toe game.
 */
public record TicTacWrapper(GameStateLogic model, ScoreKeeper currentScore, int height,
                            int length, TicTacToe.MyButton winningButton, int lightInterval, String warningMessage) implements Serializable {
}