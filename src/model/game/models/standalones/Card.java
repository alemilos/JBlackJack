package model.game.models.standalones;

import model.game.enums.Ranks;
import model.game.enums.Suits;

public class Card {
    private Ranks rank;
    private Suits suit;

    private boolean isHidden;

    public Card(Ranks rank, Suits suit){
        this.rank = rank;
        this.suit = suit;
        this.isHidden = true;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    /**
     * A Card Rank lookup can be done only if the card is visible
     * @return
     */
    public Ranks lookupRank(){
        return isHidden ? rank : null;
    }

    /**
     * A Card Suit lookup can be done only if the card is visible
     * @return
     */
    public Suits lookupSuit() {
        return isHidden ? suit : null;
    }

    @Override
    public String toString() {
        return rank.toString() + " of " + suit.toString();
    }
}
