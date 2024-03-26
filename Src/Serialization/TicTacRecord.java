package Serialization;

import model.GameStateLogic;
import view.ScoreKeeper;

import javax.swing.*;
import java.io.Serializable;

/**
 * The TicTacWrapper class serves as a wrapper for serializing and deserializing the game state,
 * current score, board dimensions, light interval, and warning message of a Tic Tac Toe game.
 */
public record TicTacRecord(GameStateLogic model, ScoreKeeper scoreRecord, int height,
                           int length, JButton winningButton, int lightInterval,
                           String warningMessage) implements Serializable {
}