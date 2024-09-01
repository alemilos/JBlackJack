package misc;

import java.awt.*;

public class Constants {

    public static final int STARTING_BALANCE = 2000;

    // Timers
    public static final int BET_TIME_MS = 10000; // 10 sec
    public static final long USER_TURN_MS = 15000; // 15 sec
    public static final long AI_TURN_MS = 2000;

    public static final Color TABLE_COLOR = Color.decode("#054312");
    public static final Color PRIMARY_COLOR = Color.black;

    public static final Font BASE_FONT = new Font("Arial", 0, 16);
    public static final Font BOLD_FONT= new Font("Arial", Font.BOLD, 16);



    private Constants() {
    } // prevent initialization
}
