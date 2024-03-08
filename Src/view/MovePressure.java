package view;

import java.awt.*;
import java.awt.event.ActionListener;

public class MovePressure extends WarningPanel{

    MovePressure(ActionListener al, int span){
        this.al = al;
    }

    MovePressure(ActionListener al, int span, Color[] colorList){
        this.al = al;
        this.colorList = colorList;
    }
}
