package model.game;

import model.db.Database;
import model.global.User;
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
    private static Game instance;

    private final int AI_PLAYERS_NUM= 4;
    private final int BUY_IN_AI_PLAYERS = 1000;

    private User user;

    private List<Player> players;
    private HumanPlayer humanPlayer;

    private Dealer dealer;
    private Sabot sabot;

    private Date startedAt;
    private Date endedAt;

    private boolean isBetPhase;

    /**
     * Create a Game with given AI Players Number.
     * A Game is made up of players, dealer.
     */
    private Game(){
    }

    public static Game getInstance(){
        if (instance == null){
           instance = new Game();
        }

        return instance;
    }

    public void init(User user, int userBuyIn){
        this.user = user;
        this.players= initializePlayers(user,  userBuyIn);
        this.dealer = Dealer.getInstance();
        this.sabot = Sabot.getInstance(6);
    }

    public void startGame(){
        this.startedAt = new Date();
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
        humanPlayer = new HumanPlayer(user, userBuyIn);
        players.add(AI_PLAYERS_NUM / 2, humanPlayer);

        return players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public HumanPlayer getHumanPlayer() {
        return humanPlayer;
    }

    public void setBetPhase(boolean betPhase) {
        isBetPhase = betPhase;
    }

    public boolean isBetPhase(){
        return this.isBetPhase;
    }
}
