package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonBuilder {

    public JButton[][] initButtons(JPanel buttonPanel,ActionListener ae, int height, int length) {

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
}
