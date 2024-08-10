package view.components.game;

import javax.security.auth.callback.Callback;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static misc.Constants.*;

public class NotificationsPanel extends JPanel {

    Timer timer;
    JProgressBar progressBar;

    JLabel notificationTitle;
    JPanel timerContainer;

    public NotificationsPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        timerContainer = new JPanel(new BorderLayout());
        timerContainer.setBackground(null);

        setBorder(new EmptyBorder(0,300,0,300));
        setBackground(PRIMARY_COLOR);

        notificationTitle= new JLabel();
        notificationTitle.setForeground(Color.white);
        notificationTitle.setFont(BOLD_FONT);
        notificationTitle.setAlignmentX(CENTER_ALIGNMENT);

        add(notificationTitle);
        add(timerContainer);
    }

    public void addTimer(String title, int ms, Runnable callback){
       progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, ms/1000);
       progressBar.setValue(ms/1000);

        ActionListener listener = new ActionListener() {
            int counter = ms/1000;
            @Override
            public void actionPerformed(ActionEvent e) {
                counter--;
                progressBar.setValue(counter);
                if (counter < 1) {
                    timer.stop();
                    clearTimer();
                    callback.run();
                }
            }
        };

        timer = new Timer(ms / 10, listener);
        timer.start();


        notificationTitle.setText(title);
        timerContainer.add(progressBar);
    }

    private void clearTimer(){
        notificationTitle.setText("");
        timerContainer.remove(progressBar);
    }
}
