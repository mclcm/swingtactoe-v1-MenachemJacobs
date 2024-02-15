import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicTacToeTest {
    GameStateLogic testingGame;
    final int HEIGHT = 3;
    final int LENGTH = 3;
    int state;

    @BeforeEach
    void setUp() {
        testingGame = new GameStateLogic(HEIGHT, LENGTH);
    }

    @Test
    void turnTracker(){
        Assertions.assertTrue(testingGame.getXTurn());
        turnTaker(0,0);
        Assertions.assertFalse(testingGame.getXTurn(), "turn isn't updating after a click");
        Assertions.assertEquals("It is player O's turn", testingGame.lblUpdater(), "lblUpdater is changing the lbl despite game not ending");
    }

    @Test
    void turnTracker_Edge_doubleClick(){
        GameStateLogic.MyButton testingButton = new GameStateLogic.MyButton(0, 0);

        testingGame.btnMouseClicked(testingButton);
        testingGame.btnMouseClicked(testingButton);
        Assertions.assertFalse(testingGame.getXTurn(), "clicking the same button twice is updating the state. It should not.");
        Assertions.assertEquals("It is player O's turn", testingGame.lblUpdater(), "lblUpdater is changing the lbl despite game not ending");
    }

    @Test
    void gameNotOver(){
        turnTaker(2,1);

        Assertions.assertFalse(testingGame.getGameIsOver(), "A is being scored incorrectly by the game over handler");
        Assertions.assertEquals("It is player O's turn", testingGame.lblUpdater(), "lblUpdater is changing the lbl despite game not ending");
    }

    @Test
    void rankWin() {
        state = 2;
        setBoard(state);
        testingGame.gameOverHandler(new GameStateLogic.MyButton(1, 1));

        Assertions.assertEquals(state, testingGame.getGameState(), "A rank win is not being scored correctly by the game over handler");
        Assertions.assertEquals("Game is over, X won", testingGame.lblUpdater(), "Rank win isn't reflected properly in the lblUpdater");
    }

    @Test
    void fileWin() {
        state = 3;
        setBoard(state);
        testingGame.gameOverHandler(new GameStateLogic.MyButton(1, 1));

        Assertions.assertEquals(state, testingGame.getGameState(), "A file win is not being scored correctly by the game over handler");
        Assertions.assertEquals("Game is over, X won", testingGame.lblUpdater(), "File win isn't reflected properly in the lblUpdater");
    }

    @Test
    void dexterWin() {
        state = 5;
        setBoard(state);
        testingGame.gameOverHandler(new GameStateLogic.MyButton(1, 1));

        Assertions.assertEquals(state, testingGame.getGameState(), "A dexter win is not being scored correctly by the game over handler");
        Assertions.assertEquals("Game is over, X won", testingGame.lblUpdater(), "Dexter win isn't reflected properly in the lblUpdater");
    }

    @Test
    void sinisterWin() {
        state = 7;
        setBoard(state);
        testingGame.gameOverHandler(new GameStateLogic.MyButton(1, 1));

        Assertions.assertEquals(state, testingGame.getGameState(), "A sinister win is not being scored correctly by the game over handler");
        Assertions.assertEquals("Game is over, X won", testingGame.lblUpdater(), "Sinister win isn't reflected properly in the lblUpdater");
    }

    @Test
    void catsEye() {
        state = -1;
        setBoard(state);

        Assertions.assertEquals(state, testingGame.getGameState(), "A cats eye is not being correctly scored by the game over handler");
        Assertions.assertEquals("Game is over, cat's eye", testingGame.lblUpdater(), "Cat's Eye reflected properly in the lblUpdater");
    }

    void setBoard(int choice) {
        int[][] passingBoard = new int[3][3];

        switch (choice) {
            //game not over case
            case 0:
                //x
                turnTaker(0,0);
                //y
                turnTaker(1,1);
                //x
                turnTaker(2,0);
                //y
                turnTaker(1,0);

                break;
            //rank win case
            case 2:
                //x
                turnTaker(0,1);
                //y
                turnTaker(0,0);
                //x
                turnTaker(2,1);
                //y
                turnTaker(1,0);
                //x
                turnTaker(1,1);
                break;
            //file win case
            case 3:
                //x
                turnTaker(1,0);
                //y
                turnTaker(0,0);
                //x
                turnTaker(1,2);
                //y
                turnTaker(0,1);
                //x
                turnTaker(1,1);
                break;
            //dexter win case
            case 5:
                //x
                turnTaker(0,0);
                //y
                turnTaker(1,0);
                //x
                turnTaker(2,2);
                //y
                turnTaker(2,1);
                //x
                turnTaker(1,1);
                break;
            //sinister win case
            case 7:
                //x
                turnTaker(2,0);
                //y
                turnTaker(1,0);
                //x
                turnTaker(0,2);
                //y
                turnTaker(2,1);
                //x
                turnTaker(1,1);
                break;
            //Cats eye case
            case -1:
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