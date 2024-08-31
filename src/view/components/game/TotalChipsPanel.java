package view.components.game;

import misc.Constants;

import javax.swing.*;
import java.awt.*;

public class TotalChipsPanel extends JPanel {

    private JLabel totalChipsLbl;

    public TotalChipsPanel(){
        setLayout(new FlowLayout());
        setBackground(null);

        JLabel totText = new JLabel("Bet: ");
        totText.setFont(Constants.BASE_FONT);
        totText.setForeground(Color.WHITE);


        totalChipsLbl = new JLabel();
        totalChipsLbl.setText(0+"");
        totalChipsLbl.setFont(Constants.BASE_FONT);
        totalChipsLbl.setForeground(Color.white);

        add(totText);
        add(Box.createRigidArea(new Dimension(2, 0)));
        add(totalChipsLbl);


    }

    public void updateTotalChips(int value){
        totalChipsLbl.setText("" + value);
    }
}
