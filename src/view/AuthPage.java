package view;

import view.ui.StretchIcon;

import javax.swing.*;
import java.awt.*;

public class AuthPage extends JFrame {

    private JTextField usernameInput;
    private JButton submitBtn;

    public AuthPage() {
        setTitle("Authentication");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setVisible(true);

        JPanel panel = new JPanel();

        // Page Background
        JLabel bgHolder = new JLabel();
        bgHolder.setIcon(new StretchIcon("./assets/backgrounds/auth.png"));
        bgHolder.setBounds(0, 0, getContentPane().getWidth(), getContentPane().getHeight());

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

        container.add(flexContainer);
        bgHolder.add(gameTitle);
        bgHolder.add(container);
        panel.add(bgHolder);
        add(panel);

        setVisible(true);
    }

    public JButton getSubmitBtn(){
        return this.submitBtn;
    }

    public JTextField getUsernameInput(){
        return this.usernameInput;
    }
}
