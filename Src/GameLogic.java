import javax.swing.*;
import java.util.Objects;

public class GameLogic {

    public static boolean isGameOver(JButton[][] buttons, boolean isXTurn, final int HEIGHT, final int LENGTH) {
        return rankCheck(buttons, isXTurn) || fileCheck(buttons, isXTurn) || diagonalCheck(buttons, isXTurn, HEIGHT, LENGTH);
    }

    private static boolean rankCheck(JButton[][] buttons, boolean isXTurn) {
        boolean gameIsOver = true;

        //check all rows, so long as a solid one isn't found
        for (JButton[] buttonRow : buttons) {
            gameIsOver = true;
            String previousText = buttonRow[0].getText();

            //check a single row for either an empty spot or a dissimilarity. set gameIsOver to false and break if found.
            for (JButton button : buttonRow) {

                if (!Objects.equals(button.getText(), previousText) || Objects.equals(button.getText(), "")) {
                    gameIsOver = false;
                    break;
                }
            }

            //if no dissimilarity was found to reset the gameIsOver state
            if (gameIsOver)
                break;
        }

        return gameIsOver;
    }

    private static boolean fileCheck(JButton[][] buttons, boolean isXTurn) {
        boolean gameIsOver = true;

        //check all columns, so long as a solid one isn't found
        for (int i = 0; i < buttons[0].length; i++) {
            gameIsOver = true;
            String previousText = buttons[0][i].getText();

            //check a single column (i) for either an empty spot or a dissimilarity. set gameIsOver to false and break if found.
            for (JButton[] button : buttons) {

                if (!Objects.equals(button[i].getText(), previousText) || Objects.equals(button[i].getText(), "")) {
                    gameIsOver = false;
                    break;
                }
            }

            //if no dissimilarity was found to reset the gameIsOver state
            if (gameIsOver)
                break;
        }

        return gameIsOver;
    }

    private static boolean diagonalCheck(JButton[][] buttons, boolean isXTurn, final int HEIGHT, final int LENGTH) {
//        if (buttons.length != buttons[0].length)
//            return dexterAscendantCheck(buttons, isXTurn) || sinisterAscendantCheck(buttons, isXTurn) || dexterCheck(buttons, isXTurn) || sinisterCheck(buttons, isXTurn);
//        else
            return dexterCheck(buttons, isXTurn) || sinisterCheck(buttons, isXTurn);
    }

    private static boolean dexterCheck(JButton[][] buttons, boolean isXTurn) {
        boolean gameIsOver = true;
        String previousText = buttons[0][0].getText();

        for (int i = 0; i < buttons.length; i++) {
            if (!Objects.equals(buttons[i][i].getText(), previousText) || Objects.equals(buttons[i][i].getText(), "")) {
                gameIsOver = false;
                break;
            }
        }

        return gameIsOver;
    }

    private static boolean sinisterCheck(JButton[][] buttons, boolean isXTurn) {
        boolean gameIsOver = true;
        String previousText = buttons[0][(buttons[0].length) - 1].getText();

        for (int i = 0; i < buttons[0].length; i++) {
            if (!Objects.equals(buttons[buttons[0].length - i - 1][i].getText(), previousText) || Objects.equals(buttons[buttons[0].length - i - 1][i].getText(), "")) {
                gameIsOver = false;
                break;
            }
        }

        return gameIsOver;
    }
}
