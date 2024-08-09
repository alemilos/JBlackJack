package model.game.models.player;

import model.game.enums.Actions;
import model.game.enums.BetNotification;
import model.game.enums.Chips;
import model.game.enums.SplitType;
import model.game.managers.ActionManager;
import model.game.models.hand.Hand;
import model.game.models.hand.SplitHand;
import model.game.models.standalones.Bankroll;
import model.game.models.standalones.Bet;
import model.game.models.standalones.Card;

import java.util.*;

public abstract class Player {
    protected String name;
    protected Hand hand;
    protected SplitHand splitHand;
    protected Bankroll bankroll; // For each chip, how many does the user have.
    protected Bet bet;

    // Split Logic
    protected boolean isSplitTurn;
    private SplitType splitType;


    public Player(String name, int buyIn){
        this.name = name;
        this.hand = new Hand();// Empty Hand to get started.
        this.bankroll= new Bankroll(buyIn);
        this.bet = new Bet();
        this.splitType = SplitType.NOT_SPLIT;
    }


    /**
     * Create the splitHand
     * @param card
     */
    public void createSplitHand(Card card) {
        this.splitHand = new SplitHand();
        splitHand.receive(card);
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
     * If the Player has a Chip, add it to the Bet.
     */
    public void addToBet(Chips chip){
        if (bankroll.canPay(chip.getValue())){
            bet.add(chip); // Add the chip to the bet
            bankroll.pay(chip.getValue());
        }
    }


    public void popBet(){
        Chips removedChip = bet.pop();
        if(removedChip != null){
            bankroll.receive(removedChip.getValue());
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
    }


    /**
     * Perform an action to the current hand
     * @param action
     */
    public void makeAction(Actions action){
        ActionManager actionManager = new ActionManager(this);
        actionManager.playAction(action);
    }

    /**
     * Get the default Player hand
     * @return
     */
    public Hand getHand(){
       return hand;
    }

    /**
     * If the user is playing in the split, the active hand will be the splitHand,
     * otherwise it will be the default hand.
     * @return
     */
    public Hand getPlayingHand() {
        if (isSplitTurn){
            return splitHand;
        }
        return hand;
    }

    @Override
    public String toString() {
        return name + "\nHAND: " + hand + "\nBET: " + bet.total() + '\n';
    }

    /**
     * GETTERS
     */

    public SplitType getSplitType() {
        return splitType;
    }

    public String getName() {
        return name;
    }

    /**
     * SETTERS
     */

    public void setSplitType(SplitType splitType){
        this.splitType = splitType;
    }
}

