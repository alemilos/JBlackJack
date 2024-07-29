package view.ui;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JComponent{
    private Image image;
    private double width;

    public BackgroundPanel(Image image, double width) {
        this.image = image;
        this.width = width;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image.getScaledInstance((int)width,-1, Image.SCALE_SMOOTH), 0,0, this);
    }
}
