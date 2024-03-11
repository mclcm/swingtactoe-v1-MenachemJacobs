package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public abstract String getWarning();
}
