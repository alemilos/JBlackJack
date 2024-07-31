package controller;

import model.global.User;

public class Controller {
    private static User user;

    private static Controller instance;

    private AuthController authController;
    private HomeController homeController;
    private GameController gameController;
    private ProfileController profileController;

    public Controller(){
        authController = AuthController.getInstance();
    }

    public static Controller getInstance(){
        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    public void goToHome(){
        homeController = HomeController.getInstance();
    }

    public void goToProfile(){
        profileController = ProfileController.getInstance();
    }

    public void goToGame(){
        gameController = GameController.getInstance();
    }

    public static void setUser(User _user){
        user = _user;
    }

    public static User getUser() {
        return user;
    }
}
