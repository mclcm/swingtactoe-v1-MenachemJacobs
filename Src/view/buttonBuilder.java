package view;

import javax.swing.*;
import java.awt.*;

public class buttonBuilder {
    /**
     * Initializes the buttons grid.
     */
    public static JPanel initButtons(JFrame ticTacToe, int height, int length) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JButton[][] buttons = new JButton[height][length];

        // Initialize buttons and add ActionListener
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                buttons[i][j] = new TicTacToe.MyButton(j, i);
                //buttons[i][j].addActionListener(this::mouseClickHandler);
                buttonPanel.add(buttons[i][j]);
            }
        }

        return buttonPanel;
    }
}
