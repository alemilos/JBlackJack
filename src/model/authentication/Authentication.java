package model.authentication;

import model.db.Database;
import model.db.InvalidUsernameException;
import model.global.User;

import java.util.List;

import static model.global.Constants.ANOTHER_ACCESS;

public class Authentication {

    private User currentUser;
    private boolean isAuthenticated;

    public User loginOrRegister(String username) {
        if (!isAuthenticated) {
            Database db = Database.getInstance();
            // Login if the username already exists
            if (db.usernameExists(username)) {
                System.out.println(username + " logged in");

                isAuthenticated = true;
                currentUser = db.getUser(username);
                return currentUser;
            }
            // Register otherwise (create a new user entry in the DB)
            // Make sure the username can be added to DB.
            try {
                db.addUser(username);
            }catch(InvalidUsernameException iue){
                System.out.println("Invalid Username");
                return null;
            }
            System.out.println(username + " registered");

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
