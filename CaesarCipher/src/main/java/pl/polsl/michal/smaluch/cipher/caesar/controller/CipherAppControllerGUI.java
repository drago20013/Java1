package pl.polsl.michal.smaluch.cipher.caesar.controller;

import java.util.List;
import pl.polsl.michal.smaluch.cipher.caesar.model.CipherAppModel;
import pl.polsl.michal.smaluch.cipher.caesar.model.InvalidMessageException;
import pl.polsl.michal.smaluch.cipher.caesar.model.InvalidOptionException;
import pl.polsl.michal.smaluch.cipher.caesar.view.CipherAppGraphicalView;

/**
 * Main class of the program, it acts as the intermediary between the
 * {@link pl.polsl.michal.smaluch.cipher.caesar.model.CipherAppModel} and the
 * {@link pl.polsl.michal.smaluch.cipher.caesar.view.CipherAppGraphicalView} so the
 * proper separation between logic and user interface is implemented.
 *
 * @author Michal Smaluch
 * @version 1.0
 */
public class CipherAppControllerGUI {
    private CipherAppGraphicalView cipherAppView;
    private CipherAppModel cipherAppModel;

    /**
     * Default non-parametric constructor.
     */
    public CipherAppControllerGUI() {
        cipherAppModel = new CipherAppModel();
        cipherAppView = new CipherAppGraphicalView(this);
    }

    /**
     * Method which creates {@link CipherAppGraphicalView} and {@link CipherAppModel}
     * instances, sends arguments to model for parsing. Might catch exception if
     * parsing failed.
     * 
     * Invokes GUI creation.
     *
     * @param args arguments list to be passed to model for parsing.
     */
    public void start(List<String> args) {
        javax.swing.SwingUtilities.invokeLater(cipherAppView::createAndShowGUI);
        
        try {
            cipherAppModel.parseArguments(args);
            cipherAppView.setInit();
        }
        catch (InvalidOptionException | NumberFormatException | InvalidMessageException e) {
           cipherAppView.printHelp();
           cipherAppView.showDialogError("Something was wrong with your arguments input, please fill up missing fields if there are any.");
        }
    }

    /**
    * Sets operation to be encryption.
    */
    public void setEncryption(){
        cipherAppModel.setEncryptFlag();
    }
        
    /**
     * Sets operation to be decryption.
     */
    public void setDecryption(){
        cipherAppModel.setDecryptFlag();
    }
    
    /**
     * Sets key in the model.
     * @param key key nr
     */
    public void setKey(String key){
        try {
            cipherAppModel.setKey(key);
        }
        catch (NumberFormatException e) {
            cipherAppView.showDialogError("Something was wrong with your key, try again");
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
     * Calls {@code shiftMessage()} on model and calls method on view responsible to add history entry.
     * @param message message to be processed.
     */
    public void shiftMessage(String message) {
            try {
                cipherAppModel.setMessage(message);
                cipherAppModel.shiftMessage(); 
                cipherAppView.addResult(cipherAppModel.getProcessedMessage());
            }
            catch (InvalidMessageException e) {
                cipherAppView.showDialogError("Something was wrong with your message, try again.");
            }
    }

    /**
     * @return encryption flag.
     */
    public boolean getEncryptionFlag() {
       return cipherAppModel.getEncryptFlag();
    }

    /**
     * @return decryption flag.
     */
    public boolean getDecryptionFlag() {
       return cipherAppModel.getDecryptFlag();
    }

    /**
     * @return message from model.
     */
    public String getMessage() {
       return cipherAppModel.getMessage();
    }
    
    /**
     * @return key from model.
     */
    public int getKey() {
        return cipherAppModel.getEncryptKey();
    }
}
