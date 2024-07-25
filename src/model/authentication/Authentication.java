package model.authentication;

import model.db.Database;
import model.global.User;

public class Authentication {

    public User loginOrRegister(String username) {
        Database db = Database.getInstance();
        // Login if the username already exists
        if (db.usernameExists(username)){
            System.out.println(username + " has logged in");
            return db.getUser(username);
        }

        // Register otherwise
        db.addUser(username);
        return db.getUser(username);
    }
}
