package view;

import javax.swing.*;
import java.awt.*;

public class AuthPage extends JFrame {

    private JTextField usernameInput;
    private JButton submitBtn;


    public AuthPage() {
        setTitle("JBlackJack");
        setSize(300, 150);
        JPanel panel = new JPanel(new GridLayout(3,2));

        getContentPane().add(panel, BorderLayout.CENTER);

        panel.add(new JLabel("Username"));
        usernameInput = new JTextField();
        panel.add(usernameInput);

        submitBtn = new JButton("Conferma");
        panel.add(submitBtn);

        setVisible(true);
    }

    public JButton getSubmitBtn(){
        return this.submitBtn;
    }

    public JTextField getUsernameInput(){
        return this.usernameInput;
    }
}
