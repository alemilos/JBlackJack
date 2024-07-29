package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ExplanationPage extends JFrame {

    private JButton leftBtn;
    private JButton rightBtn;

    private int page;

    public ExplanationPage(){
        this(0);
    }


    public ExplanationPage(int pageNumber){
        this.page = pageNumber;

        setTitle("Info Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);

        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());
        JLabel background=new JLabel(new ImageIcon("./assets/backgrounds/expl" + pageNumber + ".png"));
        background.setLayout(new BorderLayout());

        leftBtn= new JButton();
        leftBtn.setIcon(new ImageIcon("./assets/icons/leftBtn.png"));
        leftBtn.setFocusPainted(false);
        leftBtn.setContentAreaFilled(false);
        leftBtn.setBorderPainted(false);
        leftBtn.setOpaque(false);
        leftBtn.setEnabled(page > 0);

        rightBtn = new JButton();
        rightBtn.setIcon(new ImageIcon("./assets/icons/rightBtn.png"));
        rightBtn.setFocusPainted(false);
        rightBtn.setContentAreaFilled(false);
        rightBtn.setBorderPainted(false);
        rightBtn.setOpaque(false);

        JPanel buttonsContainer = new JPanel();
        buttonsContainer.setLayout(new BorderLayout());
        buttonsContainer.setBackground(Color.decode("#37322C"));

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(leftBtn, BorderLayout.EAST);
        buttons.add(rightBtn, BorderLayout.EAST);
        buttons.setBackground(null);

        buttonsContainer.add(buttons, BorderLayout.EAST);
        background.add(buttonsContainer, BorderLayout.SOUTH);
        panel.add(background);
        add(panel);
        setVisible(true);
    }

    public JButton getRightBtn() {
        return this.rightBtn;
    }

    public JButton getLeftBtn() {
        return this.leftBtn;
    }
}
