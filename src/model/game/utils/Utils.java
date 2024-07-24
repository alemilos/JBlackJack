package model.game.utils;

import java.util.Random;

// final -> the class cannott be subclassed
public final class Utils {

    /**
     * The Utils Class provides static methods that can be used all around the app.
     * The methods are custom and useful functionalities.
     */
    private Utils(){
        // private constructor -> cannot be instantiated
    }

    /**
     * TODO: Read from a file where all AI Players Names are located, choose randomly one.
     * @return
     */
    public static String createRandomPlayer(){
        return "AI Player";
    }

    /**
     * Check weather a value is included in a given range.
     * @param value
     * @param min
     * @param max
     * @return
     */
    public static boolean between(int value, int min, int max){
        return value >= min && value <= max;
    }



}
