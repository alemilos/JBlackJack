package model.game.models.standalones;

import model.game.enums.Leagues;

import java.util.ArrayList;

public class Elo {
    private Leagues league;

    public Elo(int balance){
        if (balance < 2000){
            league = Leagues.BROKE;
        }
        else if(balance < 5000){
            league = Leagues.DREAMER;
        }
        else if(balance < 10000){
            league = Leagues.WEALTHY;
        }else{
            league = Leagues.KING;
        }

    }

    @Override
    public String toString() {
        return league.toString();
    }
}
