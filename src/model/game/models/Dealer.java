package model.game.models;

import model.game.models.player.Player;

import java.util.Observable;

import static controller.enums.Updates.CARD_ADD;
import static controller.enums.Updates.CARD_REVEAL;

public class Dealer extends Observable {

    private final Sabot sabot;
    private Hand hand;
    private boolean cardRevealed;

    /**
     * The Dealer is the Player that manages the game.
     * He controls the Sabot, shuffles
     * deals cards to the players and to himself.
     */
    public Dealer() {
        this.hand = new Hand();
        this.sabot = new Sabot(6);
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
