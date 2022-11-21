package pl.polsl.michal.smaluch.cipher.caesar.model;

/**
 * Own exception class for handling invalid message.
 *
 * @author Michal Smaluch
 * @version 1.0
 */
public class InvalidMessageException extends Exception {

    /**
     * Default constructor
     */
    public InvalidMessageException() {
        super("InvalidMessageException!");
    }

    /**
     * Single argument constructor, calls super class with argument.
     *
     * @param message message passed to super class.
     */
    public InvalidMessageException(String message) {
        super(message);
    }
}
