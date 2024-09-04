package controller;

import misc.AudioManager;
import view.pages.HomePage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeController {

    private static HomeController instance;
    private HomePage homePage;

    /**
     * Create a new Home page and add listeners to it
     */
    private HomeController(){
        this.homePage = new HomePage(Controller.getUser());
        addActionListeners();
    }

    public static HomeController getInstance() {
        if (instance == null){
            instance = new HomeController();
        }

        AudioManager.getInstance().playHomeSongOnRepeat();
        return instance;
    }

    private void resetInstance(){
        instance = null;
    }

    /**
     * Add listeners to the home page components
     */
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
