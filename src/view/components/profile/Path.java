package view.components.profile;

import model.game.enums.Leagues;
import model.global.User;
import view.components.shared.EloBox;
import view.ui.JustifyBetweenFlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Path extends JPanel {

    public Path(User user){
        setBackground(null);
        setLayout(new BorderLayout());

        JLabel sectionTitle = new JLabel("Il tuo percorso");
        sectionTitle.setForeground(Color.white);
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 18));
        sectionTitle.setBorder(new EmptyBorder(0,0,30,0));

        JPanel container = new JPanel(new JustifyBetweenFlowLayout());
        container.setBackground(null);

        EloBox broke = new EloBox(new ImageIcon("./assets/levels/broke.png"), user.getElo().getLeague() == Leagues.BROKE);
        EloBox dreamer = new EloBox(new ImageIcon("./assets/levels/dreamer.png"), user.getElo().getLeague() == Leagues.DREAMER);
        EloBox wealthy = new EloBox(new ImageIcon("./assets/levels/wealthy.png"), user.getElo().getLeague() == Leagues.WEALTHY);
        EloBox king = new EloBox(new ImageIcon("./assets/levels/king.png"), user.getElo().getLeague() == Leagues.KING);

        container.add(broke);
        container.add(dreamer);
        container.add(wealthy);
        container.add(king);

        add(sectionTitle, BorderLayout.NORTH);
        add(container, BorderLayout.CENTER);
    }

}
