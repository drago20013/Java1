package pl.polsl.michal.smaluch.cipher.caesar.model;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.junit.platform.commons.util.StringUtils;
/**
 *
 * @author Michal Smaluch
 */
public class ModelTest {
    
    @ParameterizedTest
    @CsvSource({"3", "-6", "0"})
    public void setKeyTest(int key) {

    }
    
    @Test
    public void getMessageFlagTest() {
      
    }
    
    @Test
    public void getDecryptFlagTest() {
       
    }
    
    @Test
    public void getEncryptFlagTest() {
        
    }

    @Test
    public void getKeyFlagTest() {

    }

    @Test
    public void getHelpFlagTest() {
  
    }

    @Test
    public void setEncryptFlagTest() {

    }

    @Test
    public void setDecryptFlagTest() {
    
    }
    
    @ParameterizedTest
    @CsvSource({"test,TEST, sdf", "tEst,TEST, xzcv", "Java,JAVA, oih"})
    public void parseArgumentsTest(String message, String key, String option){
        //throws InvalidOptionException, NumberFormatException, InvalidMessageException 
        //TODO: Change it to MethodSource
    }

    @Test
    public void shiftMessageTest() {

    }
    
    @ParameterizedTest
    @CsvSource({"test,TEST", "tEst,TEST", "Java,JAVA"})
    public void setMessageTest(String message){
        // throws InvalidMessageException 
    }
    
    @ParameterizedTest
    @CsvSource({"test", "tEst", "Java"})
    private void setProcessedMessageTest(String processedMessage) {

    }

    @Test
    public void getMessageTest() {

    }

    @Test
    public void getProcessedMessageTest() {

    }
}
