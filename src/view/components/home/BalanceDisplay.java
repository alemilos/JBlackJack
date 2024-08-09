package view.components.home;

import javax.swing.*;
import java.awt.*;

public class BalanceDisplay extends JPanel {
    public BalanceDisplay(int balance){
        setLayout(new FlowLayout());

        JLabel moneyLbl = new JLabel();
        ImageIcon moneyIcon = new ImageIcon("./assets/icons/money.png");
        moneyLbl.setIcon(new ImageIcon(moneyIcon.getImage().getScaledInstance(26,26, Image.SCALE_SMOOTH)));


        JLabel balanceTxt = new JLabel(balance+"");
        balanceTxt.setFont(new Font("Arial", Font.BOLD, 16));
        balanceTxt.setForeground(Color.white);

        add(moneyLbl);
        add(Box.createRigidArea(new Dimension(6, 0)));
        add(balanceTxt);

        setBackground(Color.decode("#120F0F"));
        setVisible(true);
    }
}
