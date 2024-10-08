package view.components.game;

import controller.Constants;
import model.game.models.Hand;
import model.game.models.Card;
import model.game.models.Dealer;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static controller.enums.Updates.*;

/**
 * A Custom JPanel that observes changes made by the observables.
 * It manages updates regarding dealer cards and dealer hand state.
 */
public class DealerPanel extends JPanel implements Observer {

    private Dealer dealer;
    private JPanel dealerCardsPanel;
    private JLabel totalText;
    private JLabel cardTotalLbl;

    public DealerPanel(Dealer dealer){
        this.dealer = dealer;
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

    public void updateDealerHandState(Hand dealerHand){
        if (dealerHand.isBusted()){
            totalText.setText("");
            cardTotalLbl.setText("DEALER BUSTED");
            cardTotalLbl.setFont(new Font("Arial", Font.BOLD, 22));
            cardTotalLbl.setForeground(Color.RED);
        }else if (dealerHand.isBlackjack()){
            totalText.setText("");
            cardTotalLbl.setText("DEALER BLACKJACK");
            cardTotalLbl.setFont(new Font("Arial", Font.BOLD, 22));
            cardTotalLbl.setForeground(Color.MAGENTA);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == CARD_ADD){
            Card card = dealer.getHand().peek(); // View the last added card
            addDealerCard(card.lookupValue(), card.lookupSuit() != null ? card.lookupSuit().name() : null);
            updateCardTotal(dealer.getHand().softTotal());

        }

        // Redraw the Dealer Hand
        if (arg == CARD_REVEAL){
            dealerCardsPanel.removeAll();
            Hand hand = dealer.getHand();

            hand.getCards().forEach(card -> {
                        addDealerCard(card.lookupValue(), card.lookupSuit().toString());
            });

            updateCardTotal(dealer.getHand().softTotal());
        }
    }
}
