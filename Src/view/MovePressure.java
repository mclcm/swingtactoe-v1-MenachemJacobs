package view;

import javax.swing.*;
import java.awt.*;

public class MovePressure extends WarningPanel {
    int lightInterval;
    int colorIndex = 0;

    MovePressure(int length, String passedMessage) {
        lightInterval = length;

        setBackground(colorList[0]);
        JLabel myMessage = new JLabel();
        myMessage.setText(passedMessage);
        add(myMessage);

        span = new Timer(3, this);
    }

    MovePressure() {
        setBackground(colorList[0]);
        JLabel myMessage = new JLabel();
        myMessage.setText(getWarning());
        add(myMessage);

        span = new Timer(3, this);
    }

    private void changeColor(){
        setBackground(colorList[colorIndex++]);
    }

    private void setColors(Color[] passedColors){colorList = passedColors;}

    @Override
    public String getWarning() {
        return "Move Time";
    }
}
