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
   
        //User input
        cipherAppController.askUser();
        
        //applying cipher and displaying results 
        cipherAppController.shiftMessage();
        cipherAppController.printOutput();
    }
}