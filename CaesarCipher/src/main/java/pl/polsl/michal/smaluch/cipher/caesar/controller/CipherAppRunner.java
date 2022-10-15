package pl.polsl.michal.smaluch.cipher.caesar.controller;

/**
 *
 * @author Michal Smaluch
 */
public class CipherAppRunner {
    
    public static void main(String[] args){
        CipherAppController cipherAppController = new CipherAppController();
        
        //Initialization
        cipherAppController.start(args);
        if(cipherAppController.getHelpFlag()){
            cipherAppController.printHelp();
            return;
        }
                
        //UserInput
        //TODO: change it so it only asks for things which weren't provided
        cipherAppController.printIntro();
        cipherAppController.getUserInput();
        
        cipherAppController.shiftMessage();
        cipherAppController.printOutput();
    }
}