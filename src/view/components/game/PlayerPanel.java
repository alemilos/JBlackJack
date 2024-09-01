package view.components.game;

import model.game.enums.Chips;
import model.game.models.hand.Hand;
import model.game.models.player.AIPlayer;
import model.game.models.player.Player;
import model.game.models.standalones.Card;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static misc.Constants.BASE_FONT;
import static misc.Constants.BOLD_FONT;
import static misc.Updates.*;

public class PlayerPanel extends JPanel implements Observer{

    private TotalChipsPanel totalChipsPanel;
    private JLayeredPane cardsLayeredPane;
    private BankrollPanel bankrollPanel;


    private int cardsNumber;
    private int lastCardX;

    private JLabel chipLabel;
    private JLabel cardsTotalValueLabel;
    private JLabel turnStateLabel;

    private ImageIcon chipPlaceholder;

    private int chipSize;
    private int cardWidth;
    private int cardHeight;

    public PlayerPanel(Player player){
        setLayout(new BorderLayout());
        setBackground(null);

        totalChipsPanel = new TotalChipsPanel();

        JPanel chipContainer= new JPanel();
        chipContainer.setBackground(null);

        bankrollPanel = new BankrollPanel();
        bankrollPanel.updateBankroll(player.getBankroll().getChipsLeft()); // init with current bankroll

        // Based on Player Type the UI will be bigger or smaller (AI Players)
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

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(null);

        JLabel nameLabel =  new JLabel(player.getName());
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);
        nameLabel.setFont(BOLD_FONT);
        nameLabel.setForeground(Color.white);

        cardsLayeredPane = new JLayeredPane();
        cardsLayeredPane.setOpaque(true);
        cardsLayeredPane.setPreferredSize(new Dimension(200,cardHeight));

        cardsTotalValueLabel = new JLabel();
        cardsTotalValueLabel.setFont(BASE_FONT);
        cardsTotalValueLabel.setForeground(Color.white);

        JPanel turnStatePanel = new JPanel();
        turnStatePanel.setBackground(null);
        turnStateLabel = new JLabel();
        turnStateLabel.setForeground(Color.RED);
        turnStatePanel.add(turnStateLabel);


        container.add(turnStatePanel);
        container.add(Box.createRigidArea(new Dimension(0,20)));
        container.add(cardsTotalValueLabel);
        container.add(Box.createRigidArea(new Dimension(0,20)));
        container.add(cardsLayeredPane);
        container.add(Box.createRigidArea(new Dimension(0,10)));
        container.add(totalChipsPanel);
        container.add(Box.createRigidArea(new Dimension(0,10)));
        container.add(chipContainer);
        container.add(Box.createRigidArea(new Dimension(0,10)));
        container.add(nameLabel);
        container.add(Box.createRigidArea(new Dimension(0,10)));
        container.add(bankrollPanel);

        add(container, BorderLayout.SOUTH);
    }

    private void updateTotalChipsPanel(int value){
        totalChipsPanel.updateTotalChips(value);
    }

    private void updateLastChipPanel(String chipName){
        if (chipName == null){
            chipLabel.setIcon(chipPlaceholder);
            return;
        }
        chipLabel.setIcon(new ImageIcon(new ImageIcon("./assets/icons/chips/" + chipName.toLowerCase() + ".png").getImage().getScaledInstance(chipSize,chipSize,Image.SCALE_SMOOTH)));
    }

    private void addCard(String value, String suit){
        JLabel card = new JLabel();
        card.setIcon(new ImageIcon (new ImageIcon("./assets/cards/" + suit + value + ".png").getImage().getScaledInstance(cardWidth,cardHeight, 0)));
        card.setBounds(lastCardX,0,cardWidth,cardHeight);
        lastCardX+=26;

        cardsLayeredPane.add(card, cardsNumber++);
    }

    private void updateCardsTotalValue(int value){
        cardsTotalValueLabel.setText("Tot: " + value);
    }

    /**
     * Write RED busted or BLACKJACK or put a thumb in case of normal round
     */
    private void updateHandState(Hand hand){
        turnStateLabel.setText("");
        if (hand.isBlackjack()){
            turnStateLabel.setText("BLACKJACK");
            turnStateLabel.setForeground(Color.MAGENTA);
            turnStateLabel.setFont(new Font("Arial", Font.BOLD, 22));
        }
        else if(hand.isBusted()){
            turnStateLabel.setText("BUSTED");
            turnStateLabel.setForeground(Color.RED);
            turnStateLabel.setFont(new Font("Arial", Font.BOLD, 22));
        }else {
            // Thump UP
            turnStateLabel.setIcon(new ImageIcon(new ImageIcon("./assets/icons/thumbup.png").getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH)));
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        Player player = (Player)o;

        if (arg == BET_UPDATE || arg == BET_DOUBLE){
            Chips chip = player.getBet().peek();
            updateLastChipPanel(chip != null ? chip.toString() : null);
            updateTotalChipsPanel(player.getBet().total());
        }

        if (arg == BANKROLL_UPDATE){
            bankrollPanel.updateBankroll(player.getBankroll().getChipsLeft());
        }

        if (arg == CARD_ADD){
            Card card = player.getHand().peek();
            addCard(card.lookupValue(), card.lookupSuit().toString());
            updateCardsTotalValue(player.getHand().softTotal());
        }

        if (arg == TURN_FINISH){
            System.out.println(player);
            updateHandState(player.getHand());
        }
    }
}
