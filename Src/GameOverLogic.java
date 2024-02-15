/**
 * The GameOverLogic class provides static methods to determine if the game is over
 * based on the current state of the Tic Tac Toe board.
 */
public class GameOverLogic {

    private static int numOfEndGameConditions = 4;

    /**
     * Determines if the game is over based on the current state of the board.
     *
     * @param logicalBoard The logical representation of the Tic Tac Toe board.
     * @param xPos         The x position of the last move.
     * @param yPos         The y position of the last move.
     * @return int returnVal representing the game state.
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

        return returnVal == 1 ? 0 : returnVal;
    }

    /**
     * Checks the main diagonals for a win condition.
     *
     * @param logicalBoard The logical representation of the Tic Tac Toe board.
     * @param xPos         The x position of the last move.
     * @param yPos         The y position of the last move.
     * @param searchValue  The value to search for (either 1 for X or -1 for O).
     * @return int returnVal representing the game state.
     */
    private static int diagonalsChecker(int[][] logicalBoard, int xPos, int yPos, int searchValue){
        int returnVal = 1;

        //diagonal check loops should only check until the smaller of the two values
        int loopLimit = Math.min(logicalBoard.length, logicalBoard[0].length);

        if (xPos == yPos) returnVal *= dexterCheck(logicalBoard, searchValue, loopLimit);

        if (xPos + yPos == logicalBoard[0].length - 1) returnVal *= sinisterCheck(logicalBoard, searchValue, loopLimit);

        //if nothing has yet been found, and the board is not square, the last resort is to check the more obscure diagonals
        if (logicalBoard.length != logicalBoard[0].length)  returnVal *= obscureDiagonalsChecker(logicalBoard, xPos, yPos, searchValue, loopLimit);

        return returnVal;
    }

    /**
     * Checks the obscure diagonals for a win condition.
     *
     * @param logicalBoard The logical representation of the Tic Tac Toe board.
     * @param xPos         The x position of the last move.
     * @param yPos         The y position of the last move.
     * @param searchValue  The value to search for (either 1 for X or -1 for O).
     * @param loopLimit    The loop limit to prevent out-of-bounds access.
     * @return int returnVal representing the game state.
     */
    private static int obscureDiagonalsChecker(int[][] logicalBoard, int xPos, int yPos, int searchValue, int loopLimit){
        int returnVal = 1;

        if((xPos + yPos == logicalBoard.length - 1)) returnVal *= dexterAscendantCheck(logicalBoard, searchValue, loopLimit);

        if(xPos - yPos == logicalBoard[0].length - logicalBoard.length) returnVal *= sinisterAscendantCheck(logicalBoard, searchValue, loopLimit);

        return returnVal;
    }

    /**
     * Checks if there is a win condition in the same row (rank) as the clicked cell.
     *
     * @param logicalBoard The logical representation of the Tic Tac Toe board.
     * @param yPos         The y position of the last move.
     * @param searchValue  The value of the last move.
     * @return True if there is a win condition in the same row, false otherwise.
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
     * @param searchValue  The value of the last move.
     * @return True if there is a win condition in the same column, false otherwise.
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
     * @param searchValue  The value of the last move.
     * @param loopLimit    The limit for the loop iteration.
     * @return True if there is a win condition in the main diagonal, false otherwise.
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
     * @param searchValue  The value of the last move.
     * @param loopLimit    The limit for the loop iteration.
     * @return True if there is a win condition in the secondary diagonal, false otherwise.
     */
    private static int sinisterCheck(int[][] logicalBoard, int searchValue, int loopLimit) {
        int gameIsOver = 5;

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

    private static int dexterAscendantCheck(int[][] logicalBoard, int searchValue, int loopLimit) {
        return 0;
    }

    private static int sinisterAscendantCheck(int[][] logicalBoard, int searchValue, int loopLimit){
        return 0;
    }
}