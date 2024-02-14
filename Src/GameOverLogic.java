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
     * @return True if the game is over, false otherwise.
     * @throws IllegalArgumentException If the clicked value remains 0, indicating it has not been set properly.
     */
    public static int isGameOver(int[][] logicalBoard, int xPos, int yPos) {
        int returnVal = 0;

        int searchValue = logicalBoard[yPos][xPos];
        if (searchValue == 0)
            throw new IllegalArgumentException("If the clicked value remains 0, it has not been set properly");

        returnVal = rankCheck(logicalBoard, yPos, searchValue);

        if(returnVal == 0) returnVal = fileCheck(logicalBoard, xPos, searchValue);

        //diagonal check loops should only check until the smaller of the two values
        int loopLimit = Math.min(logicalBoard.length, logicalBoard[0].length);

        if(returnVal == 0) returnVal = dexterCheck(logicalBoard, searchValue, loopLimit);

        if(returnVal == 0) returnVal = sinisterCheck(logicalBoard, searchValue, loopLimit);

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
        int gameIsOver = 1;

        for (int i = 0; i < logicalBoard[0].length; i++) {
            if (logicalBoard[yPos][i] != searchValue) {
                gameIsOver = 0;
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
        int gameIsOver = 2;

        for (int[] row : logicalBoard) {
            if (row[xPos] != searchValue) {
                gameIsOver = 0;
                break;
            }
        }

        return gameIsOver;
    }

    /**
     * Checks if there is a win condition in any of the diagonals.
     *
     * @param logicalBoard The logical representation of the Tic Tac Toe board.
     * @param xPos         The x position of the last move.
     * @param yPos         The y position of the last move.
     * @param searchValue  The value of the last move.
     * @return True if there is a win condition in any diagonal, false otherwise.
     */
    private static int diagonalCheck(int[][] logicalBoard, int xPos, int yPos, int searchValue) {
        int returnVal = 0;

        //diagonal check loops should only check until the smaller of the two values
        int loopLimit = Math.min(logicalBoard.length, logicalBoard[0].length);

        //check for if clicked value is on the descending dexter
        if (xPos == yPos) returnVal = dexterCheck(logicalBoard, searchValue, loopLimit);
        //check for if clicked value is on the descending sinister
        if (xPos + yPos == logicalBoard[0].length - 1)
            returnVal = returnVal + sinisterCheck(logicalBoard, searchValue, loopLimit);

        //The ascendant checks are only necessary if the board is not a square. The assignment seems to assume the board is always 3, 3
//       else if (buttons.length != buttons[0].length &&)
//            return dexterAscendantCheck(logicalBoard, searchValue, loopLimit)
//       else if (buttons.length != buttons[0].length &&)
//            return sinisterAscendantCheck(logicalBoard, searchValue, loopLimit);

        return returnVal;
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
        int gameIsOver = 3;

        //should count until either the rows or columns run out
        for (int i = 0; i < loopLimit; i++) {
            if (logicalBoard[i][i] != searchValue) {
                gameIsOver = 0;
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
        int gameIsOver = 4;

        //should count until either the rows or columns run out
        for (int i = 0; i < loopLimit; i++) {
            //checks from top down, that is, from top left back and down.
            if (logicalBoard[i][logicalBoard[0].length - 1 - i] != searchValue) {
                gameIsOver = 0;
                break;
            }
        }

        return gameIsOver;
    }
}