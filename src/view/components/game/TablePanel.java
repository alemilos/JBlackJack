package view.components.game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

import static misc.Constants.TABLE_COLOR;

public class TablePanel extends JPanel {

    private JPanel dealerPanel;
    private JPanel userInterfacePanel;
    private JPanel usersPanel;

    public TablePanel(){
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.darkGray));
        setBackground(TABLE_COLOR);

        dealerPanel = new JPanel(new BorderLayout());
        dealerPanel.setBackground(null);
        userInterfacePanel= new JPanel(new BorderLayout());
        usersPanel= new JPanel(new JustifyBetweenFlowLayout());
        usersPanel.setBackground(null);

        add(dealerPanel, BorderLayout.NORTH);
        add(usersPanel, BorderLayout.CENTER);
        add(userInterfacePanel, BorderLayout.SOUTH);
    }

    public JPanel getDealerPanel() {
        return dealerPanel;
    }

    public JPanel getUserInterfacePanel() {
        return userInterfacePanel;
    }

    public JPanel getUsersPanel() {
        return usersPanel;
    }
}
