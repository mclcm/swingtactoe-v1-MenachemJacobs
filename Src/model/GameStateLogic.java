package model;

import javax.swing.*;
import java.io.Serializable;

/**
 * Manages the game state logic of the Tic Tac Toe game.
 * This class handles player turns, button clicks, and determines the game's outcome.
 */
public class GameStateLogic implements Serializable {
    //Game state uses the values maintained in the StaticState vars class to represent the state of the game vis a vis ongoing or kind of end.
    private int gameState;

    //Track which player's turn it currently is
    private Boolean isXTurn = true;

    //Number of turns that have elapsed under the current logical instance
    private int turnCounter = 0;

    //Logical analogue of the GUI board
    final int[][] LOGICAL_BOARD;

    //minimum number of turns need to win the game
    private final int MIN_NUM_TURNS;

    // Constant representing X's value on the game board
    static final int xVal = 1;
    // Constant representing O's value on the game board
    static final int oVal = -1;

    /**
     * Constructs a new game state logic object with the specified board dimensions.
     *
     * @param height The height of the game board.
     * @param length The length of the game board.
     * @throws IllegalArgumentException if the board dimensions are smaller than 1.
     */
    public GameStateLogic(int height, int length) {
        if (height < 1 || length < 1)
            throw new IllegalArgumentException("Board can not have dimensions smaller than 1");

        LOGICAL_BOARD = new int[height][length];
        gameState = StaticStateVar.ONGOING;

        //For x to win, he needs to fill either a row or columns worth of spaces.
        //To take that many turns, o has to have gone at least one less than that number of times.
        MIN_NUM_TURNS = Math.min(LOGICAL_BOARD.length, LOGICAL_BOARD[0].length) * 2 - 1;
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
        if ((LOGICAL_BOARD[yPos][xPos] == 0) && gameState == StaticStateVar.ONGOING) {

            //set the value on the logicalBoard
            LOGICAL_BOARD[yPos][xPos] = isXTurn ? xVal : oVal;

            //check if game is over and update the relevant state if so
            gameOverHandler(xPos, yPos);

            //update turn state
            turnCounter++;
            isXTurn = !isXTurn;

            //update the text in the clicked button.
            return buttonTextSetter(!isXTurn ? xVal : oVal);
        }

        //Ideally, clicking a disabled button would do nothing, but the responsibility for checking that is delegated too far up stream
        return clickedButton.getText();
    }

    /**
     * Sets the text for the button based on the current player's turn.
     *
     * @param cellValue The value representing the player's move (1 for X, -1 for O).
     * @return The text to be displayed on the button.
     */
    public String buttonTextSetter(int cellValue) {
        return cellValue == 1 ? "X" : "O";
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
        if (turnCounter >= MIN_NUM_TURNS - 1)
            gameState = GameOverLogic.isGameOver(LOGICAL_BOARD, xPos, yPos);

        //if no one won but the board is full call a cats eye
        if (turnCounter >= LOGICAL_BOARD.length * LOGICAL_BOARD[0].length - 1 && gameState == 0)
            gameState = StaticStateVar.CATS_EYE;
    }

    /**
     * Updates the label text based on the current turn or game state.
     *
     * @return The text to be displayed on the information label.
     */
    public String lblUpdater() {
        if (gameState == StaticStateVar.CATS_EYE) return "Game is over, cat's eye";
        else if (gameState != StaticStateVar.ONGOING) return "Game is over, " + (!isXTurn ? "X" : "O") + " won";

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
        return LOGICAL_BOARD[y][x];
    }

    /**
     * Retrieves the current turn indicator, indicating whether it's X's turn.
     *
     * @return {@code true} if it's currently X's turn, {@code false} otherwise.
     */
    public Boolean getXTurn() {
        return isXTurn;
    }

    /**
     * Retrieves the current state of the game.
     *
     * @return The current state of the game.
     */
    public int getGameStateCode() {
        return gameState;
    }

    /**
     * Retrieves the value at the specified position on the logical game board.
     *
     * @param xPos The x-coordinate of the position.
     * @param yPos The y-coordinate of the position.
     * @return The value at the specified position on the game board.
     */
    public int getCellVal(int xPos, int yPos) {
        return LOGICAL_BOARD[yPos][xPos];
    }
}