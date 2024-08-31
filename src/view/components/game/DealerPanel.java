package view.components.game;

import misc.Constants;
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
    private JLabel totalText;
    private JLabel cardTotalLbl;

    public DealerPanel(){
       setLayout(new BorderLayout());
       setBackground(null);

       JPanel cardTotalPanel = new JPanel(new FlowLayout());
       cardTotalPanel.setBackground(null);

       totalText= new JLabel("Tot: ");
       totalText.setFont(Constants.BASE_FONT);
       totalText.setForeground(Color.white);
       totalText.setVisible(false);

       cardTotalLbl = new JLabel(0+"");
       cardTotalLbl.setFont(Constants.BASE_FONT);
       cardTotalLbl.setForeground(Color.white);
       cardTotalLbl.setVisible(false);
       cardTotalPanel.add(totalText);
       cardTotalPanel.add(cardTotalLbl);

       dealerCardsPanel = new JPanel(new FlowLayout());
       dealerCardsPanel.setBackground(null);
       add(dealerCardsPanel, BorderLayout.CENTER);
       add(cardTotalPanel, BorderLayout.SOUTH);
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

    private void updateCardTotal(int value){
        cardTotalLbl.setText(value+"");
        cardTotalLbl.setVisible(true);
        totalText.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == CARD_ADD){
            Card card = Dealer.getInstance().getHand().peek(); // View the last added card
            addDealerCard(card.lookupValue(), card.lookupSuit() != null ? card.lookupSuit().name() : null);
            updateCardTotal(Dealer.getInstance().getHand().softTotal());
        }

        // Redraw the Dealer Hand
        if (arg == CARD_REVEAL){
            dealerCardsPanel.removeAll();
            Hand hand = Dealer.getInstance().getHand();

            hand.getCards().forEach(card -> {
                        addDealerCard(card.lookupValue(), card.lookupSuit().toString());
            });
            updateCardTotal(Dealer.getInstance().getHand().softTotal());
        }
    }
}
