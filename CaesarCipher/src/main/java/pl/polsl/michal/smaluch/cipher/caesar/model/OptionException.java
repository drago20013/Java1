package pl.polsl.michal.smaluch.cipher.caesar.model;

/**
 *
 * @author Michal Smaluch
 */
public class OptionException extends Exception {
    public OptionException(){
        super("OptionException!");
    }
    
    public OptionException(String message){
        super(message);
    }
}