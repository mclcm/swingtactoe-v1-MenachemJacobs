package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MovePressure extends WarningPanel {
    private int colorIndex = 0;
    private static final int DEFAULT_LENGTH = 3;

    MovePressure() {
        initialize(DEFAULT_LENGTH, getWarning());
    }

    MovePressure(int length, String passedMessage) {
        initialize(length, passedMessage);
    }

    private void initialize(int length, String passedMessage) {
        setBackground(colorList[colorIndex++]);
        JLabel myMessage = new JLabel(passedMessage);
        add(myMessage);

        //convert input into seconds
        span = new Timer(length * 1000, this);
    }

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
        if(colorIndex < colorList.length)
            setBackground(colorList[colorIndex++]);
        else
            span.stop();
    }

    public void restate(){
        colorIndex = 0;
        setBackground(colorList[colorIndex++]);
        span.restart();
    }

    @Override
    public String getWarning() {
        return "Move Time";
    }

    public void start() {
        span.start();
    }
}
