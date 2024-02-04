import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class TicTacToe extends JFrame {
    JLabel lbl;
    JPanel labelPanel;
    JPanel buttonPanel;
    JButton[][] buttons;
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

        buttonPanel = new JPanel(new GridLayout(HEIGHT, LENGTH)); // split the panel in 1 rows and 2 cols)
        lbl = new JLabel("It is player X turn");

        buttonPanel.setPreferredSize(new Dimension(0, 200));
        initButtons();

        labelPanel = new JPanel();
        labelPanel.add(lbl);
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

    private void btnMouseClicked(java.awt.event.MouseEvent ae) {
        JButton clickedButton = (JButton) ae.getSource();

        if (clickedButton.isEnabled() && !gameIsOver) {
            clickedButton.setText(isXTurn ? "X" : "O");
            clickedButton.setEnabled(false);

            isGameOver(clickedButton);

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

    private void isGameOver(JButton clickedButton) {
        rankCheck();

        if (!gameIsOver) {
            fileCheck();
        }

        if (!gameIsOver) {
            diagonalCheck();
        }
    }

    private void rankCheck() {
        gameIsOver = true;

        //check all rows, so long as a solid one isn't found
        for (JButton[] buttonRow : buttons) {
            gameIsOver = true;
            String previousText = buttonRow[0].getText();

            //check a single row for either an empty spot or a dissimilarity. set gameIsOver to false and break if found.
            for (JButton button : buttonRow) {

                if (!Objects.equals(button.getText(), previousText) || Objects.equals(button.getText(), "")) {
                    gameIsOver = false;
                    break;
                }
            }

            //if no dissimilarity was found to reset the gameIsOver state
            if (gameIsOver)
                break;
        }
    }

    private void fileCheck() {
        gameIsOver = true;

        //check all columns, so long as a solid one isn't found
        for (int i = 0; i < LENGTH; i++) {
            gameIsOver = true;
            String previousText = buttons[0][i].getText();

            //check a single column (i) for either an empty spot or a dissimilarity. set gameIsOver to false and break if found.
            for (int j = 0; j < HEIGHT; j++) {

                if (!Objects.equals(buttons[j][i].getText(), previousText) || Objects.equals(buttons[j][i].getText(), "")) {
                    gameIsOver = false;
                    break;
                }
            }

            //if no dissimilarity was found to reset the gameIsOver state
            if (gameIsOver)
                break;
        }
    }

    private void diagonalCheck() {
        dexterCheck();
//        if(HEIGHT != LENGTH)
//            dexterAscendantCheck();
        sinisterCheck();
//        if(HEIGHT != LENGTH)
//            sinisterAscendantCheck();
    }

    private void dexterCheck() {
        gameIsOver = true;
        String previousText = buttons[0][0].getText();

        for (int i = 0; i < HEIGHT; i++) {
            if (!Objects.equals(buttons[i][i].getText(), previousText) || Objects.equals(buttons[i][i].getText(), "")) {
                gameIsOver = false;
                break;
            }
        }
    }

    private void sinisterCheck() {
        System.out.println("Sinister Check Running");
        gameIsOver = true;
        String previousText = buttons[0][LENGTH - 1].getText();

        for (int i = 0; i < LENGTH; i++) {
            if (!Objects.equals(buttons[LENGTH - i - 1][i].getText(), previousText) || Objects.equals(buttons[LENGTH - i - 1][i].getText(), "")) {
                gameIsOver = false;
                break;
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
