package model.game.models.standalones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sabot {

    private static Sabot instance;

    private List<Card> cards;

    /**
     * A sabot is a big deck of cards, it usually contains k * decks (52 cards decks).
     * Bigger is the k, harder is the game of BlackJack.
     * The k * decks are shuffled and merged all together.
     * The JBlackJack rule is to reintegrate the Sabot with the discarded cards, when 75% of it has been dealt.
     * @param k the number of decks
     */
    private Sabot(int k){
        cards = new ArrayList<>();

        // Create k decks and merge them together to form k * 52 cards ArrayList.
        for (int i = 0; i<k; i++){
            Deck deck = new Deck();
            cards.addAll(deck.getCards());
        }
        shuffle();

    }

    public static Sabot getInstance(int k){
        if (instance == null) {
            instance = new Sabot(k);
        }
        return instance;
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }


    /**
     * Extract the last card from the Sabot and make it visible.
     * @return
     */
    public Card dealCard(){
       Card card = cards.remove(cards.size()-1);
        return card;
    }

    /**
     * Deal the last card from the Sabot, but don't make it visible.
     * @return
     */
    public Card dealHiddenCard(){
        Card hiddenCard = cards.remove(cards.size()-1); // Card is Hidden by Default
        hiddenCard.setHidden(true);
        return hiddenCard;
    }

    public int size(){
        return cards.size();
    }

    public void resetInstance(){
        instance = null;
    }
}
