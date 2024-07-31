package view.pages;

import view.ui.BackgroundPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AuthPage extends JFrame {

    private JTextField usernameInput;
    private JButton submitBtn;

    public AuthPage() {
        setTitle("Authentication");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        Dimension screenSize= Toolkit.getDefaultToolkit().getScreenSize();

        try {
            BufferedImage bi = ImageIO.read(new File("./assets/backgrounds/auth.png"));

            setContentPane(new BackgroundPanel(bi, screenSize.getWidth()));
        }catch(IOException ioe){
            System.out.println("Background Not Found");
            System.exit(1);
        }

        // Game Title
        JLabel gameTitle = new JLabel("JBlackJack");
        gameTitle.setForeground(Color.white);
        gameTitle.setFont(new Font("Arial", Font.BOLD, 86));
        gameTitle.setBounds(140, 100, 600, 100);

        // Username Form Container
        JPanel container = new JPanel();
        container.setBounds(80, 240, 400,300);
        container.setLayout(new GridBagLayout());
        container.setBackground(Color.BLACK);

        // Flex Container
        JPanel flexContainer = new JPanel();
        flexContainer.setLayout(new GridLayout(5,1));
        flexContainer.setBackground(Color.BLACK);
        flexContainer.setPreferredSize(new Dimension(200, 200));

        // Username Label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.white);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 22));

        // Username Input Field
        usernameInput = new JTextField();
        usernameInput.setForeground(Color.WHITE);
        usernameInput.setBorder(BorderFactory.createLineBorder(Color.gray));
        usernameInput.setBackground(null);

        // Username Submit Button
        submitBtn = new JButton("Conferma");
        submitBtn.setBackground(Color.GRAY);
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setOpaque(true);
        submitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        submitBtn.setBorderPainted(false);
        submitBtn.setFocusPainted(false);
        submitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        flexContainer.add(usernameLabel);
        flexContainer.add(usernameInput);
        flexContainer.add(Box.createRigidArea(new Dimension(0, 10))); // Gap
        flexContainer.add(submitBtn);

        add(gameTitle);
        container.add(flexContainer);

        add(container);

        setVisible(true);
    }

    public JButton getSubmitBtn(){
        return this.submitBtn;
    }

    public JTextField getUsernameInput(){
        return this.usernameInput;
    }
}
