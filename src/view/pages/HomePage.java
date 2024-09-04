package view.pages;

import model.global.User;
import view.components.shared.BalanceDisplay;
import view.components.shared.EloBox;
import view.components.home.ProfileButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class HomePage extends JFrame{

    private JButton playBtn;
    private ProfileButton profileBtn;

    public HomePage(User user){
        String elo = user.getElo().getLeague().toString();
        int balance = user.getWallet().getBalance();

        setTitle("Home");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Game Title
        JLabel gameTitle = new JLabel("JBlackJack");
        gameTitle.setForeground(Color.white);
        gameTitle.setFont(new Font("Arial", Font.BOLD, 48));
        gameTitle.setBounds(20, 0, 300, 100);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.decode("#120F0F"));

        JPanel northContainer = new JPanel(new BorderLayout());
        northContainer.setBackground(null);

        JPanel southContainer = new JPanel(new BorderLayout());
        southContainer.setBackground(null);

        JPanel southInnerContainer = new JPanel();
        southInnerContainer.setBackground(null);

        playBtn = new JButton("Gioca");
        //playBtn.setPreferredSize(new Dimension(400, 150));
        playBtn.setFont(new Font("Arial", Font.BOLD, 72));
        playBtn.setBackground(Color.decode("#37322C"));
        playBtn.setForeground(Color.white);
        playBtn.setOpaque(true);
        playBtn.setBorderPainted(false);
        playBtn.setFocusPainted(false);
        playBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        playBtn.setBounds((int)((dim.getWidth()-400) / 2),(int)(dim.getHeight() - 150 - 80 - 100), 400,150);

        EloBox elobox = new EloBox(new ImageIcon("./assets/levels/" + elo + ".png"),elo);

        File avatarImage = new File("./assets/avatars/" + user.getUsername() + "avatar.png");
        ImageIcon avatarIcon;
        if (avatarImage.exists()){
            avatarIcon = new ImageIcon(avatarImage.getPath());
        }else{
            avatarIcon = new ImageIcon("./assets/avatars/defaultavatar.png");
        }

        profileBtn = new ProfileButton(avatarIcon, user.getUsername());

        container.add(northContainer, BorderLayout.NORTH);
        container.add(southContainer, BorderLayout.SOUTH);

        BalanceDisplay balanceDisplay = new BalanceDisplay(balance);
        balanceDisplay.setBounds((int)((dim.getWidth() - 300) /2), 20, 300, 100);

        northContainer.add(profileBtn, BorderLayout.EAST);

        southContainer.add(elobox, BorderLayout.WEST);

        container.setVisible(true);

        // Cards Image
        JLabel cardsImageHolder = new JLabel();
        ImageIcon icon = new ImageIcon(new ImageIcon("./assets/ui/cards.png").getImage().getScaledInstance(400,300,Image.SCALE_SMOOTH));
        cardsImageHolder.setIcon(icon);
        cardsImageHolder.setBounds((int)((dim.getWidth() - 400) / 2), (int)((dim.getHeight() - 520 ) / 2), 400,300);

        add(cardsImageHolder);
        add(gameTitle);
        add(balanceDisplay);
        add(playBtn);
        add(container);
        setVisible(true);
    }

    public JButton getPlayBtn() {
        return playBtn;
    }

    public ProfileButton getProfileButton() {
        return profileBtn;
    }
}
