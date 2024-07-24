package model.game.models.standalones;

import model.game.enums.Ranks;
import model.game.enums.Suits;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cards;

    /**
     * A Deck contains 52 cards, 13 for each Suit (Hearts, Diamonds, Clubs, Spades)
      */
    public Deck(){
        cards = new ArrayList<>();
        for (Suits suit: Suits.values()){
            for (Ranks rank: Ranks.values()){
                cards.add(new Card(rank, suit));
            }
        }
    }

    public List<Card> getCards(){
        return cards;
    }

    public int size(){
        return cards.size();
    }
}
