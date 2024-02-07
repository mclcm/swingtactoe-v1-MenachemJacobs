import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class TicTacToe extends JFrame {
    BoardBuilder currentGame;

    private Boolean isXTurn = true;
    private Boolean gameIsOver = false;
    private int turnCounter = 0;
    private final int LENGTH = 3;
    private final int HEIGHT = 3;


    public TicTacToe() {
        currentGame = new BoardBuilder(this, HEIGHT, LENGTH);
    }

    void restartGame(){
        currentGame.dispose();
        currentGame = new BoardBuilder(this, HEIGHT, LENGTH);
        isXTurn = true;
        turnCounter = 0;
    }

    void btnMouseClicked(java.awt.event.MouseEvent ae) {
        JButton clickedButton = (JButton) ae.getSource();

        if (clickedButton.isEnabled() && !gameIsOver) {
            clickedButton.setText(isXTurn ? "X" : "O");
            clickedButton.setEnabled(false);

            gameIsOver = GameLogic.isGameOver(currentGame);

            //not totally comfortable putting the consequences of a game over here
            if (gameIsOver)
                currentGame.lbl.setText("Game is over, " + (isXTurn ? "X" : "O") + " won");
            //game is over logic
            else if (turnCounter == HEIGHT * LENGTH - 1) {
                gameIsOver = true;
                currentGame.lbl.setText("Game is over, cat's eye");
            } else {
                turnCounter++;
                isXTurn = !isXTurn;

                currentGame.lbl.setText("It is player " + (isXTurn ? "X" : "O") + " turn");
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
