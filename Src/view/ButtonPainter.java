package view;

import model.GameStateLogic;
import model.StaticStateVars;

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

        //rank win.
        if (endGameCondition % StaticStateVars.getWinCode("rank") == 0) {
            // Rank win: repaint buttons in the same row
            for (int i = 0; i < length; i++) {
                dryPaintBtn(buttons[clickedButton.getYPos()][i]);
            }
        }

        //file win.
        if (endGameCondition % StaticStateVars.getWinCode("file") == 0) {
            // File win: repaint buttons in the same column
            for (JButton[] button : buttons) {
                dryPaintBtn(button[clickedButton.getXPos()]);
            }
        }

        //dexter win.
        if (endGameCondition % StaticStateVars.getWinCode("dexter") == 0) {
            // Dexter win: repaint buttons in the main diagonal
            for (int i = 0; i < loopLimit; i++) {
                dryPaintBtn(buttons[i][i]);
            }
        }

        //sinister win.
        if (endGameCondition % StaticStateVars.getWinCode("sinister") == 0) {
            // Sinister win: repaint buttons in the secondary diagonal
            for (int i = 0; i < loopLimit; i++) {
                dryPaintBtn(buttons[i][length - 1 - i]);
            }
        }

        if (height != length) {
            //dexterAscendant win.
            if (endGameCondition % StaticStateVars.getWinCode("aDexter") == 0) {
                // Dexter Ascendant win: repaint buttons in the ascending diagonal from bottom left to top right
                for (int i = 0; i < loopLimit; i++) {
                    dryPaintBtn(buttons[height - 1 - i][i]);
                }
            }

            //sinisterAscendant win.
            if (endGameCondition % StaticStateVars.getWinCode("aSinister") == 0) {
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
