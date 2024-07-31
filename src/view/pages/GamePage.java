package view.pages;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GamePage extends JFrame {

    private JButton leaveBtn;
    private JButton musicBtn;

    public GamePage(){
        setTitle("Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        leaveBtn = new JButton("🚪");
        leaveBtn.setBounds((int)(dim.getWidth() - 100), 10, 40, 40);

        musicBtn = new JButton("🎶");
        musicBtn.setBounds((int)(dim.getWidth() - 150), 10, 40, 40);

        JLabel gameTitle = new JLabel("JBlackJack");
        gameTitle.setFont(new Font("Arial", Font.BOLD, 32));
        gameTitle.setForeground(Color.white);
        gameTitle.setBounds(60, 10, 200, 40);

        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(Color.black);
        background.setBorder(new EmptyBorder(60,60,60,60));

        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBorder(new LineBorder(Color.darkGray));
        gamePanel.setBackground(Color.black);

        background.add(gameTitle);
        background.add(leaveBtn);
        background.add(musicBtn);
        background.add(gamePanel);
        add(background);

        setVisible(true);


    }
}
