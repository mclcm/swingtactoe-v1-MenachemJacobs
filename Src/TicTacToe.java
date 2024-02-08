import javax.swing.*;

public class TicTacToe {
    BoardBuilder currentGame;

    private Boolean isXTurn = true;
    private Boolean gameIsOver = false;
    private int turnCounter = 0;
    private final int LENGTH = 3;
    private final int HEIGHT = 3;


    public TicTacToe() {
        currentGame = new BoardBuilder(this, HEIGHT, LENGTH);
    }

    void restartGame() {
        currentGame.dispose();
        currentGame = new BoardBuilder(this, HEIGHT, LENGTH);

        //reset state
        isXTurn = true;
        turnCounter = 0;
        gameIsOver = false;
    }

    void btnMouseClicked(java.awt.event.MouseEvent ae) {
        JButton clickedButton = (JButton) ae.getSource();

        //checks both that the particular button has not been pressed before, and that the game is not over
        if (clickedButton.isEnabled() && !gameIsOver) {
            gameIsOver = buttonClickerUpdate(clickedButton, isXTurn, currentGame);

            //not totally comfortable putting the consequences of a game over here
            gameOverUpdate();

            if (!gameIsOver) turnUpdate();
        }
    }

    private static boolean buttonClickerUpdate(JButton clickedButton, Boolean isXTurn, BoardBuilder currentGame) {
        clickedButton.setText(isXTurn ? "X" : "O");
        clickedButton.setEnabled(false);

        //check for game over
        return GameLogic.isGameOver(currentGame);
    }

    private void gameOverUpdate() {
        //not totally comfortable putting the consequences of a game over here
        if (gameIsOver) currentGame.lbl.setText("Game is over, " + (isXTurn ? "X" : "O") + " won");
            //end game if board is full
        else if (turnCounter == HEIGHT * LENGTH - 1) {
            gameIsOver = true;
            currentGame.lbl.setText("Game is over, cat's eye");
        }
    }

    private void turnUpdate() {
        turnCounter++;
        isXTurn = !isXTurn;

        currentGame.lbl.setText("It is player " + (isXTurn ? "X" : "O") + " turn");
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToe();
            }
        });
    }
}
