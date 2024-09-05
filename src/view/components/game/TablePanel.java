package view.components.game;

import controller.Controller;
import controller.GameController;
import model.game.models.Game;
import view.ui.JustifyBetweenFlowLayout;

import javax.swing.*;
import java.awt.*;

import static misc.Constants.TABLE_COLOR;

public class TablePanel extends JPanel {

    private DealerPanel dealerPanel;
    private UserInterfacePanel userInterfacePanel;
    private JPanel usersPanel;

    public TablePanel(Game game){
        setLayout(new BorderLayout());
        setBackground(TABLE_COLOR);

        dealerPanel = new DealerPanel(game.getDealer());
        userInterfacePanel = new UserInterfacePanel(game.getHumanPlayer());

        usersPanel= new JPanel(new JustifyBetweenFlowLayout());
        usersPanel.setBackground(null);

        add(dealerPanel, BorderLayout.NORTH);
        add(usersPanel, BorderLayout.CENTER);
        add(userInterfacePanel, BorderLayout.SOUTH);
    }

    public DealerPanel getDealerPanel() {
        return dealerPanel;
    }

    public UserInterfacePanel getUserInterfacePanel() {
        return userInterfacePanel;
    }

    public void clearForLoss() {
        removeAll();

        JLabel defeatLabel = new JLabel();
        defeatLabel.setIcon(new ImageIcon(new ImageIcon("./assets/ui/defeat.png").getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH)));
        defeatLabel.setHorizontalAlignment(JLabel.CENTER);
        defeatLabel.setVerticalAlignment(JLabel.CENTER);

        add(defeatLabel);

        repaint();
        revalidate();
    }

    public JPanel getUsersPanel() {
        return usersPanel;
    }
}
