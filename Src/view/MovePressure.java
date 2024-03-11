package view;

import javax.swing.*;
import java.awt.*;

public class MovePressure extends WarningPanel {
    int lightInterval;
    int colorIndex = 0;

    MovePressure(int length) {
        universalConstructor(length);
    }

    MovePressure(int length, Color[] colorList) {
        this.colorList = colorList;
        universalConstructor(length);
    }

    private void universalConstructor(int length){
        lightInterval = length;

        setBackground(colorList[0]);
        JLabel myMessage = new JLabel();
        myMessage.setText(getWarning());
        add(myMessage);

        //span = new Timer(3, changeColor());
    }

    private void changeColor(){
        setBackground(colorList[colorIndex++]);
    }

    @Override
    public String getWarning() {
        return "Move Time";
    }
}
