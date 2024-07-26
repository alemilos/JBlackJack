package controller;

import model.authentication.Authentication;
import model.global.User;
import view.AuthPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthController {

    private static AuthController instance;

    private User user;

    /**
     * The Controller manages and interconnects the behaviours of the Model and the View.
     * */
    private AuthController(AuthPage authPage){
        authPage.getSubmitBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user =  new Authentication().loginOrRegister(authPage.getUsernameInput().getText());
            }
        });
    }

    public static AuthController getInstance(AuthPage authPage){
        if (instance == null){
           instance = new AuthController(authPage);
        }
        return instance;
    }

    public User getUser(){
        return this.user;
    }
}
