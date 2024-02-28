import model.GameOverLogic;

import javax.swing.*;
import java.io.Serializable;

/**
 * This class manages the game state logic of the Tic Tac Toe game.
 */
public class GameStateLogic implements Serializable {
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
        if (height < 1 || length < 1)
            throw new IllegalArgumentException("Board can not have dimensions smaller than 1");

        logicalBoard = new int[height][length];

        //for x to win, he needs to fill either a row or columns worth of spaces. To take that many turns, o has to have gone at least one less than that number of times.
        minimumNumOfTurns = Math.min(logicalBoard.length, logicalBoard[0].length) * 2 - 1;
    }

    /**
     * Handles the logic when a button is clicked.
     *
     * @param clickedButton The button that was clicked.
     * @param xPos          The x-coordinate of the clicked button on the game board.
     * @param yPos          The y-coordinate of the clicked button on the game board.
     * @return The text to be displayed on the clicked button after handling the click event.
     */
    public String btnClicked(JButton clickedButton, int xPos, int yPos) {

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
     * Handles the logic when the game is over. Checks for win conditions or a tie
     * based on the current game state and updates the {@code gameState} accordingly.
     *
     * @param xPos The x-coordinate of the last played move.
     * @param yPos The y-coordinate of the last played move.
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
     * Updates the label text based on the current turn or game state.
     * If the game is over, returns the appropriate game over message.
     *
     * @return The text to be displayed on the information label.
     */
    public String lblUpdater() {
        if (gameState == -1) return "Game is over, cat's eye";
        else if (gameState != 0) return "Game is over, " + (!isXTurn ? "X" : "O") + " won";

        return "It is player " + (isXTurn ? "X's" : "O's") + " turn";
    }

    /**
     * Retrieves the value at the specified position on the logical game board.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return The value at the specified position on the game board.
     */
    public int getValAtPos(int x, int y) {
        return logicalBoard[y][x];
    }

    /**
     * Checks if the game is over.
     *
     * @return {@code true} if the game is over, {@code false} otherwise.
     */
    public boolean getGameIsOver() {
        return gameState != 0;
    }

    /**
     * Retrieves the current turn indicator, indicating whether it's X's turn.
     *
     * @return {@code true} if it's currently X's turn, {@code false} otherwise.
     */
    Boolean getXTurn() {
        return isXTurn;
    }

    /**
     * Retrieves the current state of the game.
     *
     * @return An integer representing the current state of the game:
     * - 0 for an ongoing game,
     * - 2 for a row win, 3 for a column win,
     * - 5 for a diagonal win (Dexter), 7 for a diagonal win (Sinister),
     * - 11 for a diagonal win (DexterAscendant), 13 for a diagonal win (SinisterAscendant),
     * - (-1) for a tie game (cat's eye).
     */
    int getGameState() {
        return gameState;
    }
}