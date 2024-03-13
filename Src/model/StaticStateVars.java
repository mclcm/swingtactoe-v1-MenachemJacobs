package model;

/**
 * Provides constants representing various game states and default cell values.
 */
public class StaticStateVars {

    /**
     * Represents the ongoing state of the game.
     */
    final static int ONGOING = 0;
    final static int CATS_EYE = -1;
    final static int RANK_WIN = 2;
    final static int FILE_WIN = 3;
    final static int DEXTER_WIN = 5;
    final static int SINISTER_WIN = 7;
    final static int DEXTER_ASCENDANT_WIN = 11;
    final static int SINISTER_ASCENDANT_WIN = 13;
    static final int cellDefaultVal = 0;

    /**
     * Returns the corresponding win code based on the provided code string.
     *
     * @param code The code string representing the win condition.
     * @return The corresponding win code.
     * @throws IllegalStateException if an illegal win code is passed.
     */
    public static int getWinCode(String code){

        return switch (code) {
            case "ongoing" -> ONGOING;
            case "rank" -> RANK_WIN;
            case "file" -> FILE_WIN;
            case "dexter" -> DEXTER_WIN;
            case "sinister" -> SINISTER_WIN;
            case "aDexter" -> DEXTER_ASCENDANT_WIN;
            case "aSinister" -> SINISTER_ASCENDANT_WIN;

            default -> throw new IllegalStateException("Illegal win code passed into getGameState");
        };
    }

    /**
     * Returns the default value of a cell on the game board.
     *
     * @return The default value of a cell.
     */
    public static int getCellDefaultVal(){
        return cellDefaultVal;
    }
}
