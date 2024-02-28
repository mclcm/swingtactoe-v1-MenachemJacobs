package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import model.*;
import Serialization.*;

/**
 * This class represents a Tic Tac Toe game application.
 */
public class TicTacToe extends JFrame {
    GameStateLogic gameState;
    ScoreKeeper scoreTracker = new ScoreKeeper();
    JLabel lbl;
    JPanel labelPanel;
    JButton[][] buttons;
    JPanel buttonPanel;
    JButton saveButton;
    JButton loadButton;
    JPanel headerPanel;
    JButton restartButton;

    private final int LENGTH = 3;
    private final int HEIGHT = 3;


    /**
     * Constructs a new TicTacToe game.
     */
    public TicTacToe() {
        if (HEIGHT < 1 || LENGTH < 1)
            throw new IllegalArgumentException("Board can not have dimensions smaller than 1");

        initGUI();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.add(new JLabel("TicTacToe"));
        add(header, BorderLayout.NORTH);

        add(buttonPanel, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);

        setSize(500, 300);
        pack();
        setVisible(true);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                handleResize();
            }
        });

        gameState = new GameStateLogic(HEIGHT, LENGTH);
    }


    /**
     * Custom JButton class with additional properties.
     */
    public static class MyButton extends JButton {

        //xPos of this button. Analogous to this button's Length position in the board
        private final int xPos;
        //xPos of this button. Analogous to this button's Height position in the board
        private final int yPos;

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
     * Initializes the GUI components.
     */
    private void initGUI() {
        setTitle("Tic Tac Toe");

        labelPanel = new JPanel();
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

        lbl = new JLabel("It is player X turn");
        restartButton = new JButton("Start over?");

        // Set the preferred size for the labelPanel and buttonPanel
        labelPanel.setPreferredSize(new Dimension(500, 50));
        buttonPanel.setPreferredSize(new Dimension(500, 200));

        //This line takes the buttonPanel and attaches the buttons to it.
        buttons = ButtonBuilder.initButtons(buttonPanel, this::mouseClickHandler, HEIGHT, LENGTH);

        labelPanel.add(lbl);

        restartButton.addActionListener(e -> restartGame());

        labelPanel.add(restartButton);
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
            clickedButton.setEnabled(false);

            // Update text on the clicked button and handle game state logic
            clickedButton.setText(gameState.btnClicked(clickedButton, clickedButton.getXPos(), clickedButton.getYPos()));

            // Update label text to reflect the current game state
            lbl.setText(gameState.lblUpdater());

            //check if game has ended by means other than cats eye
            if (gameState.getGameState() > 0) {
                // Repaint buttons based on the end game condition
                buttonEndGameRepaint(clickedButton, gameState.getGameState());
                //win record only increase if game ended for reason other than cats eye
                scoreTracker.incrementScore(gameState.getXTurn());
            }
        }
    }

    /**
     * Repaints the buttons involved in the end game based on the given end game condition.
     *
     * @param clickedButton    The button that was clicked to trigger the end game condition.
     * @param endGameCondition The condition indicating the type of win (rank, file, diagonal, and/or the ascendants).
     */
    private void buttonEndGameRepaint(MyButton clickedButton, int endGameCondition) {
        int loopLimit = Math.min(HEIGHT, LENGTH);

        //2 is the code for a rank win.
        if (endGameCondition % gameState.getWinCode("rank") == 0) {
            // Rank win: repaint buttons in the same row
            for (int i = 0; i < LENGTH; i++) {
                dryPaintBtn(buttons[clickedButton.getYPos()][i]);
            }
        }

        //3 a file win.
        if (endGameCondition % gameState.getWinCode("file") == 0) {
            // File win: repaint buttons in the same column
            for (JButton[] button : buttons) {
                dryPaintBtn(button[clickedButton.getXPos()]);
            }
        }

        //5 is the code for a dexter win.
        if (endGameCondition % gameState.getWinCode("dexter") == 0) {
            // Dexter win: repaint buttons in the main diagonal
            for (int i = 0; i < loopLimit; i++) {
                dryPaintBtn(buttons[i][i]);
            }
        }

        //7 is the code for a sinister win.
        if (endGameCondition % gameState.getWinCode("sinister") == 0) {
            // Sinister win: repaint buttons in the secondary diagonal
            for (int i = 0; i < loopLimit; i++) {
                dryPaintBtn(buttons[i][LENGTH - 1 - i]);
            }
        }

        if (HEIGHT != LENGTH) {
            //11 is the code for a dexterAscendant win.
            if (endGameCondition % gameState.getWinCode("aDexter") == 0) {
                // Dexter Ascendant win: repaint buttons in the ascending diagonal from bottom left to top right
                for (int i = 0; i < loopLimit; i++) {
                    dryPaintBtn(buttons[HEIGHT - 1 - i][i]);
                }
            }

            //13 is the code for a sinisterAscendant win.
            if (endGameCondition % gameState.getWinCode("aSinister") == 0) {
                // Sinister Ascendant win: repaint buttons in the ascending diagonal from top left to bottom right
                for (int i = 0; i < loopLimit; i++) {
                    dryPaintBtn(buttons[HEIGHT - 1 - i][LENGTH - 1 - i]);
                }
            }
        }
    }

    /**
     * Repaints the given button to indicate its state during the end game.
     *
     * @param btnToPaint The button to repaint.
     */
    private void dryPaintBtn(JButton btnToPaint) {
        btnToPaint.setEnabled(true);
        btnToPaint.setBackground(Color.orange);
    }

    /**
     * Restarts the game by clearing the button panel,
     * initializing new buttons, handling resize,
     * and resetting the game state.
     */
    private void restartGame() {
        buttonPanel.removeAll();

        buttons = ButtonBuilder.initButtons(buttonPanel, this::mouseClickHandler, HEIGHT, LENGTH);
        handleResize();

        //Reset game state
        gameState = new GameStateLogic(HEIGHT, LENGTH);
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
                buttons[i][j].setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            }
        }

        // Refresh button panel
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    /**
     * Serializes the current game state and view.
     */
    public void serializeGame() {
        SerializeGame.serialize(this, gameState, scoreTracker);
    }

    /**
     * restore the deserialized the elements of the current game.
     */
    public void deSerializeGame(GameStateLogic gameState, ScoreKeeper scoreTracker) {
        this.scoreTracker = scoreTracker;
        this.gameState = gameState;
    }

    /**
     * Main method to launch the application.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(TicTacToe::new);
    }
}