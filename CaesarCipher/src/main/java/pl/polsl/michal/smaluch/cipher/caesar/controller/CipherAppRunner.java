package pl.polsl.michal.smaluch.cipher.caesar.controller;

import java.util.Arrays;
import java.util.List;

/**
 * Runner class, contains {@code main} function and initializes
 * {@link pl.polsl.michal.smaluch.cipher.caesar.controller.CipherAppController}
 * object. Also responsible for general flow of the program which is calling
 * methods for: parsing user input, getting user input via input stream and
 * displaying output.
 *
 * @author Michal Smaluch
 * @version 1.0
 */
public class CipherAppRunner {

    /**
     * Default non-parametric constructor.
     */
    public CipherAppRunner() {
    }

    /**
     * Main method of the app.
     *
     * @param args arguments passed to application from command line.
     */
    public static void main(String[] args) {
        CipherAppController cipherAppController = new CipherAppController();

        //Initialization
        List<String> argsList = Arrays.asList(args);
        cipherAppController.start(argsList);
        if (cipherAppController.getHelpFlag()) {
            cipherAppController.printHelp();
            return;
        }

        //User input
        cipherAppController.askUser();

        //applying cipher and displaying results 
        cipherAppController.shiftMessage();
        cipherAppController.printOutput();
    }
}
