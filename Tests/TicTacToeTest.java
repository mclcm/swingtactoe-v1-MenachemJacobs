import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class TicTacToeTest {
    GameStateLogic testingGame;
    final int HEIGHT = 3;
    final int LENGTH = 3;

    @BeforeEach
    void setUp() {
        testingGame = new GameStateLogic(HEIGHT, LENGTH);
    }

    @Test
    void rankWin() {
        setSimpleBoard(0);
        GameStateLogic.MyButton testingButton = new GameStateLogic.MyButton(1, 1);
        testingGame.gameOverHandler(testingButton);

        Assertions.assertTrue(testingGame.getGameIsOver(), "A rank win is not being scored correctly by the game over handler");
        Assertions.assertEquals(testingGame.lblUpdater(), "Game is over, X won", "Rank win isn't reflected properly in the lblUpdater");
    }

    @Test
    void fileWin() {
        setSimpleBoard(1);
        GameStateLogic.MyButton testingButton = new GameStateLogic.MyButton(1, 1);
        testingGame.gameOverHandler(testingButton);

        Assertions.assertTrue(testingGame.getGameIsOver(), "A file win is not being scored correctly by the game over handler");
        Assertions.assertEquals(testingGame.lblUpdater(), "Game is over, X won", "File win isn't reflected properly in the lblUpdater");
    }

    @Test
    void dexterWin() {
        setSimpleBoard(2);
        GameStateLogic.MyButton testingButton = new GameStateLogic.MyButton(1, 1);
        testingGame.gameOverHandler(testingButton);

        Assertions.assertTrue(testingGame.getGameIsOver(), "A dexter win is not being scored correctly by the game over handler");
        Assertions.assertEquals(testingGame.lblUpdater(), "Game is over, X won", "Dexter win isn't reflected properly in the lblUpdater");
    }

    @Test
    void sinisterWin() {
        setSimpleBoard(3);
        GameStateLogic.MyButton testingButton = new GameStateLogic.MyButton(1, 1);
        testingGame.gameOverHandler(testingButton);

        Assertions.assertTrue(testingGame.getGameIsOver(), "A sinister win is not being scored correctly by the game over handler");
        Assertions.assertEquals(testingGame.lblUpdater(), "Game is over, X won", "Sinister win isn't reflected properly in the lblUpdater");
    }

    @Test
    void btnMouseClicked_Normal() {
        GameStateLogic.MyButton testingButton = new GameStateLogic.MyButton(0, 0);

        //testingGame
    }

    @Test
    void lblUpdater() {
    }

    @Test
    void gameIsOverGetter() {
    }

    @Test
    void isGameOver() {
    }

    void setSimpleBoard(int choice) {
        int[][] passingBoard = new int[3][3];

        switch (choice) {
            //rank win case
            case 0:
                passingBoard[1][0] = 1;
                passingBoard[1][1] = 1;
                passingBoard[1][2] = 1;

                testingGame.testingBoardAccepter(passingBoard);
                break;
            //file win case
            case 1:
                passingBoard[0][1] = 1;
                passingBoard[1][1] = 1;
                passingBoard[2][1] = 1;

                testingGame.testingBoardAccepter(passingBoard);
                break;
            //dexter win case
            case 2:
                passingBoard[0][0] = 1;
                passingBoard[1][1] = 1;
                passingBoard[2][2] = 1;

                testingGame.testingBoardAccepter(passingBoard);
                break;
            //sinister win case
            case 3:
                passingBoard[0][2] = 1;
                passingBoard[1][1] = 1;
                passingBoard[2][0] = 1;

                testingGame.testingBoardAccepter(passingBoard);
                break;
            case 4:
                System.out.println("Case 4");
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }

    }
}