package view.components.game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static misc.Constants.TABLE_COLOR;

public class TablePanel extends JPanel {

    private DealerPanel dealerPanel;
    private UserInterfacePanel userInterfacePanel;
    private JPanel usersPanel;

    public TablePanel(){
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.darkGray));
        setBackground(TABLE_COLOR);

        dealerPanel = new DealerPanel();

        userInterfacePanel = new UserInterfacePanel();

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



    public JPanel getUsersPanel() {
        return usersPanel;
    }
}
