package view;

import Serialization.SerializeGame;
import Serialization.TicTacWrapper;
import model.GameStateLogic;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Utility class for creating game buttons and associated actions.
 */
public class GameButtonBuilder {
    /**
     * Custom JButton class with additional properties.
     * <p>
     * The MyButton class extends JButton and adds properties to represent
     * the position of the button on a game board, and getters on those values.
     */
    public static class MyButton extends JButton {

        private final int xPos;     //xPos of this button. Analogous to this button's Length position in the board
        private final int yPos;     //xPos of this button. Analogous to this button's Height position in the board

        /**
         * Constructs a new MyButton.
         *
         * @param xPos The x position of the button.
         * @param yPos The y position of the button.
         */
        public MyButton(int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
        }

        /**
         * Get the x position of the button.
         *
         * @return The x position.
         */
        public int getXPos() {
            return xPos;
        }

        /**
         * Get the y position of the button.
         *
         * @return The y position.
         */
        public int getYPos() {
            return yPos;
        }
    }

    /**
     * Initializes buttons for the game board and attaches an ActionListener to each button.
     *
     * @param buttonPanel The JPanel where buttons will be added.
     * @param ae          The ActionListener to be attached to each button.
     * @param height      The height of the game board.
     * @param length      The length of the game board.
     * @return A 2D array of initialized JButtons representing the game board.
     */
    public static JButton[][] initGameButtons(JPanel buttonPanel, ActionListener ae, int height, int length) {

        JButton[][] buttons = new JButton[height][length];

        // Initialize buttons and add ActionListener
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                buttons[i][j] = new MyButton(j, i);
                buttons[i][j].addActionListener(ae);
                buttonPanel.add(buttons[i][j]);
            }
        }

        return buttons;
    }

    /**
     * Builds a "Load" or a "Save" JButton with an ActionListener to implement proper functionality.
     * A load button will restore a previously saved game state. A save button will serialize the current game state.
     *
     * @param currentGame The current TicTacToe game instance.
     * @param isSaveOp    A boolean value, passed to reflect if a Save of a Load button should be built.
     * @return The "Load" JButton.
     */
    public static JButton buildLorSButton(TicTacToe currentGame, boolean isSaveOp) {
        JButton LSButton = new JButton(isSaveOp ? "Save" : "Load");

        if (isSaveOp)
            LSButton.addActionListener(e -> sButtonFunctionality(currentGame));

        if (!isSaveOp)
            LSButton.addActionListener(e -> lButtonFunctionality(currentGame));

        return LSButton;
    }

    /**
     * Performs the functionality of the "Save" button, allowing the user to save the game state.
     *
     * @param onGoingGame The TicTacToe instance to be saved.
     */
    private static void sButtonFunctionality(TicTacToe onGoingGame) {
        String saveFileName = JOptionPane.showInputDialog("What name do you want to save the file under?");
        TicTacWrapper gamePill = onGoingGame.provideWrapper();

        if (saveFileName != null && !saveFileName.trim().isEmpty())
            SerializeGame.serialize(saveFileName, gamePill);
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
     * @param container   The TicTacWrapper containing the game state.
     * @param currentGame The current TicTacToe game instance.
     */
    public static void setUpNewGame(TicTacWrapper container, TicTacToe currentGame) {
        TicTacToe gameRestored = new TicTacToe(container);
        gameRestored.setVisible(true);

        currentGame.dispose();
    }
}
