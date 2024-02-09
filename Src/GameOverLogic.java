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

    //TODO: As it stands, diagonal checks do not work
    private static boolean diagonalCheck(int[][] logicalBoard, int xPos, int yPos, int searchValue) {
        //if the button pressed was not on a diagonal skip the analysis
        if ((xPos + yPos) % 2 != 0)
            return false;

//        if (buttons.length != buttons[0].length)
//            return dexterAscendantCheck(buttons, isXTurn) || sinisterAscendantCheck(buttons, isXTurn) || dexterCheck(buttons, isXTurn) || sinisterCheck(buttons, isXTurn);
//        else
        return dexterCheck(logicalBoard, xPos, yPos, searchValue) || sinisterCheck(logicalBoard, xPos, yPos, searchValue);
    }

    private static boolean dexterCheck(int[][] logicalBoard, int xPos, int yPos, int searchValue) {
        boolean gameIsOver = true;
        int firstVal = logicalBoard[0][0];

        for (int i = 0; i < logicalBoard.length; i++) {
            if (logicalBoard[i][i] != firstVal || (logicalBoard[i][i] == 0)) {
                gameIsOver = false;
                break;
            }
        }

        return gameIsOver;
    }

    private static boolean sinisterCheck(int[][] logicalBoard, int xPos, int yPos, int searchValue) {
        boolean gameIsOver = true;
        int firstVal = logicalBoard[0][(logicalBoard[0].length) - 1];

        for (int i = 0; i < logicalBoard[0].length; i++) {
            if (logicalBoard[logicalBoard[0].length - i - 1][i] != firstVal || logicalBoard[logicalBoard[0].length - i - 1][i] == 0) {
                gameIsOver = false;
                break;
            }
        }

        return gameIsOver;
    }
}