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
            cipherAppView.printHelp();
            cipherAppView.printMessage("Something was wrong with your arguments input, program will ask you for missing ones if there are any.");
            cipherAppView.printMessage(e);
        }
    }
    
    public void printHelp(){
        cipherAppView.printHelp();
    }
    
    public boolean getHelpFlag(){
        return cipherAppModel.getHelpFlag();
    }
    
    public void askUser(){
        while (!cipherAppModel.getDecryptFlag() && !cipherAppModel.getEncryptFlag()){
            cipherAppView.askUser(CipherAppView.MissingValue.OPTION);
            String choice = cipherAppView.getInput();
            if("d".equals(choice)){
                cipherAppModel.setDecryptFlag();
            }else if("e".equals(choice)){
                cipherAppModel.setEncryptFlag();
            }
        }
        
        while (!cipherAppModel.getMessageFlag()){
            cipherAppView.askUser(CipherAppView.MissingValue.MESSAGE);
            String message = cipherAppView.getInput();
            try{
                cipherAppModel.setMessage(message);
            }catch(InvalidMessageException e){
                cipherAppView.printMessage("Something was wrong with your message, try again.");
                cipherAppView.printMessage(e);
            }
        }
        
        while (!cipherAppModel.getKeyFlag()){
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

    public void shiftMessage(){
        cipherAppModel.shiftMessage();
    }
    
    public void printOutput(){
        cipherAppView.printOutput(cipherAppModel.getMessage(),cipherAppModel.getProcessedMessage(), true);
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