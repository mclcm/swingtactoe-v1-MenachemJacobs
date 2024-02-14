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
     * Initializes the GUI components.
     */
    private void initGUI() {
        setTitle("Tic Tac Toe");

        labelPanel = new JPanel();
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));

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
                buttons[i][j] = new GameStateLogic.MyButton(j, i);
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
        GameStateLogic.MyButton clickedButton = (GameStateLogic.MyButton) ae.getSource();

        //if game is not over and the current button has not been clicked before, update text and label
        if(!gameState.getGameIsOver() && !clickedButton.isPressed) {
            clickedButton.setEnabled(false);
            clickedButton.setText(gameState.btnMouseClicked(clickedButton));

            //update lbl, game may be over and need to reflect that
            lbl.setText(gameState.lblUpdater());
        }

    }

    /**
     * Restarts the game by...
     */
    private void restartGame(){
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
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(TicTacToe::new);
    }
}