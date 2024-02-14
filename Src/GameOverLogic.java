import javax.swing.*;
import java.util.Objects;

public class GameOverLogic {

    public static boolean isGameOver(int[][] logicalBoard, int xPos, int yPos) {
        int searchValue = logicalBoard[yPos][xPos];
        if (searchValue == 0)
            throw new IllegalArgumentException("If the clicked value remains 0, it has not been set properly");


        return rankCheck(logicalBoard, yPos, searchValue) || fileCheck(logicalBoard, xPos, searchValue) || diagonalCheck(logicalBoard, xPos, yPos, searchValue);
    }

    private static boolean rankCheck(int[][] logicalBoard, int yPos, int searchValue) {
        boolean gameIsOver = true;

        for (int i = 0; i < logicalBoard[0].length; i++) {
            if (logicalBoard[yPos][i] != searchValue) {
                gameIsOver = false;
                break;
            }
        }

        return gameIsOver;
    }

    private static boolean fileCheck(int[][] logicalBoard, int xPos, int searchValue) {
        boolean gameIsOver = true;

        for (int[] row : logicalBoard) {
            if (row[xPos] != searchValue) {
                gameIsOver = false;
                break;
            }
        }

        return gameIsOver;
    }

    private static boolean diagonalCheck(int[][] logicalBoard, int xPos, int yPos, int searchValue) {

        //check for descending dexter
        if (xPos == yPos) return dexterCheck(logicalBoard, searchValue);
            //check for descending sinister
        else if (xPos + yPos == logicalBoard[0].length - 1) return sinisterCheck(logicalBoard, searchValue);

            //The ascendant checks are only necessary if the board is not a square. The assignment seems to assume the board is always 3, 3
//       else if (buttons.length != buttons[0].length &&)
//            return dexterAscendantCheck(buttons, isXTurn)
//       else if (buttons.length != buttons[0].length &&)
//            return sinisterAscendantCheck(buttons, isXTurn);

        else return false;
    }

    private static boolean dexterCheck(int[][] logicalBoard, int searchValue) {
        boolean gameIsOver = true;

        //the loop should only check until the smaller of the two values
        int loopLimit = Math.min(logicalBoard.length, logicalBoard[0].length);

        //should count until the row runs out, not the column
        for (int i = 0; i < loopLimit; i++) {
            if (logicalBoard[i][i] != searchValue) {
                gameIsOver = false;
                break;
            }
        }

        if (gameIsOver) System.out.println("dexter win");

        return gameIsOver;
    }

    private static boolean sinisterCheck(int[][] logicalBoard, int searchValue) {
        boolean gameIsOver = true;

        //the loop should only check until the smaller of the two values
        int loopLimit = Math.min(logicalBoard.length, logicalBoard[0].length);

        //should count until the row runs out, not the column
        for (int i = 0; i < loopLimit; i++) {
            //checks from top down, that is, from top left back and down.
            if (logicalBoard[i][logicalBoard[0].length - 1 - i] != searchValue) {
                gameIsOver = false;
                break;
            }
        }

        if (gameIsOver) System.out.println("sinister win");

        return gameIsOver;
    }
}