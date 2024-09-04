package model.game.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

// final -> the class cannott be subclassed
public final class Utils {
    private final static List<String> playerNames = Arrays.asList(
            // Movie Characters
            "Harry Potter", "Vader", "Frodo",
            "Hermione", "Luke", "Sherlock", "Wolverine", "Neo",
            "Joker", "Gandalf", "Dobby",
            "Bilbo", "Batman", "Peter Parker",
            "Gollum", "Woody",

            // Game Characters
            "Mario",  "Kratos", "Geralt",
            "Lara", "Sonic",
            "Nathan", "Elena", "Sully", "Chloe",
            "Dante", "Ezio",
            "Pikachu", "Luigi", "Mario",
            "Crash", "Joel", "Ellie", "Bowser", "Yoshi",

            // League of Legends Characters
            "Darius", "Garen", "Rumble", "Blitzcrank",
            "Yasuo", "Zed", "Irelia", "Katarina", "Riven", "Vayne", "Morgana",
            "Lee Sin", "Thresh", "Jhin", "Kayn", "Aatrox",
            "Sett", "Fiora", "Orianna", "Rengar", "Janna",

            // Famous Programmers
            "Turing", "Linus Torvalds", "Knuth",
            "George Hotz", "James Gosling", "Terry A.Davis",
            "Wozniak", "Dijkstra", "Aaron Swartz"
    );

    /**
     * The Utils Class provides static methods that can be used all around the app.
     * The methods are custom and useful functionalities.
     */
    private Utils(){
        // private constructor -> cannot be instantiated
    }

    /**
     * Generate random player names.
     * @return
     */
    public static String createRandomPlayer(){
        Random random = new Random();
        int index = random.nextInt(playerNames.size());
        return playerNames.get(index);
    }

    /**
     * Check weather a value is included in a given range.
     * @param value the input value
     * @param min the min included
     * @param max the max included
     * @return true if value is in the range, otherwise false.
     */
    public static boolean between(int value, int min, int max){
        return value >= min && value <= max;
    }



}
