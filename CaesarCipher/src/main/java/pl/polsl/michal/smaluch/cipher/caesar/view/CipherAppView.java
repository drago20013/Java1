package pl.polsl.michal.smaluch.cipher.caesar.view;

import java.util.Scanner;
import pl.polsl.michal.smaluch.cipher.caesar.controller.CipherAppController;

/**
 * A class that handles the UI in the console.
 *
 * @author Michal Smaluch
 * @version 1.0
 */
public class CipherAppView {

    private final CipherAppController cipherAppController;

    private final Scanner scanner;

    /**
     * Single argument constructor, creates scanner and assign reference to
     * {@link CipherAppController}.
     *
     * @param cipherAppController reference to {@link CipherAppController}.
     */
    public CipherAppView(CipherAppController cipherAppController) {
        this.cipherAppController = cipherAppController;
        scanner = new Scanner(System.in).useDelimiter("\\n");
    }

    /**
     * Possible missing values
     */
    public enum MissingValue {
        /**
         * Message is missing
         */
        MESSAGE,
        /**
         * Key is missing
         */
        KEY,
        /**
         * Option is missing or wrong
         */
        OPTION
    }

    /**
     * Prints to user questions based on missing value.
     *
     * @param missingValue missing value.
     */
    public void askUser(MissingValue missingValue) {
        switch (missingValue) {
            case MESSAGE ->
                printMessage("Please provide a message: ");
            case KEY ->
                printMessage("Please provide a key: ");
            case OPTION ->
                printMessage("Select if you want to decrypt or encrypt [d/e]:");
        }
    }

    /**
     * Prints formatted output.
     *
     * @param originalMessage original saved massage.
     * @param processedMessage processed message either encrypted or decrypted.
     * @param decryptEncryptFlag true if were decrypting, false if were
     * encrypting.
     */
    public void printOutput(String originalMessage, String processedMessage, boolean decryptEncryptFlag) {
        printMessage("Here is your result:");
        printMessage("Original: ");
        printMessage(originalMessage);
        printMessage((decryptEncryptFlag ? "Decrypted: " : "Encrypted: "));
        printMessage(processedMessage);
    }

    /**
     * Prints line to the console.
     *
     * @param message message to be printed.
     */
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints exception to the console.
     *
     * @param exception exception to be printed.
     */
    public void printMessage(Exception exception) {
        System.out.println(exception);
    }

    /**
     * Prints help message to the console.
     */
    public void printHelp() {
        String help = """
                      Usage:
                      CaesarCipher [-d | -e] [-k <key>] [-m <message>]
                      
                      Options:
                      -d decrypt message
                      -e encrypt message
                      -k set cipher key
                      -m specify message to encrypt/decrypt
                      -h displays help
                      
                      Message can contain only English alphabet letters.
                      It's possible to run program without any flags
                      """;
        printMessage(help);
    }

    /**
     * Gets one line of user input.
     *
     * @return one line of user input.
     */
    public String getInput() {
        return scanner.next();
    }
}
