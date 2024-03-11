package view;

import javax.swing.*;
import java.awt.*;

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
        setBackground(colorList[0]);
        JLabel myMessage = new JLabel(passedMessage);
        add(myMessage);

        span = new Timer(length, this);
        //span.start();
    }

    private void iterateColor() {
        setBackground(colorList[colorIndex++]);
    }

    private void setColors(Color[] passedColors) {
        colorList = passedColors;
    }

    @Override
    public String getWarning() {
        return "Move Time";
    }
}
