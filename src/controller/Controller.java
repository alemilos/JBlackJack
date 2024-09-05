package controller;

import misc.AudioManager;
import model.global.User;

public class Controller {
    private static User user;
    private static Controller instance;


    public Controller(){
        AuthController.getInstance();
    }

    public static Controller getInstance(){
        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    /**********************************************************************
     * NAVIGATION MANAGEMENT
     **********************************************************************/

    public void goToHome(){
        HomeController.getInstance();
    }

    public void goToProfile(){
        ProfileController.getInstance();
    }

    public void goToGame(){
        AudioManager.getInstance().stop();
        new GameController();
    }

    public static void setUser(User _user){
        user = _user;
    }

    public static User getUser() {
        return user;
    }
}
