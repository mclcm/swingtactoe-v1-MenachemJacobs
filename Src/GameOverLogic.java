import javax.swing.*;
import java.util.Objects;

public class GameOverLogic {

    public static boolean isGameOver(int[][] logicalBoard, int xPos, int yPos) {
        boolean returnVal;
        int searchValue = logicalBoard[xPos][yPos];
        if (searchValue == 0)
            throw new IllegalArgumentException("If the clicked value remains 0, it has not been set properly");


        return rankCheck(logicalBoard, xPos, searchValue) || fileCheck(logicalBoard, yPos, searchValue) || diagonalCheck(logicalBoard, xPos, yPos, searchValue);
    }

    private static boolean rankCheck(int[][] logicalBoard, int xPos, int searchValue) {
        boolean gameIsOver = true;

        for (int i = 0; i < logicalBoard.length; i++) {
            if (logicalBoard[xPos][i] != searchValue) {
                gameIsOver = false;
                break;
            }
        }

        return gameIsOver;
    }

    private static boolean fileCheck(int[][] logicalBoard, int yPos, int searchValue) {
        boolean gameIsOver = true;

        for (int i = 0; i < logicalBoard[0].length; i++) {
            if (logicalBoard[i][yPos] != searchValue) {
                gameIsOver = false;
                break;
            }
        }

        return gameIsOver;
    }

    private static boolean diagonalCheck(int[][] logicalBoard, int xPos, int yPos, int searchValue) {

        //check for descending dexter
        if(xPos == yPos) return dexterCheck(logicalBoard, xPos, yPos, searchValue);
        //check for descending sinister
        else if (xPos + yPos == logicalBoard[0].length - 1) return sinisterCheck(logicalBoard, xPos, yPos, searchValue);

        //The ascendant checks are only necessary if the board is not a square. The assignment seems to assume the board is always 3, 3
//       else if (buttons.length != buttons[0].length &&)
//            return dexterAscendantCheck(buttons, isXTurn)
//       else if (buttons.length != buttons[0].length &&)
//            return sinisterAscendantCheck(buttons, isXTurn);

        else return false;
    }

    private static boolean dexterCheck(int[][] logicalBoard, int xPos, int yPos, int searchValue) {
        System.out.println("dexter is go");
        boolean gameIsOver = true;
        int firstVal = logicalBoard[0][0];

        for (int i = 0; i < logicalBoard.length; i++) {
            if (logicalBoard[i][i] != firstVal || (logicalBoard[i][i] == 0)) {
                gameIsOver = false;
                break;
            }
        }

        if(gameIsOver) System.out.println("dexter win");

        return gameIsOver;
    }

    private static boolean sinisterCheck(int[][] logicalBoard, int xPos, int yPos, int searchValue) {
        System.out.println("sinister is go");
        boolean gameIsOver = true;
        int firstVal = logicalBoard[0][logicalBoard[0].length - 1];

        for (int i = 0; i < logicalBoard.length; i++) {
            //checks from top down, that is, from top left back and down.
            if (logicalBoard[i][logicalBoard[0].length - 1 - i] != firstVal || logicalBoard[i][logicalBoard[0].length - 1 - i] == 0) {
                gameIsOver = false;
                break;
            }
        }

        if(gameIsOver) System.out.println("sinister win");

        return gameIsOver;
    }
}