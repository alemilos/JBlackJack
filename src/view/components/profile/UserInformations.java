package view.components.profile;

import model.global.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class UserInformations extends JPanel {

    private JLabel avatar;

    public UserInformations(User user){
        setBackground(null);

        JPanel container = new JPanel(new FlowLayout());
        container.setBackground(null);

        avatar = new JLabel();
        File iconPath = new File("./assets/avatars/" + user.getUsername() + "avatar.png");

        if (iconPath.exists()){
           avatar.setIcon(new ImageIcon(new ImageIcon(iconPath.getPath()).getImage().getScaledInstance(160,160,Image.SCALE_SMOOTH)));
        }else{
            avatar.setIcon(new ImageIcon(new ImageIcon("./assets/avatars/defaultavatar.png").getImage().getScaledInstance(160,160,Image.SCALE_SMOOTH)));
        }

        JLabel username = new JLabel(user.getUsername());
        username.setFont(new Font("Arial", Font.BOLD, 22));
        username.setForeground(Color.white);

        container.add(avatar);
        container.add(Box.createRigidArea(new Dimension(20,0)));
        container.add(username);

        add(container);
    }
}
