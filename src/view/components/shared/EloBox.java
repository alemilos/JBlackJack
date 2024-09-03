package view.components.shared;

import javax.swing.*;
import java.awt.*;

public class EloBox extends JPanel{

    private JLabel imageHolder;
    private JLabel eloText;

    public EloBox(ImageIcon icon, boolean active) {
        setBackground(null);
        imageHolder = new JLabel();
        imageHolder.setIcon(new ImageIcon(icon.getImage().getScaledInstance(160,160,Image.SCALE_SMOOTH)));
        imageHolder.setEnabled(active);

        add(imageHolder, BorderLayout.SOUTH);
    }

    public EloBox(ImageIcon icon, String elo){
        setLayout(new BorderLayout());
        setBackground(null);
        setBorder(BorderFactory.createEmptyBorder(0,20,20,0));

        eloText = new JLabel(elo);
        eloText.setFont(new Font("Arial", Font.BOLD, 16));
        eloText.setForeground(Color.white);

        imageHolder = new JLabel();
        imageHolder.setIcon(new ImageIcon(icon.getImage().getScaledInstance(160,160,Image.SCALE_SMOOTH)));

        eloText.setHorizontalAlignment(JLabel.CENTER);
        add(eloText, BorderLayout.NORTH);
        add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.CENTER); // Gap
        add(imageHolder, BorderLayout.SOUTH);

        setVisible(true);
    }

}
