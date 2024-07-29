package view;

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
    private ProfileButton profileButton;

    public HomePage(){
        setTitle("Home");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setVisible(true);

        // Background Screen Fit
       // try{
       //     double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
       //     BufferedImage bi = ImageIO.read(new File("./assets/backgrounds/home.png"));

       //     setContentPane(new BackgroundPanel(bi, width));
       // }catch(IOException ioe){
       //     System.out.println("Background Not Found");
       //     System.exit(1);
       // }


        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.pink);

        JPanel northContainer = new JPanel();
        northContainer.setBackground(Color.green);

        JPanel southContainer = new JPanel();
        southContainer.setBackground(Color.yellow);

        playBtn = new JButton("Gioca");

        JLabel level = new JLabel("The Broke");


        southContainer.add(level, BorderLayout.WEST);
        southContainer.add(playBtn, BorderLayout.CENTER);

        container.add(northContainer, BorderLayout.NORTH);
        container.add(southContainer, BorderLayout.SOUTH);
        add(container);
        setVisible(true);
    }
}
