package view.components.game;

import model.game.Game;
import model.game.models.hand.Hand;
import model.game.models.standalones.Card;
import model.game.models.standalones.Dealer;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static misc.Updates.CARD_ADD;
import static misc.Updates.CARD_REVEAL;

public class DealerPanel extends JPanel implements Observer {

    private JPanel dealerCardsPanel;

    public DealerPanel(){
       setLayout(new BorderLayout());
       setBackground(null);

       dealerCardsPanel = new JPanel(new FlowLayout());
       dealerCardsPanel.setBackground(null);
       add(dealerCardsPanel, BorderLayout.CENTER);
    }

    private void addDealerCard(String value, String suit){
        JLabel card = new JLabel();

        if(value == null || suit == null) {
            // Draw hidden card
            card.setIcon(new ImageIcon(new ImageIcon("./assets/cards/backblack1.png").getImage().getScaledInstance(100,120,0)));
        }else{
            card.setIcon(new ImageIcon(new ImageIcon("./assets/cards/" + suit.toLowerCase() + value + ".png").getImage().getScaledInstance(100,120,0)));
        }

        dealerCardsPanel.add(card);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == CARD_ADD){
            System.out.println("Catch CARD_ADD!");
            Card card = Dealer.getInstance().getHand().peek(); // View the last added card
            addDealerCard(card.lookupValue(), card.lookupSuit() != null ? card.lookupSuit().name() : null);
        }

        // Redraw the Dealer Hand
        if (arg == CARD_REVEAL){
            System.out.println("Catch CARD_REVEAL");
            dealerCardsPanel.removeAll();

            Hand hand = Dealer.getInstance().getHand();

            hand.getCards().forEach(card -> {
                        addDealerCard(card.lookupValue(), card.lookupSuit().toString());
            });
        }
    }
}
