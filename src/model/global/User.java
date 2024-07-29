package model.global;

import model.game.models.standalones.Elo;

public class User {
    private String username;
    private Wallet wallet;
    private boolean isFirstAccess;
    // TODO: private Elo elo;

    public User(String username, boolean isFirstAccess){
        this.username = username;
        this.wallet = new Wallet();
        this.isFirstAccess = isFirstAccess;
    }

    public User(String username, int balance, boolean isFirstAccess) {
        this.username = username;
        this.wallet = new Wallet(balance);
        this.isFirstAccess = isFirstAccess;
    }

    public String getUsername() {
        return username;
    }

    public boolean isFirstAccess(){
       return this.isFirstAccess;
    }

    @Override
    public String toString() {
        return "Username: " + this.username + "\nBalance: " + this.wallet.getBalance();
    }

}
