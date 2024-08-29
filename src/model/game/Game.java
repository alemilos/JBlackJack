package model.game;

import model.db.Database;
import model.game.enums.Actions;
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
import java.util.Observable;
import java.util.stream.Collectors;

import static misc.Updates.BET_FINISH;
import static misc.Updates.BET_START;

public class Game extends Observable {
    private static Game instance;

    private final int AI_PLAYERS_NUM= 4;
    private final int BUY_IN_AI_PLAYERS = 2000;

    private User user;

    private List<Player> players;
    private HumanPlayer humanPlayer;

    private Dealer dealer;

    private Date startedAt;
    private Date endedAt;

    private Turn turn;

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

        initializePlayers(user,  userBuyIn);
        this.dealer = Dealer.getInstance();
    }

    public void startGame(){
        this.startedAt = new Date();
    }

    public void finishGame(){
        // TODO: save game information to the user DB

        this.getDealer().resetInstance();
        instance = null;
        this.endedAt = new Date();
    }

    /**
     * Initialize the Human Player and 4 AIPlayers
     * @param user The user to create the HumanPlayer with
     */
    private void initializePlayers(User user, int userBuyIn){
        players = new ArrayList<>();

        // Create
        for (int i = 0; i < AI_PLAYERS_NUM ; i++){
            players.add(new AIPlayer(Utils.createRandomPlayer(), BUY_IN_AI_PLAYERS)) ;
        }

        // Add the human player
        humanPlayer = new HumanPlayer(user, userBuyIn);
        players.add(AI_PLAYERS_NUM / 2, humanPlayer);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Player> getPlayingPlayers(){
        return players.stream().filter(player-> !player.getBet().isEmpty()).collect(Collectors.toList());
    }

    public HumanPlayer getHumanPlayer() {
        return humanPlayer;
    }

    public Dealer getDealer() {
        return dealer;
    }

    /**
     * Update the Bet Phase and notify the observers.
     * @param betPhase weather the game is in bet state or not.
     */
    public void setBetPhase(boolean betPhase) {
        isBetPhase = betPhase;

        setChanged();
        notifyObservers(betPhase ? BET_START : BET_FINISH);
    }

    public boolean isBetPhase(){
        return this.isBetPhase;
    }

    /**
     * Start a new turn if previous doesn't exist or is terminated.
     * @param player
     */
    public void playTurn(Player player){
        if (turn == null || !turn.isActive()){
            turn = new Turn(player);
        }
    }

    /**
     * Terminate the turn by making a stand.
     */
    public void finishTurn(){
        if (turn != null && turn.isActive()) {
            turn.manageAction(Actions.STAND);
        }
    }

    public Turn getTurn() {
        return turn;
    }
}
