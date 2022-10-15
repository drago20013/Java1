package pl.polsl.michal.smaluch.cipher.caesar.controller;

import pl.polsl.michal.smaluch.cipher.caesar.model.CipherAppModel;
import pl.polsl.michal.smaluch.cipher.caesar.model.InvalidMessageException;
import pl.polsl.michal.smaluch.cipher.caesar.model.OptionException;
import pl.polsl.michal.smaluch.cipher.caesar.view.CipherAppView;

/**
 *
 * @author Michal Smaluch
 */
public class CipherAppController {
    
    private CipherAppView cipherAppView;
    private CipherAppModel cipherAppModel;
        
    public void start(String[] args) {
        cipherAppView = new CipherAppView(this);
        cipherAppModel = new CipherAppModel(this);
        
        try{
            cipherAppModel.parseArguments(args);
        }catch(OptionException | NumberFormatException | InvalidMessageException e){
            cipherAppView.printMessage(e.toString());
            cipherAppView.printHelp();
        }
    }
    
    public void printHelp(){
        cipherAppView.printHelp();
    }
    
    public boolean getHelpFlag(){
        return cipherAppModel.getHelpFlag();
    }
    
    public void printIntro(){
        //if(cipherAppModel.getDecryptFlag()
        //check if we want to decrypt or encrypt
        cipherAppView.printIntro(true);
    }

    public void shiftMessage(){
        cipherAppModel.shiftMessage();
    }
    
    public void printOutput(){
        cipherAppView.printOutput(cipherAppModel.getMessage(),cipherAppModel.getProcessedMessage(), true);
    }
    
    public void getUserInput(){
        try{
            cipherAppModel.setMessage(cipherAppView.getInput());
        }catch (InvalidMessageException e){
            cipherAppView.printMessage(e.toString());
        }
    }
    
    public void printEncryptionKey(){
        cipherAppView.printEncryptionKey();
    }
    
    public void printDecryptionKey(){
        cipherAppView.printDecryptionKey();
    }
    
    public int getEncryptionKey(){
        return cipherAppModel.getEncryptionKey();
    }
    
    public int getDecryptionKey(){
        return cipherAppModel.getDecryptionKey();
    }   
}