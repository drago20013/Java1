package pl.polsl.michal.smaluch.cipher.caesar.model;

import pl.polsl.michal.smaluch.cipher.caesar.controller.CipherAppController;

/**
 *
 * @author Michal Smaluch
 */
public class CipherAppModel {
    private final CipherAppController cipherAppController;
    
    private String message;
    private String processedMessage;
    private int encryptionKey;
    private int decryptionKey;
    
    private boolean messageFlag;
    private boolean decryptFlag;
    private boolean encryptFlag;
    private boolean keyFlag;
    private boolean helpFlag;
    
    public CipherAppModel(CipherAppController cipherAppController){
        this.cipherAppController = cipherAppController;
        messageFlag = false;
        decryptFlag = false;
        encryptFlag = false;
        keyFlag = false;
        helpFlag = false;
    }
    
    public void setKey(int key){
        this.encryptionKey = key % 26;
        this.decryptionKey = 26 - (key % 26);
        
        keyFlag=true;
    }
    
    public boolean getMessageFlag(){
        return messageFlag;
    }
    
    public boolean getDecryptFlag(){
        return decryptFlag;
    }
    
    public boolean getEncryptFlag(){
        return encryptFlag;
    }
    
    public boolean getKeyFlag(){
        return keyFlag;
    }
    
    public boolean getHelpFlag(){
        return helpFlag;
    }
    
    public void setEncryptFlag(){
        encryptFlag = true;
    }
    
    public void setDecryptFlag(){
        decryptFlag = true;
    }
    
    public void parseArguments(String[] args) throws OptionException, NumberFormatException, InvalidMessageException {
        
        //Exeptions:
        //Wrong flags
        //Wrong characters in message
        //Wrong key (assumption: key cannot be negative)
        
        for (int i = 0; i < args.length; i++) {
           
            if("-h".equals(args[i])){
                helpFlag = true;
                break;
                
            }else if("-k".equals(args[i])){
                //save the key
                setKey(Integer.parseUnsignedInt(args[++i]));

            }else if("-d".equals(args[i])){
                //Only decrypt message
                if(!decryptFlag && !encryptFlag)
                    decryptFlag=true;
                else
                    throw new OptionException("baaaka illegal flag used!\n");
                
            }else if("-e".equals(args[i])){
                //Only encrypt message
                 if(!decryptFlag && !encryptFlag)
                    encryptFlag=true;
                else
                    throw new OptionException("baaaka illegal flag used!\n");
            }else if("-m".equals(args[i])){
                    setMessage(args[++i]);
            }else{
                //throw exception, catch it in controller then display help but continue running;
                throw new OptionException("baaaka illegal flag used!\n");
            }
        }
    }
     
    public void shiftMessage(){
        StringBuilder processedMessageBuilder = new StringBuilder();
        for (char character : message.toCharArray()) {
            if (character != ' ') {
                char baseChar = Character.isUpperCase(character) ? 'A' : 'a';
                int originalAlphabetPosition = character - baseChar;
                int newAlphabetPosition = (originalAlphabetPosition + (encryptFlag ? encryptionKey : decryptionKey)) % 26;
                char newCharacter = (char) (baseChar + newAlphabetPosition);
                processedMessageBuilder.append(newCharacter);
            } else {
                processedMessageBuilder.append(character);
            }
        }
        setProcessedMessage(processedMessageBuilder.toString());
    }
    
    public void setMessage(String message) throws InvalidMessageException{
        for (char character : message.toCharArray()) {
            if (character != ' ' && (character < 'A' || (character > 'Z' && character < 'a') || character > 'z' )) {
                throw new InvalidMessageException();
            }
        }
        this.message = message;
        messageFlag = true;
    }
    
    private void setProcessedMessage(String processedMessage){
        this.processedMessage = processedMessage;
    }
    
    public String getMessage(){
        return message;
    }
    
    public String getProcessedMessage(){
        return processedMessage;
    }
    
    public int getEncryptionKey(){
        return encryptionKey;
    }   
    
    public int getDecryptionKey(){
        return decryptionKey;
    }  
}