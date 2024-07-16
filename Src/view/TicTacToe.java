package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import Serialization.TicTacRecord;
import model.*;

/**
 * Represents a Tic Tac Toe game application.
 *
 * <p>
 * The TicTacToe class provides a graphical user interface for playing
 * the classic game of Tic Tac Toe. It allows players to interact with
 * the game board through buttons and delegates tracking game state, including player
 * turns, wins, and restarts. The application is implemented using Java
 * Swing for the user interface components.
 * </p>
 *
 * <p>
 * The class offers constructors for initializing a new game or resuming
 * a previous one. It handles button clicks, updates game state, and
 * manages UI components such as buttons, labels, and panels. Additionally,
 * it supports customization of game parameters such as light interval
 * and warning messages.
 * </p>
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

    GameButtonBuilder.MyButton winningButton = null;

    /**
     * Constructs a new TicTacToe game.
     * <p>
     * This constructor initializes a new Tic Tac Toe game with default
     * dimensions and creates a new game state logic and score tracker.
     * It sets up the game window with the specified light interval and warning
     * message, and makes the window visible to start the game.
     *
     * @param lightInterval  The interval for displaying warnings or messages.
     * @param warningMessage The message to display as a warning during the game.
     */
    public TicTacToe(int lightInterval, String warningMessage) {
        //Default values of Height and Length
        LENGTH = 3;
        HEIGHT = 3;

        gameState = new GameStateLogic(HEIGHT, LENGTH);
        scoreTracker = new ScoreKeeper();

        universalConstruction(lightInterval, warningMessage);
        setVisible(true);
    }

    /**
     * Constructs a TicTacToe game with prior game state and win record.
     * <p>
     * This constructor initializes a Tic Tac Toe game with the provided
     * prior game state and win record. It allows customization of the
     * game board dimensions and sets up the game window with the specified
     * light interval and warning message. Additionally, it reloads the
     * button painter to reflect the prior game state.
     *
     * @param priorGame The Wrapped elements of the game to be loaded in.
     */
    public TicTacToe(TicTacRecord priorGame) {
        HEIGHT = priorGame.height();
        LENGTH = priorGame.length();

        winningButton = (GameButtonBuilder.MyButton) priorGame.winningButton();
        gameState = priorGame.model();
        scoreTracker = priorGame.scoreRecord();

        universalConstruction(priorGame.lightInterval(), priorGame.warningMessage());
        ButtonPainter.reloadPainter(gameButtons, gameState);

        if (winningButton != null)
            // Repaint buttons based on the end game condition
            ButtonPainter.victoryPainter(gameButtons, winningButton, gameState.getGameStateCode(), HEIGHT, LENGTH);

    }

    /**
     * Performs universal construction tasks for setting up the Tic Tac Toe game window.
     * <p>
     * This method handles the initialization of the game window by setting the title,
     * layout, and adding panels for the game components. It also sets the size and
     * position of the window, handles resizing, and starts a timer for updating game
     * warnings or messages. If the board dimensions are smaller than 1, it throws an
     * IllegalArgumentException.
     *
     * @param interval  The interval for displaying warnings or messages.
     * @param wMessage The message to display as a warning during the game.
     * @throws IllegalArgumentException If the board dimensions are smaller than 1.
     */
    private void universalConstruction(int interval, String wMessage) {
        if (HEIGHT < 1 || LENGTH < 1)
            throw new IllegalArgumentException("Board can not have dimensions smaller than 1");

        //I don't like that this is done here. The rest of the method, (except the timer start which I also do not like), is for setting window elements.
        //I would encapsulate the three lines, but the two first two need to happen at the beginning and the last at the end.
        lightInterval = interval;
        warningMessage = wMessage;

        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(PanelBuilder.arrangeHeadPanel(this), BorderLayout.NORTH);

        buttonPanel = PanelBuilder.arrangeButtonPanel(this, HEIGHT, LENGTH, this::mouseClickHandler);
        add(buttonPanel, BorderLayout.CENTER);

        add(PanelBuilder.arrangeStatusPanel(this, gameState, interval, wMessage, e -> restartGame()), BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setSize(500, 300);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                handleResize();
            }
        });

        myTimer.start();
    }

    /**
     * Handles the mouse click event on buttons.
     * <p>
     * This method is called when a button is clicked in the game window.
     * If the game is not over and the clicked button has not been clicked before, it updates
     * the button text, disables the button, updates the game state label, and checks
     * if the game has ended. If the game has ended by means other than a draw, it
     * repaints the buttons to indicate the winning combination and increments the
     * win record. This method is associated with the mouse click event on game buttons.
     * </p>
     *
     * @param ae The ActionEvent object representing the click event.
     */
    private void mouseClickHandler(ActionEvent ae) {
        GameButtonBuilder.MyButton clickedButton = (GameButtonBuilder.MyButton) ae.getSource();

        //if game is not over and the current button has not been clicked before, restart the timer and update text and label
        //then  check if the game has ended in a way that requires repainting
        if (gameState.getGameStateCode() == 0 && gameState.getValAtPos(clickedButton.getXPos(), clickedButton.getYPos()) == 0) {
            myTimer.restate();

            // Update paint and text on the clicked button and handle game state logic
            clickedButton.setEnabled(false);
            clickedButton.setText(gameState.btnClicked(clickedButton, clickedButton.getXPos(), clickedButton.getYPos()));

            // Update label text to reflect the current game state
            lbl.setText(gameState.lblUpdater());

            //check if game has ended by means other than cats eye
            if (gameState.getGameStateCode() > 0) {
                gameHasBeenWonLogic(clickedButton);
            }
        }
    }

    /**
     * When a game has been won 3 things must be tracked: The winning button, the board repaint, the win record.
     *
     * @param clickedButton the button clicked to end the game.
     */
    private void gameHasBeenWonLogic(GameButtonBuilder.MyButton clickedButton) {
        winningButton = clickedButton;

        // Repaint buttons based on the end game condition
        ButtonPainter.victoryPainter(gameButtons, clickedButton, gameState.getGameStateCode(), HEIGHT, LENGTH);
        //win record only increase if game ended for reason other than cats eye
        scoreTracker.incrementScore(gameState.getXTurn());
    }

    /**
     * Restarts the game by clearing the button panel,
     * initializing new buttons, handling resize, and resetting the game state.
     */
    private void restartGame() {
        //clear button panel
        buttonPanel.removeAll();

        //Reset game state
        gameState = new GameStateLogic(HEIGHT, LENGTH);

        //init new buttons.
        gameButtons = GameButtonBuilder.initGameButtons(buttonPanel, this::mouseClickHandler, HEIGHT, LENGTH);

        handleResize();

        //update game state label
        lbl.setText(gameState.lblUpdater());
        myTimer.restate();
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

    public TicTacRecord provideRecord() {
        return new TicTacRecord(gameState, scoreTracker, HEIGHT, LENGTH, winningButton, lightInterval, warningMessage);
    }

    /**
     * Main method to launch the application.
     *
     * @param args Command-line arguments.
     *             The first argument is expected to be the light interval,
     *             an integer representing the interval for changing the color on the movePressure panel.
     *             The second argument is expected to be the warning message,
     *             a string representing the message to display as a warning during the game.
     */
    public static void main(String[] args) {
        //Default values will be recognized as impossible and the true defaults will be used.
        int lightInterval = -1;
        String warningMessage = "\n";

        //parse command-line args
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