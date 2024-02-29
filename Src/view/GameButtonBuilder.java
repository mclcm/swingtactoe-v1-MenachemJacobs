package view;

import Serialization.SerializeGame;
import Serialization.TicTacWrapper;
import model.GameStateLogic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Utility class for creating game buttons and associated actions.
 */
public class GameButtonBuilder {

    /**
     * Initializes buttons for the game board and attaches an ActionListener to each button.
     *
     * @param buttonPanel The JPanel where buttons will be added.
     * @param ae          The ActionListener to be attached to each button.
     * @param height      The height of the game board.
     * @param length      The length of the game board.
     * @return A 2D array of initialized JButtons representing the game board.
     */
    public static JButton[][] initButtons(JPanel buttonPanel, ActionListener ae, int height, int length) {

        JButton[][] buttons = new JButton[height][length];

        // Initialize buttons and add ActionListener
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                buttons[i][j] = new TicTacToe.MyButton(j, i);
                buttons[i][j].addActionListener(ae);
                buttonPanel.add(buttons[i][j]);
            }
        }

        return buttons;
    }

    /**
     * Builds a "Save" JButton with an ActionListener to serialize the game state.
     *
     * @param gameState  The GameStateLogic representing the game state.
     * @param winsRecord The ScoreKeeper representing the game score.
     * @param height     The height of the game board.
     * @param length     The length of the game board.
     * @return The "Save" JButton.
     */
    public static JButton buildSaveButton(GameStateLogic gameState, ScoreKeeper winsRecord, int height, int length){
        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SerializeGame.serialize(gameState, winsRecord, height, length);
            }
        });

        return saveButton;
    }

    /**
     * Builds a "Load" JButton with an ActionListener to restore a previously saved game state.
     *
     * @param currentGame   The current TicTacToe game instance.
     * @param gameToRestore The identifier of the game to be restored (e.g., filename).
     * @return The "Load" JButton.
     */
    public static JButton buildLoadButton(TicTacToe currentGame, String gameToRestore){
        JButton loadButton = new JButton("Load");

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restoreGame(currentGame, gameToRestore);
            }
        });

        return loadButton;
    }

    /**
     * Restores a previously saved game state and updates the current game instance.
     *
     * @param currentGame  The current TicTacToe game instance.
     * @param gameToRestore The identifier of the game to be restored (e.g., filename).
     */
    private static void restoreGame(TicTacToe currentGame, String gameToRestore){
        TicTacWrapper container = SerializeGame.deserialize();

        if(container != null) {
            GameStateLogic model = container.model();
            ScoreKeeper scoreKeeper = container.currentScore();
            int height = container.height();
            int length = container.length();

            TicTacToe gameRestored = new TicTacToe(model, scoreKeeper, height, length);
            gameRestored.setVisible(true);

            currentGame.dispose();
        }else{
            System.out.println("Some how an empty shell has been saved");
        }
    }
}
