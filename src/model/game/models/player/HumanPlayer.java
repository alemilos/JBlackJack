package model.game.models.player;

import model.global.User;

public class HumanPlayer extends Player{
    /**
     * A Human Player is constructed with a User instance.
      * @param user
     */
    public HumanPlayer(User user, int buyIn){
        super(user.getUsername(), buyIn);
    }
}
