package view.components;

import view.ui.BackgroundPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProfileButton extends JButton{

    public ProfileButton(ImageIcon icon, String username){
        setLayout(new BorderLayout());
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(20,0,0,20),BorderFactory.createLineBorder(Color.decode("#AAAAAA"), 1, true)), BorderFactory.createEmptyBorder(5,10,5,10)));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel iconHolder = new JLabel();
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH));
        iconHolder.setIcon(scaledIcon);

        JLabel usernameLbl = new JLabel(username);
        usernameLbl.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLbl.setForeground(Color.WHITE);

        add(iconHolder, BorderLayout.WEST);
        add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.CENTER);
        add(usernameLbl, BorderLayout.EAST);

        setVisible(true);

    }
}
