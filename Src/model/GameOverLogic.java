package model;

/**
 * The model.GameOverLogic class provides static methods to determine if the game is over
 * based on the current state of the Tic Tac Toe board.
 */
public class GameOverLogic {

    /**
     * Determines if the game is over based on the current state of the board.
     * Each win condition has a distinctive prime return value, which is multiplied into the global return.
     * This ensures that each possible return state of the class is unique.
     *
     * @param logicalBoard The logical representation of the Tic Tac Toe board.
     * @param xPos         The x position of the last move.
     * @param yPos         The y position of the last move.
     * @return An integer representing the game state:
     * - 0 if the game is not ended.
     * - 2 for a rank win.
     * - 3 for a file win.
     * - 5 for a main diagonal win.
     * - 7 for a secondary diagonal win.
     * - 11 for a diagonal win ascending from bottom left to top right.
     * - 13 for a diagonal win ascending from top left to bottom right.
     * or some product there of if more than one condition is met
     * @throws IllegalArgumentException If the clicked value remains 0, indicating it has not been set properly.
     */
    public static int isGameOver(int[][] logicalBoard, int xPos, int yPos) {
        int returnVal = 1;

        int searchValue = logicalBoard[yPos][xPos];
        if (searchValue == 0)
            throw new IllegalArgumentException("If the clicked value remains 0, it has not been set properly");

        returnVal *= rankCheck(logicalBoard, yPos, searchValue);

        returnVal *= fileCheck(logicalBoard, xPos, searchValue);

        returnVal *= diagonalsChecker(logicalBoard, xPos, yPos, searchValue);

        //The code on the outside interprets 0 as being the 'no win' value, will the code on the inside uses 1, meaning that the values need to be exchanged before heading out
        return returnVal == 1 ? 0 : returnVal;
    }

    /**
     * Checks all four possible diagonals for a win condition.
     *
     * @param logicalBoard The logical representation of the Tic Tac Toe board.
     * @param xPos         The x position of the last move.
     * @param yPos         The y position of the last move.
     * @param searchValue  The value to search for (either 1 for X or -1 for O).
     * @return An integer representing the game state:
     * - 1 if no win condition is found.
     * - 5 for a main diagonal win.
     * - 7 for a secondary diagonal win.
     * - 11 for a diagonal win ascending from bottom left to top right.
     * - 13 for a diagonal win ascending from top left to bottom right.
     * or some product there of if more than one condition is met
     */
    private static int diagonalsChecker(int[][] logicalBoard, int xPos, int yPos, int searchValue) {
        int returnVal = 1;

        //diagonal check loops should only check until the smallest of Height or Length.
        int loopLimit = Math.min(logicalBoard.length, logicalBoard[0].length);

        if (xPos == yPos) returnVal *= dexterCheck(logicalBoard, searchValue, loopLimit);

        if (xPos + yPos == logicalBoard[0].length - 1) returnVal *= sinisterCheck(logicalBoard, searchValue, loopLimit);

        //if the board is not square, the more obscure diagonals must also be checked for a win
        if (logicalBoard.length != logicalBoard[0].length)
            returnVal *= obscureDiagonalsChecker(logicalBoard, xPos, yPos, searchValue, loopLimit);

        return returnVal;
    }

    /**
     * Checks the obscure diagonals for a win condition. Only implemented in games with non-square boards
     *
     * @param logicalBoard The logical representation of the Tic Tac Toe board.
     * @param xPos         The x position of the last move.
     * @param yPos         The y position of the last move.
     * @param searchValue  The value to search for (either 1 for X or -1 for O).
     * @param loopLimit    The loop limit to prevent out-of-bounds access.
     * @return An integer representing the game state:
     * - 1 if no win condition is found.
     * - 11 for a diagonal win ascending from bottom left to top right.
     * - 13 for a diagonal win ascending from top left to bottom right.
     * or the product of those if both conditions are met.
     */
    private static int obscureDiagonalsChecker(int[][] logicalBoard, int xPos, int yPos, int searchValue, int loopLimit) {
        int returnVal = 1;

        if ((xPos + yPos == logicalBoard.length - 1))
            returnVal *= dexterAscendantCheck(logicalBoard, searchValue, loopLimit);

        if (xPos - yPos == logicalBoard[0].length - logicalBoard.length)
            returnVal *= sinisterAscendantCheck(logicalBoard, searchValue, loopLimit);

        return returnVal;
    }

