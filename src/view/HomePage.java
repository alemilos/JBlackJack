package view;

import view.components.ProfileButton;

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


        JPanel container = new JPanel(new BorderLayout());

        JPanel northContainer = new JPanel();
        JPanel southContainer = new JPanel();

        JLabel gameTitle = new JLabel("JBlackJack");
        JButton playBtn = new JButton("Gioca");


        container.add(northContainer, BorderLayout.NORTH);
        container.add(southContainer, BorderLayout.SOUTH);
        add(container);
    }
}
