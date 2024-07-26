package model.authentication;

import model.db.Database;
import model.global.User;

import java.util.List;

import static model.global.Constants.ANOTHER_ACCESS;

public class Authentication {

    private User currentUser;
    private static boolean isAuthenticated;

    public User loginOrRegister(String username) {
        if (!isAuthenticated) {
            System.out.println("Exectuing Authentication");
            Database db = Database.getInstance();
            // Login if the username already exists
            if (db.usernameExists(username)) {
                System.out.println(username + " logged in");
                db.updateAccess(username, ANOTHER_ACCESS); // this is not the first access to the app.

                isAuthenticated = true;
                currentUser = db.getUser(username);
                return this.currentUser;
            }

            System.out.println(username + " registered");

            // Register otherwise (create a new user entry in the DB)
            db.addUser(username);
            isAuthenticated = true;
            currentUser = db.getUser(username);
            return currentUser;
        }

        return currentUser;
    }

    public void logout(){
        this.isAuthenticated = false;
        this.currentUser = null;
    }
}
