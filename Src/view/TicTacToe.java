package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import model.*;

/**
 * This class represents a Tic Tac Toe game application.
 */
public class TicTacToe extends JFrame {
    private final int LENGTH;
    private final int HEIGHT;

    private GameStateLogic gameState;
    private final ScoreKeeper scoreTracker;

    JLabel lbl;
    JButton[][] gameButtons;
    JPanel buttonPanel;

    int lightInterval = -1;
    String warningMessage = "\n";

    MovePressure myTimer;

    /**
     * Constructs a new TicTacToe game.
     */
    public TicTacToe(int lightInterval, String warningMessage) {
        LENGTH = 3;
        HEIGHT = 3;

        gameState = new GameStateLogic(HEIGHT, LENGTH);
        scoreTracker = new ScoreKeeper();

        universalConstruction(lightInterval, warningMessage);
        setVisible(true);
    }

    /**
     * Constructs a TicTacToe game with prior game state and win record.
     *
     * @param priorGame      The prior game state.
     * @param priorWinRecord The prior win record.
     * @param height         The height of the game board.
     * @param length         The length of the game board.
     */
    public TicTacToe(GameStateLogic priorGame, ScoreKeeper priorWinRecord, int height, int length, int lightInterval, String warningMessage) {
        LENGTH = length;
        HEIGHT = height;

        gameState = priorGame;
        scoreTracker = priorWinRecord;

        universalConstruction(lightInterval, warningMessage);
        ButtonPainter.reloadPainter(gameButtons, gameState);
    }

    /**
     * Performs universal construction tasks for setting up the Tic Tac Toe game window.
     * It sets the title, layout, adds panels, sets size and position, and handles resizing.
     * Throws an IllegalArgumentException if the board dimensions are smaller than 1.
     */
    private void universalConstruction(int lightInterval, String warningMessage) {
        if (HEIGHT < 1 || LENGTH < 1)
            throw new IllegalArgumentException("Board can not have dimensions smaller than 1");

        this.lightInterval = lightInterval;
        this.warningMessage = warningMessage;

        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(arrangeHeadPanel(), BorderLayout.NORTH);
        add(arrangeButtonPanel(), BorderLayout.CENTER);
        add(arrangeStatusPanel(), BorderLayout.SOUTH);

        setLocationRelativeTo(null);

        setSize(500, 300);
        //pack();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                handleResize();
            }
        });

        myTimer.start();
    }


    /**
     * Custom JButton class with additional properties.
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
     * Arranges the header panel containing save and load buttons, and the game title.
     *
     * @return The JPanel containing the header components.
     */
    private JPanel arrangeHeadPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());

        JPanel headerButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton saveButton = GameButtonBuilder.buildSaveButton(gameState, scoreTracker, HEIGHT, LENGTH, lightInterval, warningMessage);
        JButton loadButton = GameButtonBuilder.buildLoadButton(this);

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
     *
     * @return The JPanel containing the game buttons.
     */
    private JPanel arrangeButtonPanel() {
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setPreferredSize(new Dimension(500, 200));

        //This line takes the buttonPanel and attaches the game buttons to it.
        gameButtons = GameButtonBuilder.initButtons(buttonPanel, this::mouseClickHandler, HEIGHT, LENGTH);

        return buttonPanel;
    }

    /**
     * Arranges the label panel containing the game state label and restart button.
     *
     * @return The JPanel containing the game state label and restart button.
     */
    private JPanel arrangeStatusPanel() {
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

    /**
     * Handles the mouse click event on buttons.
     *
     * @param ae The MouseEvent object representing the click event.
     */
    private void mouseClickHandler(ActionEvent ae) {
        MyButton clickedButton = (MyButton) ae.getSource();

        //if game is not over and the current button has not been clicked before, update text and label
        if (gameState.getGameState() == 0 && gameState.getValAtPos(clickedButton.getXPos(), clickedButton.getYPos()) == 0) {
            myTimer.restate();

            clickedButton.setEnabled(false);

            // Update text on the clicked button and handle game state logic
            clickedButton.setText(gameState.btnClicked(clickedButton, clickedButton.getXPos(), clickedButton.getYPos()));

            // Update label text to reflect the current game state
            lbl.setText(gameState.lblUpdater());

            //check if game has ended by means other than cats eye
            if (gameState.getGameState() > 0) {
                // Repaint buttons based on the end game condition
                ButtonPainter.victoryPainter(gameButtons, clickedButton, gameState.getGameState(), HEIGHT, LENGTH);
                //win record only increase if game ended for reason other than cats eye
                scoreTracker.incrementScore(gameState.getXTurn());
            }
        }
    }

    /**
     * Restarts the game by clearing the button panel,
     * initializing new buttons, handling resize,
     * and resetting the game state.
     */
    private void restartGame() {
        buttonPanel.removeAll();

        //Reset game state
        gameState = new GameStateLogic(HEIGHT, LENGTH);

        gameButtons = GameButtonBuilder.initButtons(buttonPanel, this::mouseClickHandler, HEIGHT, LENGTH);
        handleResize();
        lbl.setText(gameState.lblUpdater());
    }

    /**
     * Handles resizing of the buttons to fit within the updated panel dimensions.
     */
    private void handleResize() {
        // Calculate new dimensions for buttons based on panel size and number of buttons
        int buttonWidth = buttonPanel.getWidth() / LENGTH;
        int buttonHeight = buttonPanel.getHeight() / HEIGHT;

        // Resize each button accordingly
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < LENGTH; j++) {
                gameButtons[i][j].setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            }
        }

        // Refresh button panel
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    /**
     * Main method to launch the application.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        int lightInterval = -1;
        String warningMessage = "\n";

        if (args.length == 2) {
            try {
                lightInterval = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid val for lightInterval");
            }

            warningMessage = args[1];
        }

        final int finalLightInterval = lightInterval;
        final String finalWarningMessage = warningMessage;

        EventQueue.invokeLater(() -> new TicTacToe(finalLightInterval, finalWarningMessage));
    }
}