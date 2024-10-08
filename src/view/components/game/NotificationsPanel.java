package view.components.game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static controller.Constants.*;

/**
 * A custom JPanel that can be used to add timers and text on the top of the page.
 */
public class NotificationsPanel extends JPanel {

    Timer timer;
    JProgressBar progressBar;

    JLabel notificationTitle;
    JPanel timerContainer;

    public NotificationsPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createTimerContainer();

        setBorder(new EmptyBorder(0,300,0,300));
        setBackground(BG_COLOR);

        notificationTitle= new JLabel();
        notificationTitle.setForeground(Color.white);
        notificationTitle.setFont(BOLD_FONT);
        notificationTitle.setAlignmentX(CENTER_ALIGNMENT);

        add(notificationTitle);
        add(timerContainer);
    }

    public void addTimer(String title, int ms, Runnable callback){
        clearNotificationBar(); // Make sure to clear previous progress bars if there are.
        createTimerContainer();
        add(timerContainer);

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
                    clearNotificationBar();
                    callback.run();
                }
            }
        };

        timer = new Timer(ms / 10, listener);
        timer.start();


        notificationTitle.setText(title);
        timerContainer.add(progressBar);
    }

    public void addTextNotification(String text){
        clearNotificationBar();
        notificationTitle.setText(text);
    }

    public void clearNotificationBar(){
        notificationTitle.setText("");
        if (progressBar != null) {
            progressBar.setValue(0);
            timerContainer.remove(progressBar);
            remove(timerContainer);
        }
    }

    private void createTimerContainer(){
        timerContainer = new JPanel(new BorderLayout());
        timerContainer.setBackground(null);
    }

    public void abortTimer(){
        if (timer.isRunning()) {
            timer.stop();
        }
        clearNotificationBar();
    }

}
