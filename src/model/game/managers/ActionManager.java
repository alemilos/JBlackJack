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

        System.out.println(player.getName() + " does: " + action.toString());

        switch (action){
            case HIT -> hit();
            case STAND -> stand();
            case DOUBLE_DOWN -> doubleDown();
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
    }

    /**
     * If the first 2 dealt cards are between [9,11], the Player can double the initial Bet.
     * The Player won't be able to ask more than 1 card.
     * NOTE: The double down action is available also in the split hand.
     */
    private void doubleDown(){
        if (player.canDoubleDown()){

            player.doubleBet();
            dealer.dealCard(player);
        }
    }

}
