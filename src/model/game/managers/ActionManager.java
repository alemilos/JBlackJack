package model.game.managers;

import model.game.models.standalones.Dealer;
import model.game.models.hand.Hand;
import model.game.models.player.Player;
import model.game.utils.Utils;
import model.game.enums.Actions;
import model.game.enums.Ranks;
import model.game.enums.SplitType;

import static model.game.utils.Constants.DOUBLE_DOWN_MAX;
import static model.game.utils.Constants.DOUBLE_DOWN_MIN;

public class ActionManager {

    private Player player;
    private Dealer dealer;

    public ActionManager(Player player){
       this.player = player;
       this.dealer = Dealer.getInstance();
    }

    public void playAction(Actions action){
        switch (action){
            case HIT -> hit();
            case STAND -> stand();
            case SPLIT -> split();
            case DOUBLE_DOWN -> doubleDown();
            case INSURANCE -> insurance();
        }
    }

    /**
     * Ask for a new card.
     */
    private void hit(){
        dealer.dealCard(player);
    }

    /**
     * Finish the turn.
     */
    private void stand(){
        System.out.println("Player ends his turn!");
    }

    /**
     * If the first 2 dealt cards have the same rank, the split allows to play 2 concurrent hands.
     * If the initial dealt cards are both Aces, only another card can be asked after the split.
     */
    private void split() {
        Hand hand = player.getHand();

        // Check if the 2 dealt cards have the same rank
        if (hand.size() == 2 && hand.get(0).lookupRank() == hand.get(1).lookupRank()){

            // If the Cards are both ACES
            if (hand.get(0).lookupRank() == Ranks.ACE && hand.get(1).lookupRank() == Ranks.ACE){
                // Set the Split to ACE type
                player.setSplitType(SplitType.ACE);
            }else {
                // ... otherwise a NORMAL split type
                player.setSplitType(SplitType.NORMAL);
            }

            player.createSplitHand(player.getHand().pop()); // Create the Split Hand with
        }
    }

    /**
     * If the first 2 dealt cards are between [9,11], the Player can double the initial Bet.
     * The Player won't be able to ask more than 1 card.
     * NOTE: The double down action is available also in the split hand.
     */
    private void doubleDown(){
        Hand hand = player.getPlayingHand();
        if (    hand.size() == 2 &&
                Utils.between(hand.total(), DOUBLE_DOWN_MIN, DOUBLE_DOWN_MAX)
        ){
            System.out.println("Doubling Down!");
        }
    }

    /**
     * TODO
     */
    private void insurance(){

    }

}
