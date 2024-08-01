package view.pages;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;
import java.io.File;
import java.io.IOException;

public class GamePage extends JFrame {

    private JButton leaveBtn;
    private JButton musicBtn;

    private JPanel actionsContainer;
    private JPanel chipsContainer;

    public GamePage(){
        drawGameAmbient();
    }

    public JButton getLeaveBtn() {
        return leaveBtn;
    }

    public JButton getMusicBtn() {
        return musicBtn;
    }

    private void drawGameAmbient(){
        setTitle("Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        leaveBtn = new JButton();
        leaveBtn.setBounds((int)(dim.getWidth() - 100), 10, 40, 40);
        leaveBtn.setOpaque(false);
        leaveBtn.setContentAreaFilled(false);
        leaveBtn.setFocusPainted(false);
        leaveBtn.setBorderPainted(false);
        leaveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        try{
            Image img = ImageIO.read(new File("./assets/icons/leaveBtn.png"));
            leaveBtn.setIcon(new ImageIcon(img));
        }catch(IOException ioe){
            System.out.println("Image not found");
            System.exit(1);
        }


        musicBtn = new JButton();
        musicBtn.setBounds((int)(dim.getWidth() - 150), 10, 40, 40);
        musicBtn.setOpaque(false);
        musicBtn.setFocusPainted(false);
        musicBtn.setContentAreaFilled(false);
        musicBtn.setBorderPainted(false);
        musicBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        try{
            Image img = ImageIO.read(new File("./assets/icons/musicBtn.png"));
            musicBtn.setIcon(new ImageIcon(img));
        }catch(IOException ioe){
            System.out.println("Image not found");
            System.exit(1);
        }

        // Game Title
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


        JPanel userInterfaceContainer = new JPanel(new BorderLayout());

        actionsContainer = new JPanel(new BorderLayout());
        chipsContainer = new JPanel(new BorderLayout());

        userInterfaceContainer.add(actionsContainer, BorderLayout.NORTH);
        userInterfaceContainer.add(chipsContainer, BorderLayout.SOUTH);

        gamePanel.add(userInterfaceContainer, BorderLayout.SOUTH);
        background.add(gameTitle);
        background.add(leaveBtn);
        background.add(musicBtn);
        background.add(gamePanel);
        add(background);

        setVisible(true);
    }

    /**************
     * User Interface functions
     *************/

    public void drawUserInterface(List actions, List chips){
        drawUserActions(actions);
        drawUserChips(chips);
    }

    private void drawUserActions(List actions){
        JPanel container = new JPanel(new FlowLayout());
        container.setBackground(Color.black);

        actions.forEach( action -> {
                    JButton actionBtn = new JButton(action.toString());
                    container.add(actionBtn);
        });

        actionsContainer.add(container, BorderLayout.CENTER);
    }

    private void drawUserChips(List chips){
        JPanel container = new JPanel(new FlowLayout());
        container.setBackground(Color.black);

        chips.forEach(chip -> {
           JButton chipBtn = new JButton();
           chipBtn.setIcon(new ImageIcon("./assets/icons/chips/" + chip.toString().toLowerCase() + ".png"));
           chipBtn.setOpaque(false);
           chipBtn.setFocusPainted(false);
           chipBtn.setContentAreaFilled(false);
           chipBtn.setBorderPainted(false);
           chipBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
           container.add(chipBtn);
        });

        chipsContainer.add(container, BorderLayout.CENTER);
    }

}
