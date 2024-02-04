import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class TicTacToe extends JFrame {
    JLabel lbl;
    JPanel labelPanel;
    JPanel buttonPanel;
    JButton[][] buttons;
    JButton restartButton;

    private Boolean isXTurn = true;
    private Boolean gameIsOver = false;
    private int turnCounter = 0;
    private final int LENGTH = 3;
    private final int HEIGHT = 3;


    public TicTacToe() {
        initGUI();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(buttonPanel);
        add(labelPanel);
        setLocationRelativeTo(null);

        setSize(500, 300);
        //pack();
        setVisible(true);
    }

    private void initGUI() {
        setTitle("Tic Tac Toe");

        labelPanel = new JPanel();
        buttonPanel = new JPanel(new GridLayout(HEIGHT, LENGTH)); // split the panel in 1 rows and 2 cols)

        lbl = new JLabel("It is player X turn");
        restartButton = new JButton("Start over?");

        buttonPanel.setPreferredSize(new Dimension(0, 200));
        initButtons();

        labelPanel.add(lbl);

        restartButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent ae) {
                restartGame();
            }
        });

        labelPanel.add(restartButton);
    }

    private void initButtons() {
        buttons = new JButton[3][3];

        // Initialize buttons and add ActionListener
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < LENGTH; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent ae) {
                        btnMouseClicked(ae);
                    }
                });
                buttonPanel.add(buttons[i][j]);
            }
        }
    }

    private void restartGame(){
        TicTacToe newGame = new TicTacToe();
        newGame.setVisible(true);
        dispose();
    }

    private void btnMouseClicked(java.awt.event.MouseEvent ae) {
        JButton clickedButton = (JButton) ae.getSource();

        if (clickedButton.isEnabled() && !gameIsOver) {
            clickedButton.setText(isXTurn ? "X" : "O");
            clickedButton.setEnabled(false);

            gameIsOver = GameLogic.isGameOver(buttons, isXTurn, HEIGHT, LENGTH);

            //not totally comfortable putting the consequences of a game over here
            if (gameIsOver)
                lbl.setText("Game is over, " + (isXTurn ? "X" : "O") + " won");
            else if (turnCounter == HEIGHT * LENGTH - 1 && !gameIsOver) {
                gameIsOver = true;
                lbl.setText("Game is over, cat's eye");
            } else {
                turnCounter++;
                isXTurn = !isXTurn;

                lbl.setText("It is player " + (isXTurn ? "X" : "O") + " turn");
            }
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToe();
            }
        });
        //Model
        //View
        //Controller
    }
}
