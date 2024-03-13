package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An abstract class representing a warning panel.
 *
 * This class extends JPanel and implements the ActionListener interface. It serves as a base
 * for creating warning panels in GUI applications.
 */
public abstract class WarningPanel extends JPanel implements ActionListener {
    Timer span;
    Color[] colorList = new Color[]{Color.green, Color.yellow, Color.red};

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public abstract void actionPerformed(ActionEvent e);

    /**
     * Retrieves the warning message to be displayed on the panel.
     *
     * @return The warning message to be displayed.
     */
    public abstract String getWarning();
}
