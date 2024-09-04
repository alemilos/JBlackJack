package model.game.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sabot {
    private static Sabot instance;
    private List<Card> cards;
    private final int CUT_CARD;
    private final int k;

    /**
     * A sabot is a big deck of cards, it usually contains k * decks (52 cards decks).
     * Bigger is the k, harder is the game of JBlackJack.
     * The k * decks are shuffled and merged all together.
     * The JBlackJack rule is to reintegrate the Sabot with the discarded cards, when 75% of it has been dealt.
     * @param k the number of decks
     */
    private Sabot(int k){
        this.k = k;
        cards = new ArrayList<>();

        // Create k decks and merge them together to form k * 52 cards ArrayList.
        for (int i = 0; i<k; i++){
            Deck deck = new Deck();
            cards.addAll(deck.getCards());
        }
        shuffle();

        CUT_CARD = k*52 / 2;
    }

    public static Sabot getInstance(int k){
        if (instance == null) {
            instance = new Sabot(k);
        }
        return instance;
    }

    /**
     * Shuffle the deck
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }

    /**
     * Recreate a sabot and shuffle it.
     */
    public void reshuffle(){
        cards = new ArrayList<>();
        // Create k decks and merge them together to form k * 52 cards ArrayList.
        for (int i = 0; i < k; i++){
            Deck deck = new Deck();
            cards.addAll(deck.getCards());
        }
        shuffle();
    }

    /**
     * Sabot must be reshuffled when CUT_CARD is reached.
     * @return
     */
    public boolean mustReshuffle(){
        return size() < CUT_CARD;
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
