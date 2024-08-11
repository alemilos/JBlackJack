package misc;

import model.game.enums.Actions;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Utils {
    private Utils(){}

    public static String toCapitalizedString(String s, String oldDelim, String newDelim){
        String[] splittedString = s.trim().split(oldDelim);
        ArrayList<String> words = new ArrayList<>(Arrays.asList(splittedString));
        ArrayList<String> capitalizedWords = new ArrayList<>(words.stream().map(word -> word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase()).collect(Collectors.toList()));
        return String.join(newDelim, capitalizedWords);
    }

    public static Actions actionFromString(String actionName){
        if (actionName.equalsIgnoreCase("HIT")){
            return Actions.HIT;
        }
        if (actionName.equalsIgnoreCase("STAND")){
            return Actions.STAND;
        }
        if (actionName.equalsIgnoreCase("DOUBLE_DOWN")){
            return Actions.DOUBLE_DOWN;
        }

        return null;
    }


}
