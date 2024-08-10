package view.components.game;

import model.game.models.player.AIPlayer;
import model.game.models.player.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static misc.Constants.BASE_FONT;
import static misc.Constants.BOLD_FONT;

public class PlayerPanel extends JPanel{

    private JPanel totalChipsPanel;
    private JLayeredPane cardsLayeredPane;

    private int cardsNumber;
    private int lastCardX;

    private JLabel chipLabel;
    private JLabel cardsTotalValueLabel;

    private ImageIcon chipPlaceholder;

    private int chipSize;
    private int cardWidth;
    private int cardHeight;

    public PlayerPanel(Player player){
        setLayout(new BorderLayout());
        setBackground(null);

        totalChipsPanel = new JPanel();
        JPanel chipContainer= new JPanel();
        chipContainer.setBackground(null);

        if (player instanceof AIPlayer) {
            chipSize= 50;
            cardHeight = 80;
            cardWidth = 60;
        }
        else {
            chipSize= 80;
            cardHeight = 120;
            cardWidth = 90;
        }

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

        JLabel nameLabel =  new JLabel(player.getName());
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);
        nameLabel.setFont(BOLD_FONT);
        nameLabel.setForeground(Color.white);

        cardsLayeredPane = new JLayeredPane();
        cardsLayeredPane.setOpaque(true);
        cardsLayeredPane.setPreferredSize(new Dimension(140,cardHeight));

        cardsTotalValueLabel = new JLabel();
        cardsTotalValueLabel.setFont(BASE_FONT);
        cardsTotalValueLabel.setForeground(Color.white);

        container.add(cardsTotalValueLabel);
        container.add(Box.createRigidArea(new Dimension(0,20)));
        container.add(cardsLayeredPane);
        container.add(Box.createRigidArea(new Dimension(0,10)));
        container.add(totalChipsPanel);
        container.add(Box.createRigidArea(new Dimension(0,10)));
        container.add(chipContainer);
        container.add(Box.createRigidArea(new Dimension(0,10)));
        container.add(nameLabel);

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

    public void addCard(String value, String suit){
        JLabel card = new JLabel();
        card.setIcon(new ImageIcon (new ImageIcon("./assets/cards/" + suit + value + ".png").getImage().getScaledInstance(cardWidth,cardHeight, 0)));
        card.setBounds(lastCardX,0,cardWidth,cardHeight);
        lastCardX+=36;

        cardsLayeredPane.add(card, cardsNumber++);
    }

    public void updateCardsTotalValue(int value){
        cardsTotalValueLabel.setText("Tot: " + value);
    }

    public void clearCards(){
        cardsLayeredPane.removeAll();
    }

}