    /**
     * Checks if there is a win condition in the same row (rank) as the clicked cell.
     *
     * @param logicalBoard The logical representation of the Tic Tac Toe board.
     * @param yPos         The y position of the last move.
     * @param searchValue  The value to search for (either 1 for X or -1 for O).
     * @return An integer representing the game state:
     * - 1 if no win condition is found.
     * - 2 if there is a win condition in the same row.
     */
    private static int rankCheck(int[][] logicalBoard, int yPos, int searchValue) {
        int gameIsOver = 2;

        for (int i = 0; i < logicalBoard[0].length; i++) {
            if (logicalBoard[yPos][i] != searchValue) {
                gameIsOver = 1;
                break;
            }
        }

        return gameIsOver;
    }

    /**
     * Checks if there is a win condition in the same column (file) as the clicked cell.
     *
     * @param logicalBoard The logical representation of the Tic Tac Toe board.
     * @param xPos         The x position of the last move.
     * @param searchValue  The value to search for (either 1 for X or -1 for O).
     * @return An integer representing the game state:
     * - 1 if no win condition is found.
     * - 3 if there is a win condition in the same column.
     */
    private static int fileCheck(int[][] logicalBoard, int xPos, int searchValue) {
        int gameIsOver = 3;

        for (int[] row : logicalBoard) {
            if (row[xPos] != searchValue) {
                gameIsOver = 1;
                break;
            }
        }

        return gameIsOver;
    }

    /**
     * Checks if there is a win condition in the main diagonal (descending dexter).
     *
     * @param logicalBoard The logical representation of the Tic Tac Toe board.
     * @param searchValue  The value to search for (either 1 for X or -1 for O).
     * @param loopLimit    The limit for the loop iteration.
     * @return An integer representing the game state:
     * - 1 if no win condition is found.
     * - 5 if there is a win condition in the main diagonal.
     */
    private static int dexterCheck(int[][] logicalBoard, int searchValue, int loopLimit) {
        int gameIsOver = 5;

        //should count until either the rows or columns run out
        for (int i = 0; i < loopLimit; i++) {
            if (logicalBoard[i][i] != searchValue) {
                gameIsOver = 1;
                break;
            }
        }

        return gameIsOver;
    }

    /**
     * Checks if there is a win condition in the secondary diagonal (descending sinister).
     *
     * @param logicalBoard The logical representation of the Tic Tac Toe board.
     * @param searchValue  The value to search for (either 1 for X or -1 for O).
     * @param loopLimit    The limit for the loop iteration.
     * @return An integer representing the game state:
     * - 1 if no win condition is found.
     * - 7 if there is a win condition in the secondary diagonal.
     */
    private static int sinisterCheck(int[][] logicalBoard, int searchValue, int loopLimit) {
        int gameIsOver = 7;

        //should count until either the rows or columns run out
        for (int i = 0; i < loopLimit; i++) {
            //checks from top down, that is, from top left back and down.
            if (logicalBoard[i][logicalBoard[0].length - 1 - i] != searchValue) {
                gameIsOver = 1;
                break;
            }
        }

        return gameIsOver;
    }

    /**
     * Checks if there is a win condition in the diagonal ascending from bottom left to top right (ascending dexter).
     * Only called in the event that the game board is not square
     *
     * @param logicalBoard The logical representation of the Tic Tac Toe board.
     * @param searchValue  The value to search for (either 1 for X or -1 for O).
     * @param loopLimit    The limit for the loop iteration.
     * @return An integer representing the game state:
     * - 1 if no win condition is found.
     * - 11 if there is a win condition in the diagonal ascending from bottom left to top right.
     */
    private static int dexterAscendantCheck(int[][] logicalBoard, int searchValue, int loopLimit) {
        int gameIsOver = 11;

        //should count until either the rows or columns run out
        for (int i = 0; i < loopLimit; i++) {
            //checks from top down, that is, from top left back and down.
            if (logicalBoard[logicalBoard.length - 1 - i][i] != searchValue) {
                gameIsOver = 1;
                break;
            }
        }

        return gameIsOver;
    }

    /**
     * Checks if there is a win condition in the diagonal ascending from top left to bottom right (ascending sinister).
     * Only called in the event that the game board is not square
     *
     * @param logicalBoard The logical representation of the Tic Tac Toe board.
     * @param searchValue  The value to search for (either 1 for X or -1 for O).
     * @param loopLimit    The limit for the loop iteration.
     * @return An integer representing the game state:
     * - 1 if no win condition is found.
     * - 13 if there is a win condition in the diagonal ascending from top left to bottom right.
     */
    private static int sinisterAscendantCheck(int[][] logicalBoard, int searchValue, int loopLimit) {
        int gameIsOver = 13;

        //should count until either the rows or columns run out
        for (int i = 0; i < loopLimit; i++) {
            //checks from top down, that is, from top left back and down.
            if (logicalBoard[logicalBoard.length - 1 - i][logicalBoard[0].length - 1 - i] != searchValue) {
                gameIsOver = 1;
                break;
            }
        }

        return gameIsOver;
    }
}