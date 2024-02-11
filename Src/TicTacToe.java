import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This class represents a Tic Tac Toe game application.
 */
public class TicTacToe extends JFrame {
    GameStateLogic gameState;
    JLabel lbl;
    JPanel labelPanel;
    JPanel buttonPanel;
    JButton[][] buttons;
    JButton restartButton;

    private final int LENGTH = 3;
    private final int HEIGHT = 3;


    /**
     * Constructs a new TicTacToe game.
     */
    public TicTacToe() {
        initGUI();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout to BorderLayout
        setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.add(new JLabel("TicTacToe"));
        add(header, BorderLayout.NORTH);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(buttonPanel, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);

        setSize(500, 300);
        //pack();
        setVisible(true);

        gameState = new GameStateLogic(HEIGHT,LENGTH);
    }

    /**
     * Custom JButton class with additional properties.
     */
    public static class MyButton extends JButton {

        private final int xPos;
        private final int yPos;

        /**
         * Constructs a new MyButton.
         * @param xPos The x position of the button.
         * @param yPos The y position of the button.
         */
        public MyButton(int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
        }

        /**
         * Get the x position of the button.
         * @return The x position.
         */
        public int getXPos() {
            return xPos;
        }

        /**
         * Get the y position of the button.
         * @return The y position.
         */
        public int getYPos(){
            return yPos;
        }
    }

    /**
     * Initializes the GUI components.
     */
    private void initGUI() {
        //TODO: Illegal GUI on one front
        setTitle("Tic Tac Toe");

        labelPanel = new JPanel();
        buttonPanel = new JPanel(new GridLayout(HEIGHT, LENGTH)); // split the panel in 1 rows and 2 cols)

        lbl = new JLabel("It is player X turn");
        restartButton = new JButton("Start over?");

        buttonPanel.setPreferredSize(new Dimension(0, 200));
        initButtons();

        labelPanel.add(lbl);

        restartButton.addActionListener(e -> restartGame());

        labelPanel.add(restartButton);
    }

    /**
     * Initializes the buttons grid.
     */
    private void initButtons() {
        buttons = new JButton[HEIGHT][LENGTH];

        // Initialize buttons and add ActionListener
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < LENGTH; j++) {
                buttons[i][j] = new MyButton(i, j);
                buttons[i][j].addActionListener(this::mouseClickHandler);
                buttonPanel.add(buttons[i][j]);
            }
        }
    }

    /**
     * Handles the mouse click event on buttons.
     * @param ae The MouseEvent object representing the click event.
     */
    private void mouseClickHandler(ActionEvent ae){
        MyButton clickedButton = (MyButton) ae.getSource();

        //if game is not over update text and label
        if(!gameState.gameIsOverGetter()) {
            clickedButton.setText(gameState.btnMouseClicked(clickedButton));

            //update lbl, game may be over and need to reflect that
            lbl.setText(gameState.lblUpdater());
        }

    }

    /**
     * Restarts the game by...
     */
    private void restartGame(){
        //TODO: Have reset redo the buttons and clear the state
        TicTacToe newGame = new TicTacToe();
        newGame.setVisible(true);
        dispose();
    }

    /**
     * Main method to launch the application.
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToe();
            }
        });
    }
}