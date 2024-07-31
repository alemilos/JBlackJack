package controller;

import model.global.User;
import view.pages.HomePage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeController {

    private static HomeController instance;
    private HomePage homePage;

    private HomeController(){
        User user = Controller.getUser();

        this.homePage = new HomePage(user.getUsername(), user.getElo().toString(),  user.getWallet().getBalance());

        homePage.getPlayBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePage.dispose();
                Controller.getInstance().goToGame();
            }
        });

        homePage.getProfileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePage.dispose();
                Controller.getInstance().goToProfile();
            }
        });
    }

    public static HomeController getInstance() {
        if (instance == null){
            instance = new HomeController();
        }
        return instance;
    }
}
