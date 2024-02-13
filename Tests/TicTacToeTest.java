import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicTacToeTest {
    GameStateLogic testingGame;
    final int HEIGHT = 3;
    final int LENGTH = 3;

    @BeforeEach
    void setUp() {
        testingGame = new GameStateLogic(HEIGHT, LENGTH);
    }

    @Test
    void btnMouseClicked_Normal() {
        GameStateLogic.MyButton testingButton = new GameStateLogic.MyButton(-1,-1);

        testingGame
    }

    @Test
    void lblUpdater() {
    }

    @Test
    void gameIsOverGetter() {
    }

    void setTestingGame(int[][] testBoard) {
        testingGame.testEnabler(testBoard);
    }

    @Test
    void isGameOver() {
    }
}