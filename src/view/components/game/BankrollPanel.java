package view.components.game;

import misc.Constants;

import javax.swing.*;
import java.awt.*;

public class BankrollPanel extends JPanel {

    private JLabel bankrollLbl;

    public BankrollPanel(){
        setLayout(new FlowLayout());
        setBackground(null);

        // set money Image
        JLabel moneyLbl = new JLabel();
        ImageIcon moneyIcon = new ImageIcon("./assets/icons/money.png");
        moneyLbl.setIcon(new ImageIcon(moneyIcon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH)));


        bankrollLbl = new JLabel();
        bankrollLbl.setFont(Constants.BOLD_FONT);
        bankrollLbl.setForeground(Color.white);


        add(moneyLbl);
        add(Box.createRigidArea(new Dimension(4,0)));
        add(bankrollLbl);
    }

    public void updateBankroll(int value){
        bankrollLbl.setText(""+value);
    }
}
