package model.game;

import model.game.enums.Actions;
import model.game.models.player.HumanPlayer;
import model.game.models.player.Player;
import model.game.models.standalones.Dealer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static misc.Updates.*;

public class Turn extends Observable {

    private Player player;

    private List<Actions> playedActions;

    private boolean isActive;

    public Turn(Player player){
        this.player = player;
        this.playedActions = new ArrayList<>();
    }

    public void start(){
        isActive = true;

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
            setChanged();

            if (player.canMakeAction(action)) {
                playAction(action);
                playedActions.add(action);
                notifyObservers(action);
            } else {
                // Perform a Stand action to terminate the turn
                playAction(Actions.STAND);
                playedActions.add(Actions.STAND);
                notifyObservers(Actions.STAND);
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
}
