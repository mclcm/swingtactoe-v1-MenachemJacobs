import javax.swing.*;

/**
 * This class manages the game state logic of the Tic Tac Toe game.
 */
public class GameStateLogic {
    //0 is an ongoing game. 2 is a rank win, 3 a file win. 5 is a dexter win, and 7 is sinister.
    // The special diagonals (for game with non-square boards) are 11 for DexterAscendant and 13 for SinisterAscendant.
    // -1 is a cats eye.
    private int gameState = 0;

    //Track which player's turn it currently is
    private Boolean isXTurn = true;

    //Number of turns that have elapsed under the current logical instance
    private int turnCounter = 0;

    //Logical analogue of the GUI board
    private final int[][] logicalBoard;

    //minimum number of turns need to win the game
    private final int minimumNumOfTurns;

    /**
     * Constructs a new game state logic object.
     *
     * @param height The height of the game board.
     * @param length The length of the game board.
     */
    GameStateLogic(int height, int length) {
        if(height < 1 || length < 1) throw new IllegalArgumentException("Board can not have dimensions smaller than 1");

        logicalBoard = new int[height][length];

        //for x to win, he needs to fill either a row or columns worth of spaces. To take that many turns, o has to have gone at least one less than that number of times.
        minimumNumOfTurns = Math.min(logicalBoard.length, logicalBoard[0].length) * 2 - 1;
    }

    /**
     * Handles the logic when a button is clicked. Cannot be called while the game is over.
     * More formally, this method will never be called while isGame over is true.
     *
     * @param clickedButton The button that was clicked.
     * @return The text to be displayed on the clicked button.
     */
    public String btnMouseClicked(JButton clickedButton, int xPos, int yPos) {

        //One would hope that the GUI implementing this class would have run the check on its end, but there is no way to know
        if ((logicalBoard[yPos][xPos] == 0) && gameState == 0) {

            //set the value on the logicalBoard
            logicalBoard[yPos][xPos] = isXTurn ? 1 : -1;

            //check if game is over and update the relevant state if so
            gameOverHandler(xPos, yPos);

            //update turn state
            turnCounter++;
            isXTurn = !isXTurn;

            //update the text in the clicked button.
            return !isXTurn ? "X" : "O";
        }

        //Ideally, clicking a disabled button would do nothing, but the responsibility for checking that is delegated too far up stream
        return clickedButton.getText();
    }

    /**
     * Handles the logic when the game is over.
     */
    public void gameOverHandler(int xPos, int yPos) {
        //check if someone has a win condition. Checks should only begin after the minimum number of turns needed to win the game.
        if (turnCounter >= minimumNumOfTurns - 1)
            gameState = GameOverLogic.isGameOver(logicalBoard, xPos, yPos);

        //if no one won but the board is full call a cats eye
        if (turnCounter >= logicalBoard.length * logicalBoard[0].length - 1 && gameState == 0)
            gameState = -1;
    }

    /**
     * Updates the label text based on the current turn, unless the game has ended
     * in which case the gameOverLegend is returned instead.
     *
     * @return The text to be displayed on the information label.
     */
    public String lblUpdater() {
        if (gameState == -1) return "Game is over, cat's eye";
        else if (gameState != 0) return "Game is over, " + (!isXTurn ? "X" : "O") + " won";

        return "It is player " + (isXTurn ? "X's" : "O's") + " turn";
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean getGameIsOver() {
        return gameState != 0;
    }

    Boolean getXTurn() {
        return isXTurn;
    }

    int getGameState() {
        return gameState;
    }
}