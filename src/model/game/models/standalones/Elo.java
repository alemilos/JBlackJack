package model.game.models.standalones;

import model.game.enums.Leagues;

import java.util.ArrayList;

public class Elo {
    private Leagues league;

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
