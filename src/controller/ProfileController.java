package controller;

import view.pages.ProfilePage;

public class ProfileController {

    private static ProfileController instance;

    private ProfilePage profilePage;

    private ProfileController(){
        profilePage = new ProfilePage();
    }

    public static ProfileController getInstance() {
        if (instance == null){
            instance = new ProfileController();
        }
        return instance;
    }

    public void resetInstance(){instance = null;}

    private void addActionListeners(){

    }

}
