package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * A warning panel indicating move pressure in a game.
 * <p>
 * This class extends the WarningPanel abstract class and represents a warning panel
 * used to indicate move pressure. It changes the background color over time
 * to signal advancing levels of pressure or urgency to the player.
 */
public class MovePressure extends WarningPanel {
    private int colorIndex = 0;
    private static final int DEFAULT_LENGTH = 3;

    /**
     * Constructs a MovePressure warning panel with default length and message.
     */
    MovePressure() {
        initialize(DEFAULT_LENGTH, getWarning());
    }

    /**
     * Constructs a MovePressure warning panel with specified length and message.
     *
     * @param length        The length of time in seconds for each color change.
     * @param passedMessage The message to be displayed on the panel.
     */
    MovePressure(int length, String passedMessage) {
        initialize(length, passedMessage);
    }

    /**
     * Initializes the MovePressure warning panel.
     * <p>
     * This method configures the MovePressure warning panel with the specified duration
     * for each color and the message to be displayed. It sets the background color
     * of the panel, adds a JLabel with the passed message, and initializes a Timer
     * for color change intervals based on the given length.
     *
     * @param length        The length of time in seconds for each color change.
     * @param passedMessage The message to be displayed on the panel.
     */
    private void initialize(int length, String passedMessage) {
        setBackground(colorList[colorIndex++]);
        JLabel myMessage = new JLabel(passedMessage);
        add(myMessage);

        //convert input length from seconds into milliseconds
        span = new Timer(length * 1000, this);
    }

    /**
     * Sets custom color pool for the warning panel.
     *
     * @param passedColors The array of colors to be used for background colors.
     */
    private void setColors(Color[] passedColors) {
        colorList = passedColors;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (colorIndex < colorList.length)
            setBackground(colorList[colorIndex++]);
        else
            span.stop();
    }

    /**
     * Resets the warning panel to its initial color, and resets the timer.
     */
    public void restate() {
        colorIndex = 0;
        setBackground(colorList[colorIndex++]);
        span.restart();
    }

    /**
     * Retrieves the default warning message to be displayed on the panel.
     *
     * @return The warning message.
     */
    @Override
    public String getWarning() {
        return "Move Time";
    }

    /**
     * Starts the timer for color changes in the warning panel.
     */
    public void start() {
        span.start();
    }
}
