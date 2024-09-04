package model.authentication;

import model.db.Database;
import model.db.InvalidUsernameException;
import model.global.User;

public class Authentication {

    private User currentUser;
    private boolean isAuthenticated;

    /**
     * Perform the user Authentication. If the username is present in the users database, log the user in.
     * If the user is not in the db, register the user to the database.
     * @param username
     * @return
     */
    public User loginOrRegister(String username) {
        if (!isAuthenticated) {
            Database db = Database.getInstance();
            // Login if the username already exists
            if (db.usernameExists(username)) {
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
            isAuthenticated = true;
            currentUser = db.getUser(username);
            return currentUser;
        }

        return currentUser;
    }

}
