package view;

import Serialization.SerializeGame;
import model.GameStateLogic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameButtonBuilder {

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

    public static JButton buildSaveButton(TicTacToe view, GameStateLogic gameState, ScoreKeeper winsRecord){
        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SerializeGame.serialize(view, gameState, winsRecord);
            }
        });

        return saveButton;
    }

    //TODO this takes the wrong param, should instead take a file name, or something like that, to identify which game to load
    public static JButton buildLoadButton(TicTacToe view){
        JButton loadButton = new JButton("Load");

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SerializeGame.deserialize();
            }
        });

        return loadButton;
    }
}
