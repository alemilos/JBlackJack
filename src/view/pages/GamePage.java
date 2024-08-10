package view.pages;

import misc.Constants;
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

import static misc.Constants.PRIMARY_COLOR;

public class GamePage extends JFrame {

    private JButton leaveBtn;
    private JButton musicBtn;

    private JPanel actionsContainer;
    private JPanel chipsContainer;
    private NotificationsPanel notificationsPanel;

    private TablePanel tablePanel;

    private List<JButton> actionButtons;
    private List<JButton> chipButtons;

    // Actions on chips btns
    private JButton undoBtn;
    private JButton deleteBetBtn;

    public GamePage(){
        drawGameAmbient();
        actionButtons = new ArrayList<>();
        chipButtons = new ArrayList<>();
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
            System.out.println("Image not found");
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
            System.out.println("Image not found");
            System.exit(1);
        }

        // Game Title
        JLabel gameTitle = new JLabel("JBlackJack");
        gameTitle.setFont(new Font("Arial", Font.BOLD, 32));
        gameTitle.setForeground(Color.white);
        gameTitle.setBackground(null);

        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(PRIMARY_COLOR);
        background.setBorder(new EmptyBorder(10,60,60,60));

        tablePanel= new TablePanel();

        actionsContainer = new JPanel(new BorderLayout());
        chipsContainer = new JPanel(new BorderLayout());

        tablePanel.getUserInterfacePanel().add(actionsContainer, BorderLayout.NORTH);
        tablePanel.getUserInterfacePanel().add(chipsContainer, BorderLayout.SOUTH);

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

    /**************
     * User Interface functions
     *************/

    public void drawUserInterface(List actions, List chips){
        drawUserActions(actions);
        drawUserChips(chips);
    }

    private void drawUserActions(List<String> actions){
        JPanel container = new JPanel(new FlowLayout());
        container.setBackground(Color.black);

        actions.forEach( action -> {
                    IconButton actionBtn = new IconButton(new ImageIcon("./assets/buttons/actions/"+action.toLowerCase() + ".png"));
                    actionBtn.setEnabled(false); // Disabled by default

                    actionButtons.add(actionBtn);
                    container.add(actionBtn);
        });

        actionsContainer.add(container, BorderLayout.CENTER);
    }

    private void drawUserChips(List chips){
        JPanel container = new JPanel(new FlowLayout());
        container.setBackground(Color.black);

        chips.forEach(chip -> {
           IconButton chipBtn = new IconButton(new ImageIcon("./assets/icons/chips/" + chip.toString().toLowerCase() + ".png"));
           chipBtn.setEnabled(false); // default disabled

           chipButtons.add(chipBtn);
           container.add(chipBtn);
        });

        // Draw Undo Button
        undoBtn = new JButton();
        undoBtn.setIcon(new ImageIcon(new ImageIcon("./assets/icons/undo.png").getImage().getScaledInstance(24,24,Image.SCALE_SMOOTH)));

        deleteBetBtn = new JButton();
        deleteBetBtn.setIcon(new ImageIcon(new ImageIcon("./assets/icons/bin.png").getImage().getScaledInstance(24,24,Image.SCALE_SMOOTH)));

        container.add(undoBtn);
        container.add(deleteBetBtn);

        chipsContainer.add(container, BorderLayout.CENTER);
    }

    /**
     * Draw the sabot, the users.
     */
    public void drawInitialGameState(List<PlayerPanel> playerPanels) {
        JLabel sabot = new JLabel();
        sabot.setIcon(new ImageIcon(new ImageIcon("./assets/cards/deckblack1.png").getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH)));
        tablePanel.getDealerPanel().add(sabot, BorderLayout.EAST);

        playerPanels.forEach(playerPanel -> drawPlayerPanel(playerPanel));
    }

    public void drawPlayerPanel(PlayerPanel playerPanel){
        tablePanel.getUsersPanel().add(playerPanel);
    }


    /**
     *
     */
    public void disableBetButtons(){
        chipButtons.forEach(chipBtn -> chipBtn.setEnabled(false));
        deleteBetBtn.setEnabled(false);
        undoBtn.setEnabled(false);
    }

    /**
     *
     */
    public void enableBetButtons(){
        chipButtons.forEach(chipBtn -> chipBtn.setEnabled(true));
        deleteBetBtn.setEnabled(true);
        undoBtn.setEnabled(true);
    }


    public List<JButton> getChipButtons() {
        return chipButtons;
    }

    public List<JButton> getActionButtons() {
        return actionButtons;
    }

    public TablePanel getTablePanel() {
        return tablePanel;
    }

    public JButton getUndoBtn() {
        return undoBtn;
    }

    public JButton getDeleteBetBtn() {
        return deleteBetBtn;
    }

    public NotificationsPanel getNotificationsPanel() {
        return notificationsPanel;
    }

}
