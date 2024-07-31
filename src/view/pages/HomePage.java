package view.pages;

import view.components.EloBox;
import view.components.ProfileButton;
import view.ui.BackgroundPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomePage extends JFrame{

    private JButton playBtn;
    private ProfileButton profileBtn;

    public HomePage(String username, String elo, int balance){
        setTitle("Home");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Game Title
        JLabel gameTitle = new JLabel("JBlackJack");
        gameTitle.setForeground(Color.white);
        gameTitle.setFont(new Font("Arial", Font.BOLD, 48));
        gameTitle.setBounds(20, 0, 300, 100);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.decode("#120F0F"));

        JPanel northContainer = new JPanel(new BorderLayout());
        northContainer.setBackground(null);

        JPanel southContainer = new JPanel(new BorderLayout());
        southContainer.setBackground(null);

        JPanel southInnerContainer = new JPanel();
        southInnerContainer.setBackground(null);

        playBtn = new JButton("Gioca");
        //playBtn.setPreferredSize(new Dimension(400, 150));
        playBtn.setFont(new Font("Arial", Font.BOLD, 72));
        playBtn.setBackground(Color.decode("#37322C"));
        playBtn.setForeground(Color.white);
        playBtn.setOpaque(true);
        playBtn.setBorderPainted(false);
        playBtn.setFocusPainted(false);
        playBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        playBtn.setBounds((int)((dim.getWidth()-400) / 2),(int)(dim.getHeight() - 150 - 80), 400,150);

        EloBox elobox = new EloBox(new ImageIcon("./assets/levels/" + elo + ".png"),elo);

        profileBtn = new ProfileButton(new ImageIcon("./assets/avatars/defaultavatar.png"), username);

        container.add(northContainer, BorderLayout.NORTH);
        container.add(southContainer, BorderLayout.SOUTH);

        northContainer.add(profileBtn, BorderLayout.EAST);

        southContainer.add(elobox, BorderLayout.WEST);

        container.setVisible(true);

        add(gameTitle);
        add(playBtn);
        add(container);
        setVisible(true);
    }

    public JButton getPlayBtn() {
        return playBtn;
    }

    public ProfileButton getProfileButton() {
        return profileBtn;
    }
}
