package model.game;

import model.global.User;
import model.game.managers.RoundManager;
import model.game.models.standalones.Dealer;
import model.game.models.standalones.Sabot;
import model.game.utils.Utils;
import model.game.models.player.AIPlayer;
import model.game.models.player.HumanPlayer;
import model.game.models.player.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {
    private final int AI_PLAYERS_NUM= 4;
    private final int BUY_IN_AI_PLAYERS = 1000;

    private User user;

    private List<Player> players;
    private Dealer dealer;
    private Sabot sabot;

    private Date startedAt;
    private Date endedAt;

    private RoundManager roundManager;

    /**
     * Create a Game with given AI Players Number.
     * A Game is made up of players, dealer.
     */
    public Game(User user, int userBuyIn){
        this.user = user;
        this.players= initializePlayers(user,  userBuyIn);
        this.dealer = Dealer.getInstance();
        this.sabot = Sabot.getInstance(6); // TODO: the sabot k must depend on the Game Difficulty.
        this.roundManager = new RoundManager(players, dealer);
    }

    public void startGame(){
        this.startedAt = new Date();

        // TODO: call the playRound() each time the previous ends.

        roundManager.playRound();
    }

    private void finishGame(){
        // TODO: save game information to the user DB
        this.endedAt = new Date();
    }

    /**
     * Initialize the Human Player and 4 AIPlayers
     * @param user The user to create the HumanPlayer with
     * @return
     */
    private List<Player> initializePlayers(User user, int userBuyIn){
        ArrayList<Player> players = new ArrayList<>();

        // Create
        for (int i = 0; i < AI_PLAYERS_NUM ; i++){
            players.add(new AIPlayer(Utils.createRandomPlayer(), BUY_IN_AI_PLAYERS)) ;
        }

        // Add the human player
        players.add(AI_PLAYERS_NUM / 2, new HumanPlayer(user, userBuyIn));

        return players;
    }

}
