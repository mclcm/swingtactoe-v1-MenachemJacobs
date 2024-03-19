package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import Serialization.TicTacWrapper;
import model.*;

/**
 * <p>
 * This class represents a Tic Tac Toe game application.
 * </p>
 *
 * <p>
 * The TicTacToe class provides a graphical user interface for playing
 * the classic game of Tic Tac Toe. It allows players to interact with
 * the game board through buttons and delegates tracking game state, including player
 * turns, wins, and restarts. The application is implemented using Java
 * Swing for the user interface components.
 * </p>
 *
 * <p> The class offers constructors for initializing a new game or resuming
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

    MyButton winningButton = null;

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
    public TicTacToe(TicTacWrapper priorGame) {
        HEIGHT = priorGame.height();
        LENGTH = priorGame.length();

        this.winningButton = priorGame.winningButton();

        gameState = priorGame.model();
        scoreTracker = priorGame.currentScore();

        universalConstruction(priorGame.lightInterval(), priorGame.warningMessage());
        ButtonPainter.reloadPainter(gameButtons, gameState);

        if (winningButton != null) {
            // Repaint buttons based on the end game condition
            ButtonPainter.victoryPainter(gameButtons, winningButton, gameState.getGameState(), HEIGHT, LENGTH);
            //win record only increase if game ended for reason other than cats eye
            scoreTracker.incrementScore(gameState.getXTurn());
        }
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
     * @param lightInterval  The interval for displaying warnings or messages.
     * @param warningMessage The message to display as a warning during the game.
     * @throws IllegalArgumentException If the board dimensions are smaller than 1.
     */
    private void universalConstruction(int lightInterval, String warningMessage) {
        if (HEIGHT < 1 || LENGTH < 1)
            throw new IllegalArgumentException("Board can not have dimensions smaller than 1");

        //I don't like that this is done here.
        // The rest of the method, except the timer start which I also do not like, is for setting window elements.
        //I would encapsulate the three lines, but the two first two need to happen at the beginning and the last at the end.
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
     * Arranges the header panel containing save and load buttons, and the game title.
     * <p>
     * This method creates and configures a JPanel to hold the header components.
     * It adds save and load buttons for game state management, and a title label displaying the game title.
     *
     * @return The JPanel containing the header components.
     */
    private JPanel arrangeHeadPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());

        JPanel headerButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton saveButton = GameButtonBuilder.buildSaveButton(this);
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
     * <p>
     * This method configures a JPanel to hold the game buttons for the game window.
     * It sets up the layout and dimensions of the panel and initializes the game buttons using the GameButtonBuilder class.
     * The game buttons are attached to the panel, and their click event handler is set to the mouseClickHandler method
     * of the TicTacToe class.
     *
     * @return The JPanel containing the game buttons.
     */
    private JPanel arrangeButtonPanel() {
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setPreferredSize(new Dimension(500, 200));

        //This line takes the buttonPanel and attaches the game buttons to it.
        gameButtons = GameButtonBuilder.initGameButtons(buttonPanel, this::mouseClickHandler, HEIGHT, LENGTH);

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
     * <p>
     * This method is called when a button is clicked in the game window.
     * If the game is not over and the clicked button has not been clicked before, it updates
     * the button text, disables the button, updates the game state label, and checks
     * if the game has ended. If the game has ended by means other than a draw, it
     * repaints the buttons to indicate the winning combination and increments the
     * win record. This method is associated with the mouse click event on game buttons.
     *
     * @param ae The ActionEvent object representing the click event.
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
                winningButton = clickedButton;

                // Repaint buttons based on the end game condition
                ButtonPainter.victoryPainter(gameButtons, clickedButton, gameState.getGameState(), HEIGHT, LENGTH);
                //win record only increase if game ended for reason other than cats eye
                scoreTracker.incrementScore(gameState.getXTurn());
            }
        }
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

    public TicTacWrapper provideWrapper(){
        return new TicTacWrapper(gameState, scoreTracker, HEIGHT, LENGTH, winningButton, lightInterval, warningMessage);
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