package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelBuilder {

    /**
     * Arranges the header panel containing save and load buttons, and the game title.
     * <p>
     * This method creates and configures a JPanel to hold the header components.
     * It adds save and load buttons for game state management, and a title label displaying the game title.
     *
     * @return The JPanel containing the header components.
     */
    public static JPanel arrangeHeadPanel(TicTacToe myGame) {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());

        JPanel headerButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton saveButton = GameButtonBuilder.buildLorSButton(myGame, true);
        JButton loadButton = GameButtonBuilder.buildLorSButton(myGame, false);

        headerButtons.add(saveButton);
        headerButtons.add(loadButton);

        JLabel title = new JLabel("TicTacToe");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(headerButtons, BorderLayout.LINE_START);
        headerPanel.add(title, BorderLayout.CENTER);

        return headerPanel;
    }

    /**
     * Arranges the button panel containing the Tic Tac Toe game buttons.
     * <p>
     * This method configures a JPanel to hold the game buttons for the game window.
     * It sets up the layout and dimensions of the panel and initializes the game buttons using the GameButtonBuilder class.
     * The game buttons are attached to the panel, and their click event handler is set to the mouseClickHandler method
     * of the TicTacToe class.
     *
     * @return The JPanel containing the game buttons.
     */
    public static JPanel arrangeButtonPanel(JButton[][] gameButtons, int height, int length, ActionListener mouseClickHandler) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setPreferredSize(new Dimension(500, 200));

        //This line takes the buttonPanel and attaches the game buttons to it.
        gameButtons = GameButtonBuilder.initGameButtons(buttonPanel, mouseClickHandler, height, length);

        return buttonPanel;
    }

    /**
     * Arranges the status panel containing the game state label and restart button.
     * <p>
     * This method configures a JPanel to hold the game state label and restart button.
     * It initializes a JLabel to display the current game state, adds a restart button
     * for restarting the game, and adds a warning pad (if applicable) for displaying warnings or messages during the game.
     *
     * @return The JPanel containing the game state label and restart button.
     */
    public static JPanel arrangeStatusPanel(JLabel lbl, ) {
        lbl = new JLabel(gameState.lblUpdater());

        JPanel labelPanel = new JPanel();
        JButton restartButton = new JButton("Start over?");
        restartButton.addActionListener(e -> restartGame());

        labelPanel.setPreferredSize(new Dimension(500, 50));

        labelPanel.add(lbl);
        labelPanel.add(restartButton);

        MovePressure warningPad = (lightInterval != -1 && !warningMessage.equals("\n")) ? new MovePressure(lightInterval, warningMessage) : new MovePressure();
        myTimer = warningPad;

        labelPanel.add(warningPad);

        return labelPanel;
    }
}
