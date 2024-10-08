package model.game.models;

import model.game.enums.Actions;
import model.game.models.player.Player;
import java.util.Observable;
import java.util.Observer;
import static controller.enums.Updates.*;
import static model.game.utils.Constants.BLACKJACK;

public class Turn extends Observable {
    private Dealer dealer;
    private Player player;
    private boolean isActive;

    public Turn(Player player, Dealer dealer){
        this.player = player;
        this.dealer = dealer;

        if (player.getHand().softTotal() >= BLACKJACK){
            this.terminate();
            return;
        }

        start();
    }

    /**
     * Start a turn by setting isActive property
     */
    private void start(){
        isActive = true;
    }

    /**
     * Add Observer and notify TURN START
     * @param observer
     */
    public void startWithObserver(Observer observer){
        addObserver(observer);

        setChanged();
        notifyObservers(TURN_START);
    }

    /**
     * Terminate a turn and notify observers of TURN FINISH
     */
    private void terminate(){
        isActive = false;
        player.notifyTurnFinish();

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
                dealer.dealCard(player);

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
                dealer.dealCard(player);
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

}
