package model.game.models.standalones;

import model.game.Game;
import model.game.models.hand.Hand;
import model.game.models.player.Player;

import java.util.List;
import java.util.Observable;

import static misc.Updates.CARD_ADD;
import static misc.Updates.CARD_REVEAL;

public class Dealer extends Observable {

    private static Dealer instance;

    private Sabot sabot;

    protected final String name = "Dealer";
    private Hand hand;

    private boolean cardRevealed;

    /**
     * The Dealer is the Player that manages the game.
     * He controls the Sabot and Discard Deck instances,
     * shuffles, reintegrates decks, deals cards to the players.
     */
    private Dealer() {
        this.hand = new Hand();
        this.sabot = Sabot.getInstance(6);
    }

    /**
     * Get the Singleton instance of Dealer or create a new instance;
     * @return
     */
    public static Dealer getInstance(){
        if (instance ==null){
            instance = new Dealer();
        }
        return instance;
    }

    /**
     * Directly sends the card to the player's current hand (it can either be Hand or SplitHand);
     * @param player
     */
    public void dealCard(Player player){
        Card dealtCard = sabot.dealCard();
        player.receiveCard(dealtCard);
    }

    public void dealDealerCard(boolean isHidden){
        if (isHidden) {
            cardRevealed = false;
           hand.add(sabot.dealHiddenCard());
        }else{
            hand.add(sabot.dealCard());
        }

        notifyCardUpdate();
    }

    public Hand getHand() {
        return hand;
    }

    public void resetInstance(){
        instance = null;
        sabot.resetInstance();
    }

    public void revealHiddenCard(){
        cardRevealed = true;
        hand.get(0).setHidden(false); // The hidden card is always the first dealt
        setChanged();
        notifyObservers(CARD_REVEAL);
    }

    private void notifyCardUpdate(){
        setChanged();
        notifyObservers(CARD_ADD);
    }

    public void reset(){
        this.hand = new Hand();
    }

    public boolean isCardRevealed() {
        return cardRevealed;
    }

    public Sabot getSabot() {
        return sabot;
    }
}
