package model.global;

import model.game.models.standalones.Elo;

public class User {
    private String username;
    private Wallet wallet;
    private Elo elo;


    public User(String username) {
        this.username = username;
        this.wallet = new Wallet();
        this.elo = new Elo();
    }

    public String getUsername() {
        return username;
    }
}
