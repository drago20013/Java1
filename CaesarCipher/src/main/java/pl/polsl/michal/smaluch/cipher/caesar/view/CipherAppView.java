package pl.polsl.michal.smaluch.cipher.caesar.view;

import java.util.Scanner;
import pl.polsl.michal.smaluch.cipher.caesar.controller.CipherAppController;

/**
 *
 * @author Michal Smaluch
 */
public class CipherAppView {
    private final CipherAppController cipherAppController;
    
    private final Scanner scanner;
    
    public CipherAppView(CipherAppController cipherAppController){
        this.cipherAppController = cipherAppController;
        scanner = new Scanner(System.in).useDelimiter("\\n");
    }
   
    public void printIntro(boolean decryptEncryptFlag){
        String intro = "Hello, please enter message to " + (decryptEncryptFlag? "decrypt" : "encrypt") + " using Caesar Cipher: ";
        
        printMessage(intro);
    }
    
    //TODO: change it so it displays only encrypted or decrypted message
    public void printOutput(String originalMessage, String processedMessage, boolean decryptEncryptFlag){
        printMessage("Here is your result:");
        printMessage("Original: ");
        printMessage(originalMessage);
        printMessage((decryptEncryptFlag ? "Decrypted: " :  "Encrypted: "));
        printMessage(processedMessage);
    }
        
    public void printMessage(String message){
        System.out.println(message);
    }
    
    public void printEncryptionKey(){
        printMessage("Encryption key : " + Integer.toString(cipherAppController.getEncryptionKey()));
    }
    
    public void printDecryptionKey(){
        printMessage("Decryption key: " + Integer.toString(cipherAppController.getDecryptionKey()));
    }
    
    public void printHelp(){
        String help = """
                      Usage:
                      CaesarCipher [-d | -e] [-k <key>] [-m <message>]
                      
                      Options:
                      -d decrypt message
                      -e encrypt message
                      -k set cipher key
                      -m specify message to encrypt/decrypt
                      -h displays help
                      
                      It's possible to run program without any flags
                      """;
        printMessage(help);
    }
    
    public String getInput(){
        return scanner.next();
    }
}