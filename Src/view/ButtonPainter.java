package view;

import model.GameStateLogic;

import javax.swing.*;
import java.awt.*;

public class ButtonPainter {
    /**
     * Repaints the buttons involved in the end game based on the given end game condition.
     *
     * @param clickedButton    The button that was clicked to trigger the end game condition.
     * @param endGameCondition The condition indicating the type of win (rank, file, diagonal, and/or the ascendants).
     */
    public static void victoryPainter(JButton[][] buttons, TicTacToe.MyButton clickedButton, int endGameCondition, int height, int length) {
        int loopLimit = Math.min(height, length);

        //2 is the code for a rank win.
        if (endGameCondition % GameStateLogic.getWinCode("rank") == 0) {
            // Rank win: repaint buttons in the same row
            for (int i = 0; i < length; i++) {
                dryPaintBtn(buttons[clickedButton.getYPos()][i]);
            }
        }

        //3 a file win.
        if (endGameCondition % GameStateLogic.getWinCode("file") == 0) {
            // File win: repaint buttons in the same column
            for (JButton[] button : buttons) {
                dryPaintBtn(button[clickedButton.getXPos()]);
            }
        }

        //5 is the code for a dexter win.
        if (endGameCondition % GameStateLogic.getWinCode("dexter") == 0) {
            // Dexter win: repaint buttons in the main diagonal
            for (int i = 0; i < loopLimit; i++) {
                dryPaintBtn(buttons[i][i]);
            }
        }

        //7 is the code for a sinister win.
        if (endGameCondition % GameStateLogic.getWinCode("sinister") == 0) {
            // Sinister win: repaint buttons in the secondary diagonal
            for (int i = 0; i < loopLimit; i++) {
                dryPaintBtn(buttons[i][length - 1 - i]);
            }
        }

        if (height != length) {
            //11 is the code for a dexterAscendant win.
            if (endGameCondition % GameStateLogic.getWinCode("aDexter") == 0) {
                // Dexter Ascendant win: repaint buttons in the ascending diagonal from bottom left to top right
                for (int i = 0; i < loopLimit; i++) {
                    dryPaintBtn(buttons[height - 1 - i][i]);
                }
            }

            //13 is the code for a sinisterAscendant win.
            if (endGameCondition % GameStateLogic.getWinCode("aSinister") == 0) {
                // Sinister Ascendant win: repaint buttons in the ascending diagonal from top left to bottom right
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
}
