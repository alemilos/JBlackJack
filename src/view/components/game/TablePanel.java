package view.components.game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static misc.Constants.TABLE_COLOR;

public class TablePanel extends JPanel {

    private JPanel dealerPanel;
    private UserInterfacePanel userInterfacePanel;
    private JPanel usersPanel;

    private JPanel dealerCardsPanel;


    public TablePanel(){
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.darkGray));
        setBackground(TABLE_COLOR);

        dealerPanel = new JPanel(new BorderLayout());
        dealerPanel.setBackground(null);

        userInterfacePanel = new UserInterfacePanel();

        usersPanel= new JPanel(new JustifyBetweenFlowLayout());
        usersPanel.setBackground(null);

        dealerCardsPanel = new JPanel(new FlowLayout());
        dealerCardsPanel.setBackground(null);
        dealerPanel.add(dealerCardsPanel, BorderLayout.CENTER);

        add(dealerPanel, BorderLayout.NORTH);
        add(usersPanel, BorderLayout.CENTER);
        add(userInterfacePanel, BorderLayout.SOUTH);
    }

    public JPanel getDealerPanel() {
        return dealerPanel;
    }

    public UserInterfacePanel getUserInterfacePanel() {
        return userInterfacePanel;
    }

    public void addDealerCard(String value, String suit){
        JLabel card = new JLabel();
        if(value == null || suit == null) {
            // Draw hidden card
            card.setIcon(new ImageIcon(new ImageIcon("./assets/cards/backblack1.png").getImage().getScaledInstance(100,120,0)));
        }else{
            card.setIcon(new ImageIcon(new ImageIcon("./assets/cards/" + suit + value + ".png").getImage().getScaledInstance(100,120,0)));
        }
        dealerCardsPanel.add(card);
    }

    public JPanel getUsersPanel() {
        return usersPanel;
    }
}
