package pl.polsl.michal.smaluch.cipher.caesar.controller;

import java.util.List;
import pl.polsl.michal.smaluch.cipher.caesar.model.CipherAppModel;
import pl.polsl.michal.smaluch.cipher.caesar.model.InvalidMessageException;
import pl.polsl.michal.smaluch.cipher.caesar.model.InvalidOptionException;
import pl.polsl.michal.smaluch.cipher.caesar.view.CipherAppView;

/**
 * Main class of the program, it acts as the intermediary between the
 * {@link pl.polsl.michal.smaluch.cipher.caesar.model.CipherAppModel} and the
 * {@link pl.polsl.michal.smaluch.cipher.caesar.view.CipherAppView} so the
 * proper separation between logic and user interface is implemented.
 *
 * @author Michal Smaluch
 * @version 1.0
 */
public class CipherAppController {

    private CipherAppView cipherAppView;
    private CipherAppModel cipherAppModel;

    /**
     * Default non-parametric constructor.
     */
    public CipherAppController() {
    }

    /**
     * Method which creates {@link CipherAppView} and {@link CipherAppModel}
     * instances, sends arguments to model for parsing. Might catch exception if
     * parsing failed.
     *
     * @param args arguments list to be passed to model for parsing.
     */
    public void start(List<String> args) {
        cipherAppView = new CipherAppView(this);
        cipherAppModel = new CipherAppModel(this);
        
        
        try {
            cipherAppModel.parseArguments(args);
        }
        catch (InvalidOptionException | NumberFormatException | InvalidMessageException e) {
            cipherAppView.printHelp();
            cipherAppView.printMessage("Something was wrong with your arguments input, program will ask you for missing ones if there are any.");
            cipherAppView.printMessage(e);
        }
    }

    /**
     * Calls {@code printHelp()} on view.
     */
    public void printHelp() {
        cipherAppView.printHelp();
    }

    /**
     * Calls {@code getHelpFlag()} on model.
     *
     * @return true/false value of help flag.
     */
    public boolean getHelpFlag() {
        return cipherAppModel.getHelpFlag();
    }

    /**
     * Method which checks if there are missing information and ask user for
     * them.
     */
    public void askUser() {
        while (!cipherAppModel.getDecryptFlag() && !cipherAppModel.getEncryptFlag()) {
            cipherAppView.askUser(CipherAppView.MissingValue.OPTION);
            String choice = cipherAppView.getInput();
            if ("d".equals(choice)) {
                cipherAppModel.setDecryptFlag();
            } else if ("e".equals(choice)) {
                cipherAppModel.setEncryptFlag();
            }
        }

        while (!cipherAppModel.getMessageFlag()) {
            cipherAppView.askUser(CipherAppView.MissingValue.MESSAGE);
            String message = cipherAppView.getInput();
            try {
                cipherAppModel.setMessage(message);
            }
            catch (InvalidMessageException e) {
                cipherAppView.printMessage("Something was wrong with your message, try again.");
                cipherAppView.printMessage(e);
            }
        }

        while (!cipherAppModel.getKeyFlag()) {
            cipherAppView.askUser(CipherAppView.MissingValue.KEY);
            try {
                cipherAppModel.setKey(Integer.parseUnsignedInt(cipherAppView.getInput()));
            }
            catch (NumberFormatException e) {
                cipherAppView.printMessage("Something was wrong with your key, try again");
                cipherAppView.printMessage(e);
            }
        }
    }

    /**
     * Calls {@code shiftMessage()} on model.
     */
    public void shiftMessage() {
        cipherAppModel.shiftMessage();
    }

    /**
     * Calls {@code printOutput()} on view and passes messages to be displayed.
     */
    public void printOutput() {
        cipherAppView.printOutput(cipherAppModel.getMessage(), cipherAppModel.getProcessedMessage(), cipherAppModel.getDecryptFlag());
    }
}
