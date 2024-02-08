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

    //This can only be called while the game is not yet over
    public String btnMouseClicked(TicTacToe.MyButton clickedButton) {

        if (clickedButton.isEnabled()) {
            clickedButton.setEnabled(false);

            logicalBoard[clickedButton.getXPos()][clickedButton.getYPos()] = isXTurn ? 1 : -1;

            gameIsOver = GameOverLogic.isGameOver(logicalBoard, clickedButton.getXPos(), clickedButton.getYPos());
            if (gameIsOver) gameOverLegend = "Game is over, " + (isXTurn ? "X" : "O") + " won";
            else if(turnCounter >= 9) {
                gameOverLegend = "Game is over, cat's eye";
                gameIsOver = true;
            }

            turnCounter++;
            isXTurn = !isXTurn;

            return !isXTurn ? "X" : "O";
        }

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

    //not totally comfortable putting the consequences of a game over here
//            if (gameIsOver)
//            lbl.setText("Game is over, " + (isXTurn ? "X" : "O") + " won");
//            else if (turnCounter == HEIGHT * LENGTH - 1 && !gameIsOver) {
//        gameIsOver = true;
//        lbl.setText("Game is over, cat's eye");
//    } else {
//
//
//        lbl.setText("It is player " + (isXTurn ? "X" : "O") + " turn");
//    }
}
