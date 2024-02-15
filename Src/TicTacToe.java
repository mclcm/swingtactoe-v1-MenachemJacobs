import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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

    static final int RANK_WIN = 2;
    static final int FILE_WIN = 3;
    static final int DEXTER_WIN = 5;
    static final int SINISTER_WIN = 7;
    static final int DEXTER_ASCENDANT_WIN = 11;
    static final int SINISTER_ASCENDANT__WIN = 13;


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
        //TODO: put back in GUI

        //xPos of this button. Analogous to this button's Length position in the board
        private final int xPos;
        //xPos of this button. Analogous to this button's Height position in the board
        private final int yPos;
        //Bool value tracking if this button has been pressed already
        public boolean isPressed;

        /**
         * Constructs a new MyButton.
         *
         * @param xPos The x position of the button.
         * @param yPos The y position of the button.
         */
        public MyButton(int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;

            isPressed = false;
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
                buttons[i][j] = new MyButton(j, i);
                buttons[i][j].addActionListener(this::mouseClickHandler);
                buttonPanel.add(buttons[i][j]);
            }
        }
    }

    /**
     * Handles the mouse click event on buttons.
     *
     * @param ae The MouseEvent object representing the click event.
     */
    private void mouseClickHandler(ActionEvent ae) {
        MyButton clickedButton = (MyButton) ae.getSource();

        //if game is not over and the current button has not been clicked before, update text and label
        if (gameState.getGameState() == 0 && !clickedButton.isPressed) {
            clickedButton.setEnabled(false);
            clickedButton.setText(gameState.btnMouseClicked(clickedButton));

            //update lbl, game may be over and need to reflect that
            lbl.setText(gameState.lblUpdater());

            //check if game has ended by means other than cats eye
            if (gameState.getGameState() > 0) {
                buttonEndGameRepaint(clickedButton, gameState.getGameState());
            }
        }
    }

    private void buttonEndGameRepaint(MyButton clickedButton, int endGameCondition) {
        int loopLimit = Math.min(HEIGHT, LENGTH);

        //2 is a rank win.
        if (endGameCondition % RANK_WIN == 0) {
            for (int i = 0; i < LENGTH; i++) {
                dryPaintBtn(buttons[clickedButton.getYPos()][i]);
            }
        }

        //3 a file win.
        if (endGameCondition % FILE_WIN == 0) {
            for (JButton[] button : buttons) {
                dryPaintBtn(button[clickedButton.getXPos()]);
            }
        }

        //5 is a dexter win.
        if (endGameCondition % DEXTER_WIN == 0) {
            for (int i = 0; i < loopLimit; i++) {
                dryPaintBtn(buttons[i][i]);
            }
        }

        //7 is sinister.
        if (endGameCondition % SINISTER_WIN == 0) {
            for (int i = 0; i < loopLimit; i++) {
                dryPaintBtn(buttons[i][LENGTH - 1 - i]);
            }
        }

        if(HEIGHT != LENGTH){
            //11 is dexterAscendant.
            if (endGameCondition % DEXTER_ASCENDANT_WIN == 0) {
                for (int i = 0; i < loopLimit; i++) {
                    dryPaintBtn(buttons[HEIGHT - 1 - i][i]);
                }
            }

            //13 is sinisterAscendant.
            if (endGameCondition % SINISTER_ASCENDANT__WIN == 0) {
                for (int i = 0; i < loopLimit; i++) {
                    dryPaintBtn(buttons[HEIGHT - 1 - i][LENGTH - 1 - i]);
                }
            }
        }
    }

    private void dryPaintBtn(JButton btnToPaint){
        btnToPaint.setEnabled(true);
        btnToPaint.setBackground(Color.orange);
    }

    /**
     * Restarts the game by...
     */
    private void restartGame() {
        buttonPanel.removeAll();

        initButtons();
        handleResize();

        buttonPanel.revalidate();
        buttonPanel.repaint();

        gameState = new GameStateLogic(HEIGHT, LENGTH);
    }

    /**
     * Handles resizing of the components.
     */
    private void handleResize() {
        int buttonWidth = buttonPanel.getWidth() / LENGTH;
        int buttonHeight = buttonPanel.getHeight() / HEIGHT;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < LENGTH; j++) {
                buttons[i][j].setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            }
        }

        buttonPanel.revalidate();
        buttonPanel.repaint();
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