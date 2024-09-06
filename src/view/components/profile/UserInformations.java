package view.components.profile;

import model.global.User;
import view.ui.IconButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class UserInformations extends JPanel {

    private IconButton avatar;
    private User user;

    public UserInformations(User user){
        this.user = user;
        setBackground(null);

        JPanel container = new JPanel(new FlowLayout());
        container.setBackground(null);

        ImageIcon avatarIcon;
        File iconPath = new File("./assets/avatars/" + user.getUsername() + "avatar.png");

        if (iconPath.exists()){
           avatarIcon = new ImageIcon(new ImageIcon(iconPath.getPath()).getImage().getScaledInstance(160,160,Image.SCALE_SMOOTH));
        }else{
            avatarIcon = new ImageIcon(new ImageIcon("./assets/avatars/defaultavatar.png").getImage().getScaledInstance(160,160,Image.SCALE_SMOOTH));
        }

        avatar = new IconButton(avatarIcon);

        JLabel username = new JLabel(user.getUsername());
        username.setFont(new Font("Arial", Font.BOLD, 22));
        username.setForeground(Color.white);

        container.add(avatar);
        container.add(Box.createRigidArea(new Dimension(20,0)));
        container.add(username);

        add(container);
    }

    public void updateImage() {
        File iconPath = new File("./assets/avatars/" + user.getUsername() + "avatar.png");
        ImageIcon avatarIcon;

        if (iconPath.exists()){
            avatarIcon = new ImageIcon(new ImageIcon(iconPath.getPath()).getImage().getScaledInstance(160,160,Image.SCALE_SMOOTH));
        }else{
            avatarIcon = new ImageIcon(new ImageIcon("./assets/avatars/defaultavatar.png").getImage().getScaledInstance(160,160,Image.SCALE_SMOOTH));
        }

        avatar.setIcon(avatarIcon);
    }

    public IconButton getAvatar(){
        return avatar;
    }
}
