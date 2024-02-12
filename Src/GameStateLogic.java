import javax.swing.*;

public class GameStateLogic {
    public String gameOverLegend = "Something has gone horribly wrong if this is displayed";
    private Boolean isXTurn = true;
    private Boolean gameIsOver = false;
    private int turnCounter = 0;
    public int[][] logicalBoard;

    GameStateLogic(int height, int length) {
        logicalBoard = new int[height][length];

        //default the logical board to 0
        for (int[] row : logicalBoard) {
            for (int cell : row) {
                cell = 0;
            }
        }
    }

    //This method can only be called while the game is not yet over
    public String btnMouseClicked(TicTacToe.MyButton clickedButton) {

        if (clickedButton.isEnabled()) {
            clickedButton.setEnabled(false);

            //set the value on the logicalBoard
            logicalBoard[clickedButton.getXPos()][clickedButton.getYPos()] = isXTurn ? 1 : -1;

            //check if someone has a win condition
            gameIsOver = GameOverLogic.isGameOver(logicalBoard, clickedButton.getXPos(), clickedButton.getYPos());

            //if someone has a win condition set the win tag.
            if (gameIsOver) gameOverLegend = "Game is over, " + (isXTurn ? "X" : "O") + " won";
                //check if this turn fills the board, and call a cat's eye if it is.
            else if (turnCounter >= logicalBoard.length * logicalBoard[0].length - 1) {
                gameOverLegend = "Game is over, cat's eye";
                gameIsOver = true;
            }

            //update state
            turnCounter++;
            isXTurn = !isXTurn;

            //update the text in the button
            return !isXTurn ? "X" : "O";
        }

        //ideally, clicking the button would do nothing, but the responsibility for checking that is delegated too far down stream
        return clickedButton.getText();
    }

    public String lblUpdater() {
        if (gameIsOver) {
            return gameOverLegend;
        }
        return "It is player " + (isXTurn ? "X" : "O") + " turn";
    }

    public boolean gameIsOverGetter() {
        return gameIsOver;
    }
}