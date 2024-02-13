import javax.swing.*;

/**
 * This class manages the game state logic of the Tic Tac Toe game.
 */
public class GameStateLogic {
    public String gameOverLegend = "Something has gone horribly wrong if this is displayed";
    private Boolean isXTurn = true;
    private Boolean gameIsOver = false;
    private int turnCounter = 0;
    public int[][] logicalBoard;

    /**
     * Constructs a new game state logic object.
     *
     * @param height The height of the game board.
     * @param length The length of the game board.
     */
    GameStateLogic(int height, int length) {
        logicalBoard = new int[height][length];

        //default the logical board to 0
        for (int[] row : logicalBoard) {
            for (int cell : row) {
                cell = 0;
            }
        }
    }

    /**
     * Custom JButton class with additional properties.
     */
    public static class MyButton extends JButton {

        private final int xPos;
        private final int yPos;
        public boolean isPressed;

        /**
         * Constructs a new MyButton.
         * @param xPos The x position of the button.
         * @param yPos The y position of the button.
         */
        public MyButton(int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;

            isPressed = false;
        }

        /**
         * Get the x position of the button.
         * @return The x position.
         */
        public int getXPos() {
            return xPos;
        }

        /**
         * Get the y position of the button.
         * @return The y position.
         */
        public int getYPos(){
            return yPos;
        }
    }

    /**
     * Handles the logic when a button is clicked. Cannot be called while the game is over.
     * More formally, this method will never be called while isGame over is true.
     *
     * @param clickedButton The button that was clicked.
     * @return The text to be displayed on the clicked button.
     */
    public String btnMouseClicked(MyButton clickedButton) {

        if (!clickedButton.isPressed && !gameIsOver) {
            clickedButton.isPressed = true;

            //set the value on the logicalBoard
            logicalBoard[clickedButton.getYPos()][clickedButton.getXPos()] = isXTurn ? 1 : -1;

            //check if game is over and update state if so
            gameOverHandler(clickedButton);

            //update turn state
            turnCounter++;
            isXTurn = !isXTurn;

            //update the text in the clicked button
            return !isXTurn ? "X" : "O";
        }

        //Ideally, clicking a disabled button would do nothing, but the responsibility for checking that is delegated too far down stream
        return clickedButton.getText();
    }

    /**
     * Handles the logic when the game is over.
     *
     * @param clickedButton The button that was clicked.
     */
    private void gameOverHandler(MyButton clickedButton) {
        //check if someone has a win condition
        gameIsOver = GameOverLogic.isGameOver(logicalBoard, clickedButton.getXPos(), clickedButton.getYPos());

        //if someone has a win condition set the win tag.
        if (gameIsOver) gameOverLegend = "Game is over, " + (isXTurn ? "X" : "O") + " won";
            //check if this turn fills the board, and call a cat's eye if it is.
        else if (turnCounter >= logicalBoard.length * logicalBoard[0].length - 1) {
            gameOverLegend = "Game is over, cat's eye";
            gameIsOver = true;
        }
    }

    /**
     * Updates the label text based on the game state.
     *
     * @return The text to be displayed on the label.
     */
    public String lblUpdater() {
        if (gameIsOver) {
            return gameOverLegend;
        }
        return "It is player " + (isXTurn ? "X" : "O") + " turn";
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean gameIsOverGetter() {
        return gameIsOver;
    }
}