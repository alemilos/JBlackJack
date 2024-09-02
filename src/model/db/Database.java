package model.db;

import model.game.Game;
import model.game.models.player.HumanPlayer;
import model.game.models.player.Player;
import model.global.User;

import java.io.*;
import java.util.Arrays;
import java.util.Date;

import static misc.Constants.STARTING_BALANCE;

/* The Database's scope is to provide a simple way to access User information.
*  Each line is assigned to a different user.
*  Each User field is separated by a "," (comma).
*
*  The fields are:
*  - username
*  - wallet count
*  - ...games -> Each game field is separated by a "|" ()
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
     * 2) Create (or access) the db file.
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

    public boolean usernameExists(String username){
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.users.toString()));

            String entry;
            while ((entry= br.readLine()) != null) {
                if (username.equals(getUsername(entry))) return true;
            }
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
     * Remove the line containing the input username, if it does exist.
     * @param username
     */
    public void deleteUser(String username){
        if (username == null || username.length()<1){
            return;
        }
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.users.toString()));

            String entry;
            StringBuilder updatedFile = new StringBuilder();
            while((entry= br.readLine()) != null){
                if (!username.equals(getUsername(entry))){
                    updatedFile.append(entry).append(System.lineSeparator());
                }
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(this.users.toString()));
            bw.write(updatedFile.toString());

            bw.close();
        }catch(IOException ioe){
            System.err.println("DB not found!");
            System.exit(1);
        }
    }

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

    public void addGameToUser(Game game, User user){
        // Format the game info with semicolon as game field separator.
        String duration = game.calculateTimePlayed();
        int earnings = calculatePlayerEarnings(game.getHumanPlayer());
        int blackjacks = getBlackjacksCount(game.getHumanPlayer());
        int bustedCount = getBustedCount(game.getHumanPlayer());
        int wonHands = getWonHandsCount(game.getHumanPlayer());
        String gameField = duration + ";" + earnings + ";" + blackjacks + ";" + bustedCount + ";" + wonHands + ",";

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

    public void updateBalance(Game game, User user){
        int earnings = calculatePlayerEarnings(game.getHumanPlayer());

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
     * Get the username from the database entry (a line)
     * @param entry
     * @return
     */
    private String getUsername(String entry){
        return entry.split(",")[UNAME_IDX].trim();
    }


    /**
     * Get the balance in the users db by the username.
     * @param username
     * @return
     */
    public int getBalanceByUsername(String username) {
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.users));

            String entry;
            while((entry = br.readLine()) != null){
                if (getUsername(entry).equals(username)){

                    br.close();
                    return getBalance(entry);
                }
            }

            throw new IOException("user not found");

        }catch(RuntimeException | IOException e){
            System.exit(1);
        }

        return 0; // should be never reached
    }

    private int getBalance(String entry){
        return Integer.parseInt(entry.split(",")[BALANCE_IDX].trim());
    }


    private int calculatePlayerEarnings(Player player){
        return player.getBankroll().getChipsLeft() - player.getBuyIn();
    }

    private int getBlackjacksCount(Player player){
        return player.getBlackjacksCount();
    }

    private int getBustedCount(Player player){
        return player.getBustedHands();
    }

    private int getWonHandsCount(Player player){
        return player.getWonHands();
    }

}
