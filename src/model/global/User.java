package model.global;

import model.game.models.standalones.Elo;

public class User {
    private String username;
    private Wallet wallet;
    private Elo elo;

    public User(String username){
        this.username = username;
        this.wallet = new Wallet();
    }

    public User(String username, int balance) {
        this.username = username;
        this.wallet = new Wallet(balance);
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Username: " + this.username + "\nBalance: " + this.wallet.getBalance();
    }
}
