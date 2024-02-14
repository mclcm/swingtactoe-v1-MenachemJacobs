import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class TicTacToeTest {
    GameStateLogic testingGame;
    GameStateLogic.MyButton testingButton;
    final int HEIGHT = 3;
    final int LENGTH = 3;

    @BeforeEach
    void setUp() {
        testingGame = new GameStateLogic(HEIGHT, LENGTH);
    }

    @Test
    void turnTracker(){
        Assertions.assertTrue(testingGame.getXTurn());
        turnTaker(0,0);
        Assertions.assertFalse(testingGame.getXTurn());
    }

    @Test
    void gameNotOver(){
        turnTaker(2,1);

        Assertions.assertFalse(testingGame.getGameIsOver(), "A is being scored incorrectly by the game over handler");
        Assertions.assertEquals("It is player O's turn", testingGame.lblUpdater(), "lblUpdater is changing the lbl despite game not ending");
    }

    @Test
    void rankWin() {
        setSimpleBoard(1);
        testingGame.gameOverHandler(new GameStateLogic.MyButton(1, 1));

        Assertions.assertTrue(testingGame.getGameIsOver(), "A rank win is not being scored correctly by the game over handler");
        Assertions.assertEquals("Game is over, X won", testingGame.lblUpdater(), "Rank win isn't reflected properly in the lblUpdater");
    }

    @Test
    void fileWin() {
        setSimpleBoard(2);
        testingGame.gameOverHandler(new GameStateLogic.MyButton(1, 1));

        Assertions.assertTrue(testingGame.getGameIsOver(), "A file win is not being scored correctly by the game over handler");
        Assertions.assertEquals("Game is over, X won", testingGame.lblUpdater(), "File win isn't reflected properly in the lblUpdater");
    }

    @Test
    void dexterWin() {
        setSimpleBoard(3);
        testingGame.gameOverHandler(new GameStateLogic.MyButton(1, 1));

        Assertions.assertTrue(testingGame.getGameIsOver(), "A dexter win is not being scored correctly by the game over handler");
        Assertions.assertEquals("Game is over, X won", testingGame.lblUpdater(), "Dexter win isn't reflected properly in the lblUpdater");
    }

    @Test
    void sinisterWin() {
        setSimpleBoard(4);
        testingGame.gameOverHandler(new GameStateLogic.MyButton(1, 1));

        Assertions.assertTrue(testingGame.getGameIsOver(), "A sinister win is not being scored correctly by the game over handler");
        Assertions.assertEquals("Game is over, X won", testingGame.lblUpdater(), "Sinister win isn't reflected properly in the lblUpdater");
    }

    @Test
    void catsEye() {
        //1
        turnTaker(0,0);
        //2
        turnTaker(1,1);
        //3
        turnTaker(0,2);
        //4
        turnTaker(0,1);
        //5
        turnTaker(2,1);
        //6
        turnTaker(1,0);
        //7
        turnTaker(1,2);
        //8
        turnTaker(2,2);
        //9
        turnTaker(2,0);

        Assertions.assertTrue(testingGame.getGameIsOver(), "A cats eye is not being correctly scored by the game over handler");
        Assertions.assertEquals("Game is over, cat's eye", testingGame.lblUpdater(), "Cat's Eye reflected properly in the lblUpdater");
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
            //game not over case
            case 0:
                passingBoard[0][0] = 1;
                passingBoard[0][2] = 1;
                passingBoard[1][1] = -1;
                passingBoard[0][1] = -1;

                testingGame.testingBoardAccepter(passingBoard);
                break;
            //rank win case
            case 1:
                passingBoard[1][0] = 1;
                passingBoard[1][1] = 1;
                passingBoard[1][2] = 1;

                testingGame.testingBoardAccepter(passingBoard);
                break;
            //file win case
            case 2:
                passingBoard[0][1] = 1;
                passingBoard[1][1] = 1;
                passingBoard[2][1] = 1;

                testingGame.testingBoardAccepter(passingBoard);
                break;
            //dexter win case
            case 3:
                passingBoard[0][0] = 1;
                passingBoard[1][1] = 1;
                passingBoard[2][2] = 1;

                testingGame.testingBoardAccepter(passingBoard);
                break;
            //sinister win case
            case 4:
                passingBoard[0][2] = 1;
                passingBoard[1][1] = 1;
                passingBoard[2][0] = 1;

                testingGame.testingBoardAccepter(passingBoard);
                break;
            //Cats eye case
            case 5:
                //The board for the cat's eye should be constructed in the test itself.
                // The case is preserved only to agree with the game state tracker in the State Logic.
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    void turnTaker(int x, int y){
        testingGame.btnMouseClicked(new GameStateLogic.MyButton(x, y));
    }
}