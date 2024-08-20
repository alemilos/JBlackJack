package model.game;

import model.game.models.player.HumanPlayer;
import model.game.models.player.Player;

import java.util.Observable;

import static misc.Updates.TURN_UPDATE;

public class Turn extends Observable {

    private Player player;

    private boolean isActive;

    public Turn(){
    }

    public void start(){
        isActive = true;

        if (player instanceof HumanPlayer) {
            setChanged();
            notifyObservers(TURN_UPDATE);
        }
    }

    public void terminate(){
        isActive = false;

        if (player instanceof  HumanPlayer) {
            setChanged();
            notifyObservers(TURN_UPDATE);
        }
    }

}
