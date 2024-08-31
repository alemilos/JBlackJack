package model.game;

import model.game.enums.Actions;
import model.game.models.player.Player;
import model.game.models.standalones.Dealer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static misc.Updates.*;
import static model.game.utils.Constants.BLACKJACK;

public class Turn extends Observable {

    private Player player;

    private boolean isActive;

    public Turn(Player player){
        this.player = player;

        if (player.getHand().softTotal() >= BLACKJACK){
            this.terminate();
            return;
        }

        start();
    }

    private void start(){
        isActive = true;
    }

    public void startWithObserver(Observer observer){
        addObserver(observer);

        setChanged();
        notifyObservers(TURN_START);
    }

    private void terminate(){
        isActive = false;

        setChanged();
        notifyObservers(TURN_FINISH);
    }

    /**
     * Check if the player can make the action. Notify Observers if that's the case, otherwise notify stand because the
     * player can't make any action.
     * @param action
     */
    public void manageAction(Actions action){
        if (isActive) {
            if (player.canMakeAction(action)) {
                playAction(action);

                // Stand should block the turn update
                if (action == Actions.STAND || action == Actions.DOUBLE_DOWN){
                    this.terminate();
                    return;
                }

                setChanged();
                notifyObservers(TURN_UPDATE);
            } else {
                // Perform a Stand action to terminate the turn
                this.terminate();
            }
        }
    }

    private void playAction(Actions action){
        switch (action){
            /**
             * Hit a card. The turn doesn't terminate until a STAND is performed.
             */
            case HIT:
                Dealer.getInstance().dealCard(player);

                if (player.getHand().softTotal() >= BLACKJACK){
                    this.terminate();
                }
                break;

            /**
             * Simply terminate the turn.
             */
            case STAND:
                this.terminate();
                break;

            /**
             * Double down will deal only a card to the player, and the turn will be terminated.
             */
            case DOUBLE_DOWN:
                player.doubleBet();
                Dealer.getInstance().dealCard(player);
                this.terminate();
                break;
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return player.getName() + " turn is active playing: " + isActive;
    }

}
