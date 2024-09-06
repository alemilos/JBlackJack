package model.game.models;

import model.db.Database;
import model.game.enums.Actions;
import model.global.User;
import model.game.utils.Utils;
import model.game.models.player.AIPlayer;
import model.game.models.player.HumanPlayer;
import model.game.models.player.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

import static controller.enums.Updates.BET_FINISH;
import static controller.enums.Updates.BET_START;
import static model.game.utils.Constants.AI_PLAYERS_NUMBER;
import static model.game.utils.Constants.BUY_IN_AI_PLAYERS;

public class Game extends Observable {
    private static Game instance;
    private User user;
    private List<Player> players;
    private HumanPlayer humanPlayer;
    private Dealer dealer;
    private Date startedAt;
    private Turn turn;
    private boolean isBetPhase;
    private int roundNumber;

    /**
     * Create a Game with given AI Players Number.
     * A Game is made up of players, dealer.
     */
    public Game(User user, int userBuyIn){
        this.user = user;
        initializePlayers(user, userBuyIn);
        this.dealer = new Dealer();
    }


    /**
     * Save the date of the game start.
     */
    public void startGame(){
        this.startedAt = new Date();
    }

    /**
     * Handle the game termination, by updating the Databases
     */
    public void finishGame(){
        user.updateAfterGame(this);
        Database.getInstance().addGameToUser(this, user);
        Database.getInstance().updateBalance(this, user);
    }

    /**
     * Initialize the Human Player and 4 AIPlayers with unique names
     * @param user The user to create the HumanPlayer with
     */
    private void initializePlayers(User user, int userBuyIn){
        players = new ArrayList<>();
        List<String> playersNames = new ArrayList<>() ;

        // Generate AI_PLAYER_NUMBER unique names
        for (int i = 0; i < AI_PLAYERS_NUMBER; i++)  {
            while(true) {
                String playerName = Utils.createRandomPlayer();
                if (!playersNames.contains(playerName)){
                    playersNames.add(playerName);
                    break;
                }
            }
        }

        // Create AI Players
        for (int i = 0; i < AI_PLAYERS_NUMBER ; i++){
            players.add(new AIPlayer(playersNames.get(i), BUY_IN_AI_PLAYERS)) ;
        }

        // Add the human player in the middle
        humanPlayer = new HumanPlayer(user, userBuyIn);
        players.add(AI_PLAYERS_NUMBER / 2, humanPlayer);
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



    /**
     * Start a new turn if previous doesn't exist or is terminated.
     * @param player
     */
    public void playTurn(Player player){
        if (turn == null || !turn.isActive()){
            turn = new Turn(player, dealer);
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



    /**
     * Make sure all the game state is set back to the round start
     */
    public void clearRound(){
        roundNumber++;
        players = players.stream().filter(player -> player.getBankroll().getChipsLeft() > 0).toList();
        players.forEach(Player::reset);
        dealer.clearHand();
        this.turn = null;
    }

    /**
     * Calculate time played from the beginning of the game to the current moment.
     * The return format is hh:mm:ss
     * @return
     */
    public String calculateTimePlayed(){
        Date now = new Date();
        long timePlayed = now.getTime() - startedAt.getTime();

        // Convert to hours:minutes:seconds from milliseconds
        long hours = timePlayed / 3600000;
        long minutes = (timePlayed % 3600000) / 60000;
        long seconds = (timePlayed % 60000) / 1000;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Must be called before the round is reinitialized.
     */
    public void updatePlayerStats(){
        getPlayingPlayers().forEach(player -> {
            if(player.getHand().isBlackjack()){
                player.incrementBlackjacks();
            }
            if (player.getHand().isBusted()){
                player.incrementBustedHands();
            }
            else{
                if (dealer.getHand().isBusted() || player.getHand().softTotal() > dealer.getHand().softTotal()){
                    player.incrementWonHands();
                }
            }
        });
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

    public boolean isBetPhase(){
        return this.isBetPhase;
    }

    public Turn getTurn() {
        return turn;
    }
    public int getRoundNumber() {
        return roundNumber;
    }
}
