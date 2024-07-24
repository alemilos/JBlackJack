package model.game.models.standalones;

import java.util.ArrayList;
import java.util.List;

public class DiscardDeck {
    private List<Card> cards;

    /**
     * A Discard Deck starts with a size of 0.
     * Its purpose is to keep cards that have been played in the JBlackJack rounds.
     */
    public DiscardDeck(){
        cards = new ArrayList<>();
    }

    public void addCard(Card discardedCard){
        cards.add(discardedCard);
    }

}
