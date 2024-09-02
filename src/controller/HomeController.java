package controller;

import misc.AudioManager;
import misc.Sounds;
import model.global.User;
import view.pages.HomePage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeController {

    private static HomeController instance;
    private HomePage homePage;

    private HomeController(){
        User user = Controller.getUser();
        this.homePage = new HomePage(user.getUsername(), user.getElo().toString());
        addActionListeners();
    }

    public static HomeController getInstance() {
        if (instance == null){
            instance = new HomeController();
        }

        AudioManager.getInstance().play(Sounds.HOME);
        return instance;
    }

    private void resetInstance(){
        instance = null;
    }

    private void addActionListeners(){
        homePage.getPlayBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePage.dispose();
                resetInstance();
                Controller.getInstance().goToGame();
            }
        });

        homePage.getProfileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePage.dispose();
                resetInstance();
                Controller.getInstance().goToProfile();
            }
        });
    }

}
