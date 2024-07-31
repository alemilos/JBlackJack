package model.global;

import model.game.models.standalones.Elo;

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
}
