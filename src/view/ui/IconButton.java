package view.ui;

import javax.swing.*;
import java.awt.*;

public class IconButton extends JButton {
    public IconButton(ImageIcon icon){
        super();
        setIcon(icon);
        setOpaque(false);
        setFocusPainted(false);
        setBackground(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

    }
}
