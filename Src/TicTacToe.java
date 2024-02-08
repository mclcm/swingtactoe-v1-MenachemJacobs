import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {
    GameStateLogic gameState;
    JLabel lbl;
    JPanel labelPanel;
    JPanel buttonPanel;
    JButton[][] buttons;
    JButton restartButton;

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

        gameState = new GameStateLogic(HEIGHT,LENGTH);
    }

    public class MyButton extends JButton {

        private final int xPos;
        private final int yPos;

        // Constructor
        public MyButton(int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
        }

        public int getXPos() {
            return xPos;
        }

        public int getYPos(){
            return yPos;
        }
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
                //this shouldn't be necessary
                //gameState.resetState();
                restartGame();
            }
        });

        labelPanel.add(restartButton);
    }

    private void initButtons() {
        buttons = new JButton[HEIGHT][LENGTH];

        // Initialize buttons and add ActionListener
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < LENGTH; j++) {
                buttons[i][j] = new MyButton(i, j);
                buttons[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent ae) {
                        mouseClickHandler(ae);
                    }
                });
                buttonPanel.add(buttons[i][j]);
            }
        }
    }

    private void mouseClickHandler(java.awt.event.MouseEvent ae){
        MyButton clickedButton = (MyButton) ae.getSource();

        //if game is not over update text and label
        if(!gameState.gameIsOverGetter()) {
            clickedButton.setText(gameState.btnMouseClicked(clickedButton));

            //update lbl, game may be over and need to reflect that
            lbl.setText(gameState.lblUpdater());
        }

    }

    private void restartGame(){
        TicTacToe newGame = new TicTacToe();
        newGame.setVisible(true);
        dispose();
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