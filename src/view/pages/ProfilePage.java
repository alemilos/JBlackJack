package view.pages;

import controller.Controller;
import model.db.Database;
import model.global.User;
import view.components.profile.LastGames;
import view.components.profile.Path;
import view.components.profile.UserInformations;
import view.components.shared.BalanceDisplay;
import view.ui.IconButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import static misc.Constants.BG_COLOR;

public class ProfilePage extends JFrame {

    private IconButton goBackBtn;
    private UserInformations userInformations;
    private Path path;
    private LastGames lastGames;

    public ProfilePage() {
        User user = Controller.getUser();

        setTitle("Profile");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(BG_COLOR);
        container.setBorder(new EmptyBorder(20,20,0,0));

        // Game Title
        JLabel gameTitle = new JLabel("JBlackJack");
        gameTitle.setForeground(Color.white);
        gameTitle.setFont(new Font("Arial", Font.BOLD, 48));

        // North container
        JPanel northContainer = new JPanel(new BorderLayout());
        northContainer.setBackground(null);

        northContainer.add(gameTitle, BorderLayout.WEST);
        goBackBtn = new IconButton(new ImageIcon(new ImageIcon("./assets/icons/leftbtn.png").getImage().getScaledInstance(60,60,Image.SCALE_SMOOTH)));
        northContainer.add(goBackBtn, BorderLayout.EAST);

        // Balance Display fixed
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        BalanceDisplay balanceDisplay = new BalanceDisplay(user.getWallet().getBalance());
        balanceDisplay.setBounds((int)((dim.getWidth() - 300) /2), 20, 300, 100);

        // Inner Container
        JPanel innerContainer= new JPanel(new BorderLayout());
        innerContainer.setBackground(null);
        innerContainer.setBorder(new EmptyBorder(60,60,10,60));

        userInformations = new UserInformations(user);
        path = new Path(user);
        lastGames = new LastGames(user);

        innerContainer.add(userInformations, BorderLayout.NORTH);
        innerContainer.add(path, BorderLayout.CENTER);
        innerContainer.add(lastGames, BorderLayout.SOUTH);

        container.add(northContainer, BorderLayout.NORTH);
        container.add(innerContainer, BorderLayout.CENTER);

        add(balanceDisplay);
        add(container);
    }

    public IconButton getGoBackBtn() {
        return goBackBtn;
    }
}
