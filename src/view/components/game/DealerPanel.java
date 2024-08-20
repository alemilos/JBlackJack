package view.components.game;

import model.game.Game;
import model.game.models.standalones.Card;
import model.game.models.standalones.Dealer;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static misc.Updates.CARD_ADD;

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
            Card card = Dealer.getInstance().getHand().peek(); // View the last added card
            addDealerCard(card.lookupValue(), card.lookupSuit() != null ? card.lookupSuit().name() : null);
        }
    }
}
