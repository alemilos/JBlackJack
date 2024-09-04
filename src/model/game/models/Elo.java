package model.game.models;

import model.game.enums.Leagues;

public class Elo {
    private Leagues league;

    /**
     * Elo is based on the input balance.
     * @param balance
     */
    public Elo(int balance){
        if (balance < 3000){
            league = Leagues.BROKE;
        }
        else if(balance < 6000){
            league = Leagues.DREAMER;
        }
        else if(balance < 10000){
            league = Leagues.WEALTHY;
        }else{
            league = Leagues.KING;
        }

    }

    public Leagues getLeague() {
        return league;
    }
}
