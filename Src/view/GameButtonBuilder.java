package view;

import Serialization.SerializeGame;
import Serialization.TicTacWrapper;
import model.GameStateLogic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
     * @param gamePill   The Wrapped elements of the game to be saved
     * @return The "Save" JButton.
     */
    public static JButton buildSaveButton(TicTacWrapper gamePill) {
        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(e -> sButtonFunctionality(gamePill));

        return saveButton;
    }

    /**
     * Performs the functionality of the "Save" button, allowing the user to save the game state.
     *
     * @param gamePill   The Wrapped elements of the game to be saved.
     */
    private static void sButtonFunctionality(TicTacWrapper gamePill) {
        String saveFileName = JOptionPane.showInputDialog("What name do you want to save the file under?");

        if (saveFileName != null && !saveFileName.trim().isEmpty())
            SerializeGame.serialize(saveFileName, gamePill);
    }

    /**
     * Builds a "Load" JButton with an ActionListener to restore a previously saved game state.
     *
     * @param currentGame The current TicTacToe game instance.
     * @return The "Load" JButton.
     */
    public static JButton buildLoadButton(TicTacToe currentGame) {
        JButton loadButton = new JButton("Load");

        loadButton.addActionListener(e -> lButtonFunctionality(currentGame));

        return loadButton;
    }

    /**
     * Restores a previously saved game state and updates the current game instance.
     *
     * @param currentGame The current TicTacToe game instance.
     */
    private static void lButtonFunctionality(TicTacToe currentGame) {
        JFileChooser gamesMenu = new JFileChooser("savedGames");
        String gameToRestore;
        TicTacWrapper container = null;

        gamesMenu.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Show open dialog
        int returnValue = gamesMenu.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            gameToRestore = gamesMenu.getSelectedFile().getName();
            container = SerializeGame.deserialize(gameToRestore);
        }

        if (container != null) {
            setUpNewGame(container, currentGame);
        } else {
            System.out.println("Somehow an empty shell has been saved");
        }
    }

    /**
     * Sets up a new game based on the provided game container and updates the current game instance.
     *
     * @param container    The TicTacWrapper containing the game state.
     * @param currentGame  The current TicTacToe game instance.
     */
    public static void setUpNewGame(TicTacWrapper container, TicTacToe currentGame) {
        GameStateLogic model = container.model();
        ScoreKeeper scoreKeeper = container.currentScore();
        int height = container.height();
        int length = container.length();
        int lightInterval = container.lightInterval();
        String warningMessage = container.warningMessage();

        TicTacToe gameRestored = new TicTacToe(model, scoreKeeper, height, length, lightInterval, warningMessage);
        gameRestored.setVisible(true);

        currentGame.dispose();
    }
}
