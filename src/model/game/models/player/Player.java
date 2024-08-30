package model.game.models.player;

import model.game.enums.Actions;
import model.game.enums.Chips;
import model.game.models.hand.Hand;
import model.game.models.standalones.Bankroll;
import model.game.models.standalones.Bet;
import model.game.models.standalones.Card;
import model.game.utils.Utils;

import java.util.*;

import static misc.Updates.*;
import static model.game.utils.Constants.*;

public abstract class Player extends Observable{
    protected String name;
    protected Hand hand;
    protected Bankroll bankroll; // For each chip, how many does the user have.
    protected Bet bet;

    public Player(String name, int buyIn){
        this.name = name;
        this.hand = new Hand();// Empty Hand to get started.
        this.bankroll= new Bankroll(buyIn);
        this.bet = new Bet();
    }

    public List<Actions> getAvailableActions(){
        List<Actions> availableActions = new ArrayList<>();

        if (hand.softTotal() >= BLACKJACK){
            return availableActions;
        }

        // Can player double its bet ?  Has the player only 2 cards ? Is cards total value between 9 and 11 included ?
        if (canDoubleDown()){
            availableActions.add(Actions.DOUBLE_DOWN);
        }

        availableActions.add(Actions.STAND);
        availableActions.add(Actions.HIT);

        return availableActions;
    }


    public Bet getBet() {
        return bet;
    }

    /**
     * Can the player make a bet with given chip?
     * @param chip
     * @return
     */
    public boolean canBet(Chips chip) {
        return bankroll.canPay(chip.getValue());
    }

    /**
     * Can the player double down ?
     * - does he have enough bankrool to double ?
     * - are only the first 2 cards dealt ?
     * - is the card total value between 9 and 11 ?
     * @return
     */
    private boolean canDoubleDown(){
        System.out.println("Can Double down ? -----");
        System.out.println("hand size: " + (hand.size() == 2));
        System.out.println("------------------------");
        return (bankroll.canPay(bet.total() * 2)) && (hand.size() == 2) && (Utils.between(hand.softTotal(), DOUBLE_DOWN_MIN,DOUBLE_DOWN_MAX));
    }

    /**
     * Check if a player can make a hit.
     *  True only if the hand soft total is less than Blackjack.
      * @return
     */
    private boolean canHit(){
        return hand.softTotal() < BLACKJACK;
    }

    /**
     * If the Player has a Chip, add it to the Bet.
     */
    public void addToBet(Chips chip){
        if (bankroll.canPay(chip.getValue())){
            bet.add(chip); // Add the chip to the bet
            bankroll.pay(chip.getValue());

            notifyBet();
        }
    }

    public void popBet(){
        Chips removedChip = bet.pop();
        if(removedChip != null){
            bankroll.receive(removedChip.getValue());

            notifyBet();
        }
    }


    /**
     * Clear the Bet Chips and get them back on the Bankroll
     */
    public void deleteBet(){
        for (Map.Entry<Chips, Integer> entry: bet.getChips().entrySet()){
            Chips chip = entry.getKey();
            int chipCount = entry.getValue();

            bankroll.receive(chip.getValue(), chipCount);
        }

        bet.delete();

        notifyBet();
    }

    /**
     * Double the bet by paying again from bankroll and by betting the previous betted value.
     */
    public void doubleBet(){
        if (canDoubleDown()) {
            bankroll.pay(bet.total());
            bet.x2();

            notifyDoubleBet();
        }
    }

    public boolean canMakeAction(Actions action){
        if (action == Actions.HIT){
            return this.canHit();
        }

        if (action == Actions.STAND){
            return true;
        }

        if (action == Actions.DOUBLE_DOWN){
            return this.canDoubleDown();
        }

        return false;
    }

    /**
     * Get the default Player hand
     * @return
     */
    public Hand getHand(){
       return hand;
    }

    public Bankroll getBankroll() {
        return bankroll;
    }

    public void receiveCard(Card card){
        hand.add(card);

        notifyCardAdd();
    }

    @Override
    public String toString() {
        return name + "\nHAND: " + hand + "\nBET: " + bet.total() + "\nHAND SOFT: " + hand.softTotal();
    }

    /**
     * Functions to manage payments
     */

    /**
     * Player earns double the initial bet amount. This happens when his hand is bigger than the dealer's or when the dealer busts.
     */
    public void doEarn(){
        bankroll.receive(bet.total() * 2);
    }

    /**
     * Player pays the amount bet at the beginning. This happens when he busts.
     */
    public void doPay(){
        bet.delete();
    }

    /**
     * The player earns the amount he bet at the beginning. This happens when dealer's hand total is equal to the player one.
     */
    public void doPush(){
        bankroll.receive(bet.total());
    }

    /**
     * GETTERS
     */
    public String getName() {
        return name;
    }

    private void notifyBet(){
        setChanged();
        notifyObservers(BET_UPDATE);
    }
    private void notifyCardAdd(){
        setChanged();
        notifyObservers(CARD_ADD);
    }
    private void notifyDoubleBet(){
        setChanged();
        notifyObservers(BET_DOUBLE);
    }

}



