package pl.polsl.michal.smaluch.cipher.caesar.model;

/**
 * Own exception class for handling invalid option/argument flag.
 *
 * @author Michal Smaluch
 * @version 1.0
 */
public class InvalidOptionException extends Exception {

    /**
     * Default constructor
     */
    public InvalidOptionException() {
        super("InavalidOptionException!");
    }

    /**
     * Single argument constructor, calls super class with argument.
     *
     * @param message message passed to super class.
     */
    public InvalidOptionException(String message) {
        super(message);
    }
}
