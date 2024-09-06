package view.components.profile;

import view.components.shared.BalanceDisplay;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

import static controller.Constants.*;

public class GameListItem extends JPanel {

    public GameListItem(String gameEntry){
        setBackground(BG_COLOR);

        List<String> fields = List.of(gameEntry.split(";"));

        // Extract info
        String duration = fields.get(0);
        int earnings = Integer.parseInt(fields.get(1));
        int blackjacksCount  = Integer.parseInt(fields.get(2));
        int bustedHandsCount = Integer.parseInt(fields.get(3));
        int wonHandsCount = Integer.parseInt(fields.get(4));
        int gameRounds = Integer.parseInt(fields.get(5));

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(BG_COLOR);
        container.setBorder(new LineBorder(Color.white, 1, true));
        container.setBorder(new CompoundBorder(new LineBorder(Color.white,1,true), new EmptyBorder(10,10,10,10)));

        JPanel durationPanel = new JPanel();
        durationPanel.setBackground(BG_COLOR);
        JLabel durationLabel = new JLabel("Tempo di gioco " + duration);
        durationLabel.setFont(new Font("Arial", Font.BOLD, 16));
        durationLabel.setForeground(Color.darkGray);

        durationPanel.add(durationLabel);

        JPanel infosPanel = new JPanel(new GridLayout(2, 4, 20,6));
        infosPanel.setBackground(BG_COLOR);

        JLabel earningsLabel = new JLabel("Guadagni");
        earningsLabel.setFont(BOLD_FONT);
        earningsLabel.setForeground(Color.white);

        JLabel bjLabel= new JLabel("JBlackJacks");
        bjLabel.setFont(BOLD_FONT);
        bjLabel.setForeground(Color.white);

        JLabel roundsPlayed= new JLabel("Turni Giocati");
        roundsPlayed.setFont(BOLD_FONT);
        roundsPlayed.setForeground(Color.white);

        JLabel wonHandsLabel= new JLabel("Mani Vinte");
        wonHandsLabel.setFont(BOLD_FONT);
        wonHandsLabel.setForeground(Color.white);

        JLabel bustedHandsLabel= new JLabel("Mani Busted");
        bustedHandsLabel.setFont(BOLD_FONT);
        bustedHandsLabel.setForeground(Color.white);

        infosPanel.add(earningsLabel);
        infosPanel.add(bjLabel);
        infosPanel.add(roundsPlayed);
        infosPanel.add(wonHandsLabel);
        infosPanel.add(bustedHandsLabel);

        JLabel blackjacksCountLabel = new JLabel(""+blackjacksCount);
        blackjacksCountLabel.setForeground(Color.white);
        blackjacksCountLabel.setFont(BOLD_FONT);

        JLabel roundsPlayedLabel= new JLabel(""+gameRounds);
        roundsPlayedLabel.setForeground(Color.white);
        roundsPlayedLabel.setFont(BOLD_FONT);

        JLabel wonHandsCountLabel = new JLabel(""+wonHandsCount);
        wonHandsCountLabel.setForeground(Color.white);
        wonHandsCountLabel.setFont(BOLD_FONT);

        JLabel bustedHandsCountLabel = new JLabel(""+bustedHandsCount);
        bustedHandsCountLabel.setForeground(Color.white);
        bustedHandsCountLabel.setFont(BOLD_FONT);

        infosPanel.add(new BalanceDisplay(earnings));
        infosPanel.add(blackjacksCountLabel);
        infosPanel.add(roundsPlayedLabel);
        infosPanel.add(wonHandsCountLabel);
        infosPanel.add(bustedHandsCountLabel);

        container.add(durationPanel, BorderLayout.NORTH);
        container.add(infosPanel, BorderLayout.CENTER);

        add(container);
    }
}
