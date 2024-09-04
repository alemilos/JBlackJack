package model.game.models;

import model.game.enums.Ranks;
import java.util.ArrayList;
import java.util.List;

import static model.game.utils.Constants.BLACKJACK;

public class Hand{
    protected List<Card> cards;

    public Hand(){
        this.cards = new ArrayList<>();
    }

    /**
     * Returns the hand's card number
     * @return
     */
    public int size(){
        return cards.size();
    }

    /**
     * Add a card to the hand.
     * @param card
     */
    public void add(Card card){
        cards.add(card);
    }

    /**
     * Get a card from the hand.
      * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    public Card get(int index) throws IndexOutOfBoundsException{
        try{
            return cards.get(index);
        }catch (IndexOutOfBoundsException err){
            System.out.println(err);
            throw new IndexOutOfBoundsException(err.getMessage());
        }
    }

    /** Peek the last added card to the hand*/
    public Card peek(){
        if (!cards.isEmpty()){
            return cards.get(cards.size()-1);
        }
        return null;
    }



    /**
     * Count the hand total, adding the soft value if an Ace is present and doesn't exceed the BLACKJACK 21
     * @return
     */
    public int softTotal(){
        int total = 0;
        boolean softAceAdded= false;
        int aceInHand = 0;

        for (Card card: cards){
            if(card.lookupRank() != null) {
                if (card.lookupRank() == Ranks.ACE) {
                    aceInHand += 1;
                } else {
                    total += card.lookupRank().getValue();
                }
            }
        }

        // Add the ACE making sure that only one soft is counted and only if counting
        // it as soft doesn't make the hand total exceed the BLACKJACK 21!
        for (int i = 0; i < aceInHand; i++){
            if (total + Ranks.ACE.getSoftValue() <= BLACKJACK && !softAceAdded){
                total += Ranks.ACE.getSoftValue();
            }else{
                total += Ranks.ACE.getValue();
            }
        }

        return total;
    }

    public boolean isBusted(){
        return softTotal() > BLACKJACK;
    }

    public boolean isBlackjack(){
        return softTotal() == BLACKJACK;
    }

    public List<Card> getCards() {
        return cards;
    }
}
