package model.db;

import model.global.User;

import java.io.*;

import static model.global.Constants.FIRST_ACCESS;
import static model.global.Constants.STARTING_BALANCE;

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
    private File db;

    // Fields Indexes
    private final int UNAME_IDX= 0;
    private final int BALANCE_IDX= 1;
    private final int GAMES_IDX= 2;

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
                System.out.println("Could not create a non existing dir.");
                System.exit(1);
            }
        }

        File db = new File("./resources/db");
        if (!db.exists()){
            try {
                db.createNewFile();
            }
            catch(IOException ioe){
                System.exit(1);
            }
            }


        this.db = db;
    }

    public boolean usernameExists(String username){
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.db.toString()));

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
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.db.toString(),true));

            String dbEntry = username.trim() + "," + FIRST_ACCESS + "," + STARTING_BALANCE + "," + System.lineSeparator();
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
            BufferedReader br = new BufferedReader(new FileReader(this.db.toString()));

            String entry;
            StringBuilder updatedFile = new StringBuilder();
            while((entry= br.readLine()) != null){
                if (!username.equals(getUsername(entry))){
                    updatedFile.append(entry).append(System.lineSeparator());
                }
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(this.db.toString()));
            bw.write(updatedFile.toString());

            bw.close();
        }catch(IOException ioe){
            System.out.println("DB not found!");
            System.exit(1);
        }
    }

    public User getUser(String username) {
        // Take the fist element of the DB line. That's the username
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.db.toString()));

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

    public void addGameToUser(){

    }

    /**
     * Get the username from the database entry (a line)
     * @param entry
     * @return
     */
    private String getUsername(String entry){
        return entry.split(",")[UNAME_IDX].trim();
    }


}
