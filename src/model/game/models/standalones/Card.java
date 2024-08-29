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
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    /**
     * A Card Rank lookup can be done only if the card is visible
     * @return
     */
    public Ranks lookupRank(){
        return !isHidden ? rank : null;
    }

    /** The value can be 1,...,10,J,Q,K */
    public String lookupValue(){
        if (!isHidden){
            if (rank.getValue() < 10) return ""+rank.getValue();

            char rankFirstLetter =  rank.toString().toUpperCase().charAt(0);

            if (rankFirstLetter == 'T') return 10+"";

            return rankFirstLetter+"";
        }

        return null;
    }

    /**
     * A Card Suit lookup can be done only if the card is visible
     * @return
     */
    public Suits lookupSuit() {
        return !isHidden ? suit : null;
    }

    @Override
    public String toString() {
        return rank.toString() + " of " + suit.toString() + " hidden: " + isHidden;
    }
}
