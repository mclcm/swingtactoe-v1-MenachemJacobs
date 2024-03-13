package view;

import model.GameStateLogic;
import model.StaticStateVars;

import javax.swing.*;
import java.awt.*;

/**
 * The ButtonPainter class provides methods for painting buttons in a TicTacToe game interface.
 */
public class ButtonPainter {
    /**
     * Repaints the buttons involved in the end game based on the given end game condition.
     *
     * @param buttons          The 2D array of JButtons representing the game board.
     * @param clickedButton    The button that was clicked to trigger the end game condition.
     * @param endGameCondition The condition indicating the type of win (rank, file, diagonal, and/or the ascendants).
     * @param height           The height of the game board.
     * @param length           The length of the game board.
     */
    public static void victoryPainter(JButton[][] buttons, TicTacToe.MyButton clickedButton, int endGameCondition, int height, int length) {
        int loopLimit = Math.min(height, length);

        //rank win.
        if (endGameCondition % StaticStateVars.getWinCode("rank") == 0) {
            // Rank win: repaint buttons in the same row
            for (int i = 0; i < length; i++) {
                dryPaintBtn(buttons[clickedButton.getYPos()][i]);
            }
        }

        //File win.
        if (endGameCondition % StaticStateVars.getWinCode("file") == 0) {
            // File win: repaint buttons in the same column
            for (JButton[] button : buttons) {
                dryPaintBtn(button[clickedButton.getXPos()]);
            }
        }

        //Dexter win.
        if (endGameCondition % StaticStateVars.getWinCode("dexter") == 0) {
            // Dexter win: repaint buttons in the main diagonal
            for (int i = 0; i < loopLimit; i++) {
                dryPaintBtn(buttons[i][i]);
            }
        }

        //Sinister win.
        if (endGameCondition % StaticStateVars.getWinCode("sinister") == 0) {
            // Sinister win: repaint buttons in the secondary diagonal
            for (int i = 0; i < loopLimit; i++) {
                dryPaintBtn(buttons[i][length - 1 - i]);
            }
        }

        //special checks for where board is not a square
        if (height != length) {
            //DexterAscendant win.
            if (endGameCondition % StaticStateVars.getWinCode("aDexter") == 0) {
                //Dexter Ascendant win: repaint buttons in the ascending diagonal from bottom left to top right
                for (int i = 0; i < loopLimit; i++) {
                    dryPaintBtn(buttons[height - 1 - i][i]);
                }
            }

            //SinisterAscendant win.
            if (endGameCondition % StaticStateVars.getWinCode("aSinister") == 0) {
                //Sinister Ascendant win: repaint buttons in the ascending diagonal from top left to bottom right
                for (int i = 0; i < loopLimit; i++) {
                    dryPaintBtn(buttons[height - 1 - i][length - 1 - i]);
                }
            }
        }
    }

    /**
     * Repaints the given button to indicate its state during the end game.
     *
     * @param btnToPaint The button to repaint.
     */
    private static void dryPaintBtn(JButton btnToPaint) {
        btnToPaint.setEnabled(true);
        btnToPaint.setBackground(Color.orange);
    }

    /**
     * Reloads the state of the buttons based on the provided game state.
     *
     * @param gameButtons   The 2D array of JButtons representing the game board.
     * @param analogueBoard The GameStateLogic object representing the game state.
     */
    static void reloadPainter(JButton[][] gameButtons, GameStateLogic analogueBoard) {
        int buttonVal;
        TicTacToe.MyButton castedButton;

        for (JButton[] buttonRow : gameButtons) {
            for (JButton button : buttonRow) {
                castedButton = (TicTacToe.MyButton) button;
                buttonVal = analogueBoard.getCellVal(castedButton.getXPos(), castedButton.getYPos());


                if (buttonVal != StaticStateVars.getCellDefaultVal()) {
                    castedButton.setText(analogueBoard.buttonTextSetter(buttonVal));
                    castedButton.setEnabled(false);
                }
            }
        }
    }
}
