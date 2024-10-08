package model.db;

import model.game.models.Game;
import model.game.models.player.Player;
import model.global.User;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static controller.Constants.STARTING_BALANCE;

/**
 * Database manages the 2 databases of users and games.
 * - Users contains all the users registered to the game, along with their balance.
 * - Games contains all the games performed by each user.
 */
public class Database {

    private static Database instance;
    private File users;
    private File games;

    // Fields Indexes
    private final int UNAME_IDX= 0;
    private final int BALANCE_IDX= 1;

    private Database(){
        createRequiredFiles();
    }

    public static Database getInstance(){
       if (instance == null) {
           instance = new Database();
       }
       return instance;
    }

    /**
     * When first running the application, create the files if they are not present.
     * 1) Create (or access) the /resources folder
     * 2) Create (or access) the users file.
     * 3) Create (or access) the games file
      */
    private void createRequiredFiles() {
        File resourceDir = new File("./resources");
        if (!resourceDir.exists()){
            if (!resourceDir.mkdir()) {
                System.err.println("Could not create a non existing dir.");
                System.exit(1);
            }
        }

        File users = new File("./resources/users");
        File games = new File("./resources/games");
        if (!users.exists()){
            try {
                users.createNewFile();
            }
            catch(IOException ioe){
                System.exit(1);
            }
            }
        if (!games.exists()){
            try{
                games.createNewFile();
            }catch(IOException ioe){
                System.exit(1);
            }
        }


        this.users = users;
        this.games = games;
    }

    /**
     * Check if the username is already present in the users database.
     * @param username
     * @return
     */
    public boolean usernameExists(String username){
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.users.toString()));

            String entry;
            while ((entry= br.readLine()) != null) {
                if (username.equals(getUsername(entry))) {
                    br.close();
                    return true;
                }
            }

            br.close();
        }catch(IOException ioe){
            // Database does not exist
            System.exit(1);
        }

        return false;
    }

    /**
     * Add a User with given username to the DB file.
     * @param username
     */
    public void addUser(String username) throws InvalidUsernameException {
        if (username == null || username.length() < 1){
            throw new InvalidUsernameException();
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.users.toString(),true));

            String dbEntry = username.trim() +  "," + STARTING_BALANCE + "," + System.lineSeparator();
            bw.write(dbEntry);

            bw.close();
        } catch(RuntimeException | IOException ioe){
            // Database does not exist or could not instantiate a BufferedWriter
            System.exit(1);
        }
    }

    /**
     * Get a User instance by providing a username.
     * @param username
     * @return
     */
    public User getUser(String username) {
        // Take the fist element of the DB line. That's the username
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.users.toString()));

            String entry;
            while((entry= br.readLine()) != null){
                String[] fields = entry.split(",", -1);

                if (getUsername(entry).equals(username)){
                    int dbBalance = Integer.parseInt(fields[BALANCE_IDX]);
                    return new User(username, dbBalance);
                }
            }

            br.close();
        }catch(Exception e){
            System.err.println("Get User: " + e.getMessage());
            System.exit(1);
        }

        return null;
    }

    /**
     * Add a game to the games db, on the entry of the given user username.
     * A game entry is composed by:
     * - duration of the game
     * - earnings on the game
     * - blackjacks count
     * - busted hands count
     * - won hands count
     * - game rounds
     *
     * @param game
     * @param user
     */
    public void addGameToUser(Game game, User user){
        Player player = game.getHumanPlayer();
        // Format the game info with semicolon as game field separator.

        String duration = game.calculateTimePlayed();
        int earnings = player.getBankroll().getChipsLeft() - player.getBuyIn();
        int blackjacks = player.getBlackjacksCount();
        int bustedCount = player.getBustedHands();
        int wonHands = player.getWonHands();
        int gameRounds= game.getRoundNumber();

        String gameField = duration + ";" + earnings + ";" + blackjacks + ";" + bustedCount + ";" + wonHands + ";" + gameRounds + ",";

        try {
            // Find the correct line
            BufferedReader br = new BufferedReader(new FileReader(this.games.toString()));

            String entry;
            String updatedEntry;
            StringBuilder updatedFile = new StringBuilder();

            boolean userFound = false;

            while((entry = br.readLine()) != null) {
                if (getUsername(entry).equals(user.getUsername())){
                    userFound = true;
                    updatedEntry = entry + gameField;
                    updatedFile.append(updatedEntry).append(System.lineSeparator());
                }else{
                    updatedFile.append(entry).append(System.lineSeparator());
                }
            }

            // Create the first game entry for given user
            if (!userFound){
                String newEntry = user.getUsername() + "," + gameField;
                updatedFile.append(newEntry).append(System.lineSeparator());
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(this.games.toString(),false));

            bw.write(updatedFile.toString());

            br.close();
            bw.close();
        } catch(RuntimeException | IOException ioe){
            // Database does not exist or could not instantiate a BufferedWriter
            System.exit(1);
        }
    }

    /**
     * Update the user balance in the users database, by providing a game instance.
     * @param game
     * @param user
     */
    public void updateBalance(Game game, User user){
        int earnings = game.getHumanPlayer().getBankroll().getChipsLeft() - game.getHumanPlayer().getBuyIn();

        try {
            // Find user in db
            BufferedReader br = new BufferedReader(new FileReader(this.users));
            String entry;
            StringBuilder updatedFile = new StringBuilder();

            while((entry = br.readLine()) != null){
               if (getUsername(entry).equals(user.getUsername())){
                   int newBalance = getBalance(entry) + earnings;
                   String newEntry;
                    if (newBalance >= STARTING_BALANCE) {
                        newEntry = getUsername(entry) + "," + newBalance;
                    }else{
                        newEntry = getUsername(entry) + "," + STARTING_BALANCE;
                    }
                    updatedFile.append(newEntry).append(System.lineSeparator());
               }else{
                    updatedFile.append(entry).append(System.lineSeparator());
               }
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(this.users.toString(), false));
            bw.write(updatedFile.toString());

            br.close();
            bw.close();

        }catch(RuntimeException | IOException e){
            System.exit(1);
        }
    }

    /**
     * Get a List of user games.
     * @param username
     * @return
     */
    public List<String> getUserGames(String username){
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.games));

            String entry;
            while((entry = br.readLine()) != null){
                if (getUsername(entry).equals(username)){
                    String[] splitEntry = entry.split(",");

                    br.close();
                    return Arrays.asList(splitEntry).subList(1, splitEntry.length);
                }
            }

            br.close();

        }catch(RuntimeException | IOException e){
           System.exit(1);
        }

        return new ArrayList<>(); // no games found
    }

    /**
     * Get the username from the database entry (a line)
     * @param entry
     * @return
     */
    private String getUsername(String entry){
        return entry.split(",")[UNAME_IDX].trim();
    }


    /**
     * Get the balance from the database entry
     * @param entry
     * @return
     */
    private int getBalance(String entry){
        return Integer.parseInt(entry.split(",")[BALANCE_IDX].trim());
    }


}
