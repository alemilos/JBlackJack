package view.components.game;

import model.game.models.player.AIPlayer;
import model.game.models.player.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel{

    private JPanel totalChipsPanel;

    private JLabel chipLabel;

    private ImageIcon chipPlaceholder;

    private int chipSize;

    public PlayerPanel(Player player){
        setLayout(new BorderLayout());
        setBackground(null);
        totalChipsPanel = new JPanel();
        JPanel chipContainer= new JPanel();
        chipContainer.setBackground(null);

        if (player instanceof AIPlayer) chipSize= 50;
        else chipSize= 80;

        chipPlaceholder = new ImageIcon(new ImageIcon("./assets/icons/chips/chip-placeholder.png").getImage().getScaledInstance(chipSize,chipSize, Image.SCALE_SMOOTH));

        chipLabel = new JLabel();
        chipLabel.setIcon(chipPlaceholder);

        chipContainer.add(chipLabel);

        JLabel totalChipsLbl = new JLabel();
        totalChipsLbl.setText(0+"");
        totalChipsPanel.add(totalChipsLbl);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(null);

        container.add(totalChipsPanel);
        container.add(Box.createRigidArea(new Dimension(0,5)));
        container.add(chipContainer);
        container.add(Box.createRigidArea(new Dimension(0,5)));
        container.add(new JLabel(player.getName()));

        add(container, BorderLayout.SOUTH);
    }

    public void updateTotalChipsPanel(int value){
        JLabel totalLabel = (JLabel)totalChipsPanel.getComponent(0);
        totalLabel.setText(value+"");
    }

    public void updateLastChipPanel(String chipName){
        if (chipName == null){
            chipLabel.setIcon(chipPlaceholder);
            return;
        }
        chipLabel.setIcon(new ImageIcon(new ImageIcon("./assets/icons/chips/" + chipName + ".png").getImage().getScaledInstance(chipSize,chipSize,Image.SCALE_SMOOTH)));
    }

}
