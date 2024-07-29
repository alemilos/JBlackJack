package model.db;

public class InvalidUsernameException extends Exception{
    public InvalidUsernameException(){
       new InvalidUsernameException("Invalid Username");
    }

    public InvalidUsernameException(String message){
        super(message);
    }
}
