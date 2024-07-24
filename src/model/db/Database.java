package model.db;

import model.global.User;

public class Database {

    private static Database instance;

    private Database(){
        createRequiredFiles();
    }

    public static Database getInstance(){
       if (instance == null) {
           instance = new Database();
       }
       return instance;
    }

    /**
     * When first running the application, create the files if they are not present.
      */
    private void createRequiredFiles(){
        // TODO: Check that the Users file is present, if not create it, otherwise set

    }

    public void addUser(String username, String password){}

    public void deleteUser(String username, String password){}

    public User getUser(String username){
        return null;
    }

    public void addGameToUser(){}

}
