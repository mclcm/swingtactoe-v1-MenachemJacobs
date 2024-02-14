import javax.swing.*;

/**
 * This class manages the game state logic of the Tic Tac Toe game.
 */
public class GameStateLogic {
    //Message to be returned to the lblUpdater if the game ends
    private String gameOverLegend = "Something has gone horribly wrong if this is displayed";

    //0 is an ongoing game. 1 is a rank win, 2 a file win. 3 is a dexter win, and 4 is sinister. -1 is a cats eye.
    private int gameState = 0;

    //Track which player's turn it currently is
    private Boolean isXTurn = true;

    //Number of turns that have elapsed under the current logical instance
    private int turnCounter = 0;

    //Logical analogue of the GUI board
    private int[][] logicalBoard;

    /**
     * Constructs a new game state logic object.
     *
     * @param height The height of the game board.
     * @param length The length of the game board.
     */
    GameStateLogic(int height, int length) {
        logicalBoard = new int[height][length];

//        //set all positions in the logical board to 0.
//        // Apparently these default to zero, so nothing needs to be done.
//        for (int[] row : logicalBoard) {  for (int cell : row) {  cell = 0;   }   }
    }

    /**
     * Custom JButton class with additional properties.
     */
    public static class MyButton extends JButton {

        //xPos of this button. Analogous to this button's Length position in the board
        private final int xPos;
        //xPos of this button. Analogous to this button's Height position in the board
        private final int yPos;
        //Bool value tracking if this button has been pressed already
        public boolean isPressed;

        /**
         * Constructs a new MyButton.
         *
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
         *
         * @return The x position.
         */
        public int getXPos() {
            return xPos;
        }

        /**
         * Get the y position of the button.
         *
         * @return The y position.
         */
        public int getYPos() {
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

        //One would hope that the GUI implementing this class would have run the check on its end, but there is no way to know
        if (!clickedButton.isPressed && gameState == 0) {
            clickedButton.isPressed = true;

            //set the value on the logicalBoard
            logicalBoard[clickedButton.getYPos()][clickedButton.getXPos()] = isXTurn ? 1 : -1;

            //check if game is over and update the relevant state if so
            gameOverHandler(clickedButton);

            //update turn state
            turnCounter++;
            isXTurn = !isXTurn;

            //update the text in the clicked button.
            return !isXTurn ? "X" : "O";
        }

        //Ideally, clicking a disabled button would do nothing, but the responsibility for checking that is delegated too far up stream
        return clickedButton.getText();
    }

    /**
     * Handles the logic when the game is over.
     *
     * @param clickedButton The button that was clicked.
     */
    public void gameOverHandler(MyButton clickedButton) {
        //check if someone has a win condition
        gameState = GameOverLogic.isGameOver(logicalBoard, clickedButton.getXPos(), clickedButton.getYPos());

        //if someone has a win condition set the win tag.
        if (gameState != 0) gameOverLegend = "Game is over, " + (isXTurn ? "X" : "O") + " won";
            //check if this turn fills the board, and call a cat's eye if it is.
        else if (turnCounter >= logicalBoard.length * logicalBoard[0].length - 1) {
            gameOverLegend = "Game is over, cat's eye";
            gameState = -1;
        }
    }

    /**
     * Updates the label text based on the current turn, unless the game has ended
     * in which case the gameOverLegend is returned instead.
     *
     * @return The text to be displayed on the information label.
     */
    public String lblUpdater() {
        if (gameState != 0) {
            return gameOverLegend;
        }
        return "It is player " + (isXTurn ? "X's" : "O's") + " turn";
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean getGameIsOver() {
        return gameState != 0;
    }

    void testingBoardAccepter(int[][] acceptedBoard){
        logicalBoard = acceptedBoard;
    }

    Boolean getXTurn() {
        return isXTurn;
    }

    int getGameState(){
        return gameState;
    }
}