package view;

import view.components.ProfileButton;
import view.ui.StretchIcon;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame{

    private JButton playBtn;
    private ProfileButton profileButton;

    public HomePage(){
        setTitle("Home");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel panel = new JPanel();

        // Page Background
        JLabel bgHolder = new JLabel();
        bgHolder.setIcon(new StretchIcon("./assets/backgrounds/home.png"));
        bgHolder.setBounds(0, 0, getContentPane().getWidth(), getContentPane().getHeight());

        JPanel container = new JPanel(new BorderLayout());

        JPanel northContainer = new JPanel();
        JPanel southContainer = new JPanel();

        JLabel gameTitle = new JLabel("JBlackJack");
        JButton playBtn = new JButton("Gioca");


        container.add(northContainer, BorderLayout.NORTH);
        container.add(southContainer, BorderLayout.SOUTH);
        panel.add(bgHolder);
        add(panel);
    }
}
