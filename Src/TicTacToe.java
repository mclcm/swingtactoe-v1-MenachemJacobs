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

        buttonPanel = new JPanel(new GridLayout(3, 3)); // split the panel in 1 rows and 2 cols)
        buttonPanel.setPreferredSize(new Dimension(0, 200));
        initButtons();

        lbl = new JLabel("It is player X turn");
        labelPanel = new JPanel();
        labelPanel.add(lbl);
    }

    private void initButtons() {
        buttons = new JButton[3][3];

        // Initialize buttons and add ActionListener
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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

        if(clickedButton.isEnabled() && !gameIsOver) {
            clickedButton.setText(isXTurn ? "X" : "O");
            clickedButton.setEnabled(false);

            isGameOver(clickedButton);

            //not totally comfortable putting the consequences of a game over here
            if(gameIsOver)
                lbl.setText("Game is over, " + (isXTurn ? "X" : "O") + " won");
            else{
                isXTurn = !isXTurn;

                lbl.setText("It is player " + (isXTurn ? "X" : "O") + " turn");
            }
        }
    }

    private void isGameOver(JButton clickedButton){
        rankCheck();
        if(gameIsOver)
            System.out.println("game is over");

        if(!gameIsOver){
            //fileCheck();
        }
    }

    private void rankCheck(){
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

                previousText = button.getText();
            }

            if(gameIsOver)
                break;
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
