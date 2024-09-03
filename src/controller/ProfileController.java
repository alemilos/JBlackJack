package controller;

import view.pages.ProfilePage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileController {

    private static ProfileController instance;

    private ProfilePage profilePage;

    private ProfileController(){
        profilePage = new ProfilePage();
        addActionListeners();
    }

    public static ProfileController getInstance() {
        if (instance == null){
            instance = new ProfileController();
        }
        return instance;
    }

    public void resetInstance(){instance = null;}

    private void addActionListeners(){
        profilePage.getGoBackBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profilePage.dispose();
                resetInstance();
               Controller.getInstance().goToHome();
            }
        });
    }

}
