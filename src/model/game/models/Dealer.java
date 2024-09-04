package model.game.models;

import model.game.models.player.Player;

import java.util.Observable;

import static misc.Updates.CARD_ADD;
import static misc.Updates.CARD_REVEAL;

public class Dealer extends Observable {

    private static Dealer instance;
    private final Sabot sabot;
    private Hand hand;
    private boolean cardRevealed;

    /**
     * The Dealer is the Player that manages the game.
     * He controls the Sabot, shuffles
     * deals cards to the players and to himself.
     */
    private Dealer() {
        this.hand = new Hand();
        this.sabot = Sabot.getInstance(6);
    }

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

    /**
     * Dealer deals a card to himself.
     * @param isHidden
     */
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

    /**
     * Reveal the first card and notify observers of a card reveal
     */
    public void revealHiddenCard(){
        cardRevealed = true;
        hand.get(0).setHidden(false); // The hidden card is always the first dealt
        setChanged();
        notifyObservers(CARD_REVEAL);
    }

    /**
     * Notify observers of a card add
     */
    private void notifyCardUpdate(){
        setChanged();
        notifyObservers(CARD_ADD);
    }

    public void clearHand(){
        this.hand = new Hand();
    }

    public boolean isCardRevealed() {
        return cardRevealed;
    }

    public Sabot getSabot() {
        return sabot;
    }
}
