package model.global;

import model.game.Game;
import model.game.models.standalones.Elo;

import static misc.Constants.STARTING_BALANCE;

public class User {
    private String username;
    private Wallet wallet;
    private Elo elo;

    public User(String username){
        this.username = username;
        this.wallet = new Wallet();
        this.elo = new Elo(wallet.getBalance());
    }

    public User(String username, int balance) {
        this.username = username;
        this.wallet = new Wallet(balance);
        this.elo = new Elo(balance);
    }

    public Wallet getWallet() {
        return wallet;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Username: " + this.username + "\nBalance: " + this.wallet.getBalance();
    }

    public Elo getElo() {
        return elo;
    }

    /**
     * Update user data after the game.
     * - balance
     * - elo
     * @param game
     */
    public void updateAfterGame(Game game){
        int earnings = game.getHumanPlayer().getBankroll().getChipsLeft() - game.getHumanPlayer().getBuyIn();
        int newBalance= this.wallet.getBalance() + earnings;

        if (newBalance >= STARTING_BALANCE){
            this.wallet.setBalance(newBalance);
        }

        // Update the Elo based off the new balance.
        this.elo = new Elo(this.wallet.getBalance());

    }
}
