package controller;

import model.authentication.Authentication;
import model.global.User;
import view.AuthPage;
import view.HomePage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthController {

    private AuthPage authPage;
    private boolean isAuthenticating = true;

    private static AuthController instance;

    private User user;

    /**
     * The Controller manages the Model and the View.
     * */
    private AuthController(){
        this.authPage = new AuthPage();

        authPage.getSubmitBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user =  new Authentication().loginOrRegister(authPage.getUsernameInput().getText());
                if (user != null){
                        // Redirect to Home Page
                        System.out.println("Redirecting to home page");
                        authPage.dispose();
                        new HomeController();
                }
            }
        });
    }

    public boolean isAuthenticating() {
        return this.isAuthenticating;
    }

    public static AuthController getInstance(){
        if (instance == null){
           instance = new AuthController();
        }
        return instance;
    }

    public User getUser(){
        return this.user;
    }
}
