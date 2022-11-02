package pl.polsl.michal.smaluch.cipher.caesar.model.tests;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import pl.polsl.michal.smaluch.cipher.caesar.controller.CipherAppController;
import pl.polsl.michal.smaluch.cipher.caesar.model.CipherAppModel;
import pl.polsl.michal.smaluch.cipher.caesar.model.InvalidMessageException;
import pl.polsl.michal.smaluch.cipher.caesar.model.InvalidOptionException;
/**
 * Class with unit tests used to test public Model methods.
 * @author Michal Smaluch
 */
public class ModelTest {
    CipherAppModel cipherAppModel;
    CipherAppController cipherAppController;
    
    @BeforeEach
    public void setUp() {
        cipherAppController = new CipherAppController();
        cipherAppModel = new CipherAppModel(cipherAppController);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"3", "0"})
    public void setKeyTestWithCorrectValues(String key) {
        try{
            cipherAppModel.setKey(key);
            int encryptionKey = Integer.parseUnsignedInt(key) % 26;
            int decryptionKey = 26 - encryptionKey;
            assertEquals(encryptionKey,cipherAppModel.getEncryptKey(), "Encryption key values ​​are not the same!");
            assertEquals(decryptionKey,cipherAppModel.getDecryptKey(), "Decryption key values ​​are not the same!");
        }catch(NumberFormatException e){
            fail("Exception shouldn't be thrown. " + e);
        }
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"-3", "asd"})
    public void setKeyTestWithIncorrectValues(String key) {
        try{
            cipherAppModel.setKey(key);
            fail("Exception should be thrown.");
        }catch(NumberFormatException e){
        }
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"-d -m message -k 3", "-m message", "-h", "-e", "-k 3 -d -m message"})
    public void parseArgumentsTestWithCorrectValues(String args){
        List<String> argsList = Arrays.asList(args.split(" "));
        try{
            cipherAppModel.parseArguments(argsList);
        }catch(InvalidOptionException | NumberFormatException | InvalidMessageException e){
            fail("Exception shouldn't be thrown. " + e);
        }
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"-d 3 -m message -k 3", "-m 1", "-c", "-x -m message", "-k -3", "-k c"})
    public void parseArgumentsTestWithIncorrectValues(String args){
        List<String> argsList = Arrays.asList(args.split(" "));
        try{
            cipherAppModel.parseArguments(argsList);
            fail("Exception should be thrown.");
        }catch(InvalidOptionException | NumberFormatException | InvalidMessageException e){
        }
    }
    
    @Test
    public void parseArgumentsTestEncryptFlag(){
        try{
            cipherAppModel.parseArguments(Arrays.asList("-e"));
            assertEquals(true, cipherAppModel.getEncryptFlag(), "Encrypt flag was not set.");
            
        }catch(InvalidOptionException | NumberFormatException | InvalidMessageException e){
            fail("Exception shouldn't be thrown. " + e);
        }
    }
    
    @Test
    public void parseArgumentsTestDecryptFlag(){
        try{
            cipherAppModel.parseArguments(Arrays.asList("-d"));
            assertEquals(true, cipherAppModel.getDecryptFlag(), "Decrypt flag was not set.");
            
        }catch(InvalidOptionException | NumberFormatException | InvalidMessageException e){
            fail("Exception shouldn't be thrown. " + e);
        }
    }
    
    @Test
    public void parseArgumentsTestMessage(){
        try{
            cipherAppModel.parseArguments(Arrays.asList("-m message".split(" ")));
            assertEquals(true, cipherAppModel.getMessageFlag(), "Message flag was not set.");
            assertEquals("message", cipherAppModel.getMessage(), "Message is not correct.");
        }catch(InvalidOptionException | NumberFormatException | InvalidMessageException e){
            fail("Exception shouldn't be thrown. " + e);
        }
    }
    
    @Test
    public void parseArgumentsTestKey(){
        try{
            cipherAppModel.parseArguments(Arrays.asList("-k 3".split(" ")));
            assertEquals(true, cipherAppModel.getKeyFlag(), "Key flag was not set.");
            assertEquals(3, cipherAppModel.getEncryptKey(), "Encryption key is wrong.");
            assertEquals(23, cipherAppModel.getDecryptKey(), "Decryption key is wrong.");
        }catch(InvalidOptionException | NumberFormatException | InvalidMessageException e){
            fail("Exception shouldn't be thrown. " + e);
        }
    }

    @Test
    public void shiftMessageTestEncrypt() {
        try{
            cipherAppModel.parseArguments(Arrays.asList("-e -k 3 -m message".split(" ")));
            cipherAppModel.shiftMessage();
            assertEquals("phvvdjh", cipherAppModel.getProcessedMessage(), "Processed message is not the same.");

        }catch(InvalidOptionException | NumberFormatException | InvalidMessageException e){
            fail("Exception shouldn't be thrown. " + e);
        }
    }
    
    @Test
    public void shiftMessageTestDecrypt() {
        try{
            cipherAppModel.parseArguments(Arrays.asList("-d -k 3 -m phvvdjh".split(" ")));
            cipherAppModel.shiftMessage();
            assertEquals("message", cipherAppModel.getProcessedMessage(), "Processed message is not the same.");

        }catch(InvalidOptionException | NumberFormatException | InvalidMessageException e){
            fail("Exception shouldn't be thrown. " + e);
        }
    }
    
    @Test
    public void setMessageTestCorrect(){
        try{
            cipherAppModel.setMessage("message");
            cipherAppModel.setMessage("MeSsAge");
            cipherAppModel.setMessage("message MESSAGE");
        } catch (InvalidMessageException e){
            fail("Exception shouldn't be thrown. " + e);
        }
    }
    
    @Test
    public void setMessageTestIncorrect(){
        try{
            cipherAppModel.setMessage("1message");
            cipherAppModel.setMessage("MeSsAge!");
            cipherAppModel.setMessage("messagę MESSAGĘ");
            fail("Exception should be thrown.");
        } catch (InvalidMessageException e){
            
        }
    }
}
