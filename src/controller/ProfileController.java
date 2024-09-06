package controller;

import model.db.Database;
import org.w3c.dom.ls.LSOutput;
import view.pages.ProfilePage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ProfileController {

    private static ProfileController instance;

    private ProfilePage profilePage;

    /**
     * Create a profile page and add listeners to it.
     */
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

    /**
     * Add Listeners to the Profile Page components
     */
    private void addActionListeners(){
        profilePage.getGoBackBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profilePage.dispose();
                resetInstance();
               Controller.getInstance().goToHome();
            }
        });

        profilePage.getUserInformations().getAvatar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileUpload = new JFileChooser();

                int res = fileUpload.showOpenDialog(null);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileUpload.getSelectedFile();

                    File destinationFolder = new File("./assets/avatars");
                    if (!destinationFolder.exists()) {
                        System.exit(1); // broke assets
                    }

                    File destinationFile = new File(destinationFolder, Controller.getUser().getUsername() + "avatar.png");

                    try {
                        Files.copy(selectedFile.toPath(), destinationFile.toPath());
                        profilePage.getUserInformations().updateImage();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

}
