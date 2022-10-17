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
    
    public enum MissingValue{
        MESSAGE, KEY, OPTION
    }
    
    public void askUser(MissingValue missingValue){
        switch(missingValue){
            case MESSAGE -> printMessage("Please provide a message: ");
            case KEY -> printMessage("Please provide a key: ");
            case OPTION -> printMessage("Select if you want to decrypt or encrypt [d/e]:");
        }
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
    
    public void printMessage(Exception exception){
        System.out.println(exception);
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
                      
                      Message can contain only English alphabet letters.
                      It's possible to run program without any flags
                      """;
        printMessage(help);
    }
    
    public String getInput(){
        return scanner.next();
    }
}