package pl.polsl.michal.smaluch.cipher.caesar.model;

/**
 *
 * @author Michal Smaluch
 */
public class InvalidMessageException extends Exception {
    public InvalidMessageException(){
        super("InvalidMessageException!");
    }
    
    public InvalidMessageException(String message){
        super(message);
    }
}