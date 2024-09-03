package view.pages;

import misc.Constants;
import view.components.game.ActionButton;
import view.components.game.NotificationsPanel;
import view.components.game.TablePanel;
import view.components.game.PlayerPanel;
import view.ui.IconButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

import static misc.Constants.BG_COLOR;
import static misc.Constants.PRIMARY_COLOR;

public class GamePage extends JFrame {
    private JButton leaveBtn;
    private JButton musicBtn;

    private NotificationsPanel notificationsPanel;

    private TablePanel tablePanel;


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

        // Leave Button
        leaveBtn = new JButton();
        leaveBtn.setOpaque(false);
        leaveBtn.setContentAreaFilled(false);
        leaveBtn.setFocusPainted(false);
        leaveBtn.setBorderPainted(false);
        leaveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        try{
            Image img = ImageIO.read(new File("./assets/icons/leaveBtn.png"));
            leaveBtn.setIcon(new ImageIcon(img));
        }catch(IOException ioe){
            System.err.println("Image not found");
            System.exit(1);
        }

        // Music Button
        musicBtn = new JButton();
        musicBtn.setOpaque(false);
        musicBtn.setFocusPainted(false);
        musicBtn.setContentAreaFilled(false);
        musicBtn.setBorderPainted(false);
        musicBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        try{
            Image img = ImageIO.read(new File("./assets/icons/musicBtn.png"));
            musicBtn.setIcon(new ImageIcon(img));
        }catch(IOException ioe){
            System.err.println("Image not found");
            System.exit(1);
        }

        // Game Title
        JLabel gameTitle = new JLabel("JBlackJack");
        gameTitle.setFont(new Font("Arial", Font.BOLD, 32));
        gameTitle.setForeground(Color.white);
        gameTitle.setBackground(null);

        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(BG_COLOR);

        tablePanel= new TablePanel();

        notificationsPanel= new NotificationsPanel();

        JPanel buttonsContainer = new JPanel(new FlowLayout());
        buttonsContainer.setBackground(null);
        buttonsContainer.add(musicBtn);
        buttonsContainer.add(leaveBtn);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(null);
        topPanel.add(gameTitle, BorderLayout.WEST);
        topPanel.add(notificationsPanel, BorderLayout.CENTER);
        topPanel.add(buttonsContainer, BorderLayout.EAST);

        background.add(topPanel, BorderLayout.NORTH);
        background.add(tablePanel, BorderLayout.CENTER);
        add(background);

        setVisible(true);
    }


    /**
     * Draw the sabot, the users.
     */
    public void drawInitialGameState(List<PlayerPanel> playerPanels) {
        JLabel sabot = new JLabel();
        sabot.setIcon(new ImageIcon(new ImageIcon("./assets/cards/deckblack1.png").getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH)));
        tablePanel.getDealerPanel().add(sabot, BorderLayout.EAST);

        playerPanels.forEach(this::drawPlayerPanel);
    }

    public void drawPlayerPanel(PlayerPanel playerPanel){
        tablePanel.getUsersPanel().add(playerPanel);
    }

    public TablePanel getTablePanel() {
        return tablePanel;
    }

    public NotificationsPanel getNotificationsPanel() {
        return notificationsPanel;
    }

}
