package pl.polsl.michal.smaluch.cipher.caesar.model;

import java.util.List;
import java.util.stream.Stream;

/**
 * Model handles the data processing and storing and has all the methods used to
 * process it.
 *
 * @author Michal Smaluch
 * @version 1.0
 */
public class CipherAppModel {

    private String message;
    private String processedMessage;
    private int encryptionKey;
    private int decryptionKey;

    private boolean messageFlag;
    private boolean decryptFlag;
    private boolean encryptFlag;
    private boolean keyFlag;
    private boolean helpFlag;

    /**
     * Single argument constructor, sets default values for private fields.
     *
     */
    public CipherAppModel() {
        message = "";
        messageFlag = false;
        decryptFlag = false;
        encryptFlag = false;
        keyFlag = false;
        helpFlag = false;
        encryptionKey = 0;
        decryptionKey = 26;
    }

    /**
     * Sets encryption and decryption key, limits key to 0 - 25. Sets key flag
     * to true;
     *
     * @param key not processed key (uint).
     * @throws NumberFormatException when cant parse uint.
     */
    public void setKey(String key) throws NumberFormatException {
        this.encryptionKey = Integer.parseUnsignedInt(key) % 26;
        this.decryptionKey = 26 - encryptionKey;

        keyFlag = true;
    }

    /**
     * Returns message flag.
     *
     * @return true/false value of message flag. True if the message is valid
     * and saved.
     */
    public boolean getMessageFlag() {
        return messageFlag;
    }

    /**
     * Returns decrypt flag.
     *
     * @return true/false value of decrypt flag. True if the decryption option
     * was chosen.
     */
    public boolean getDecryptFlag() {
        return decryptFlag;
    }

    /**
     * Returns encrypt flag.
     *
     * @return true/false value of encrypt flag. True if the encryption option
     * was chosen.
     */
    public boolean getEncryptFlag() {
        return encryptFlag;
    }

    /**
     * Returns encryption key.
     *
     * @return encryption key
     */
    public int getEncryptKey() {
        return encryptionKey;
    }

    /**
     * Returns decryption key.
     *
     * @return decryption key.
     */
    public int getDecryptKey() {
        return decryptionKey;
    }

    /**
     * Returns key flag.
     *
     * @return true/false value of key flag. True if the key was provided.
     */
    public boolean getKeyFlag() {
        return keyFlag;
    }

    /**
     * Returns help flag.
     *
     * @return true/false value of help flag. True if the help flag was
     * provided.
     */
    public boolean getHelpFlag() {
        return helpFlag;
    }

    /**
     * Sets encryption flag.
     */
    public void setEncryptFlag() {
        encryptFlag = true;
        decryptFlag = false;
    }

    /**
     * Sets decryption flag.
     */
    public void setDecryptFlag() {
        decryptFlag = true;
        encryptFlag = false;
    }

    /**
     * Method responsible for parsing console arguments.
     *
     * @param args arguments from to be parsed.
     * @throws InvalidOptionException when provided flag was illegal or
     * decryption/encryption was chosen two times.
     * @throws NumberFormatException when provided key was in wrong format
     * (letters or negative value).
     * @throws InvalidMessageException when message contains illegal characters
     * (not English alphabet letters).
     */
    public void parseArguments(List<String> args) throws InvalidOptionException, InvalidMessageException {

        //Exeptions:
        //Wrong flags
        //Wrong characters in message
        //Wrong key (assumption: key cannot be negative)
        for (int i = 0; i < args.size(); i++) {

            if ("-h".equals(args.get(i))) {
                helpFlag = true;
                break;

            } else if ("-k".equals(args.get(i))) {
                //save the key
                setKey(args.get(++i));

            } else if ("-d".equals(args.get(i))) {
                //Only decrypt message
                if (!decryptFlag && !encryptFlag) {
                    decryptFlag = true;
                } else {
                    throw new InvalidOptionException("You already chose decryption/encryption!\n");
                }

            } else if ("-e".equals(args.get(i))) {
                //Only encrypt message
                if (!decryptFlag && !encryptFlag) {
                    encryptFlag = true;
                } else {
                    throw new InvalidOptionException("You already chose decryption/encryption!\n");
                }
            } else if ("-m".equals(args.get(i))) {
                setMessage(args.get(++i));
            } else {
                throw new InvalidOptionException("There is no such flag.\n");
            }
        }
    }

    /**
     * Method responsible for shifting each character "key" times. Uses Stream
     * of Characters.
     */
    public void shiftMessage() {
        StringBuilder processedMessageBuilder = new StringBuilder();

        Stream<Character> charStream = message.chars().mapToObj(c -> (char) c);
        charStream.forEach(character -> {
            if (character != ' ' && character != '\r' && character != '\n') {
                char baseChar = Character.isUpperCase(character) ? 'A' : 'a';
                int originalAlphabetPosition = character - baseChar;
                int newAlphabetPosition = (originalAlphabetPosition + (encryptFlag ? encryptionKey : decryptionKey)) % 26;
                char newCharacter = (char) (baseChar + newAlphabetPosition);
                processedMessageBuilder.append(newCharacter);
            } else {
                processedMessageBuilder.append(character);
            }
        });
        setProcessedMessage(processedMessageBuilder.toString());

    }

    /**
     * Saves message in the model after verification.
     *
     * @param message message to be saved.
     * @throws InvalidMessageException when message contains illegal characters
     * (not English alphabet letters).
     */
    public void setMessage(String message) throws InvalidMessageException {
        try {
            if (message.length() == 0) {
                throw new InvalidMessageException();
            }
        } catch (NullPointerException e) {
            throw new InvalidMessageException();
        }

        for (char character : message.toCharArray()) {
            if (character != ' ' && character != '\r' && character != '\n' && (character < 'A' || (character > 'Z' && character < 'a') || character > 'z')) {
                throw new InvalidMessageException();
            }
        }

        this.message = message;
        messageFlag = true;
    }

    /**
     * Sets processed message
     *
     * @param processedMessage processed message.
     */
    private void setProcessedMessage(String processedMessage) {
        this.processedMessage = processedMessage;
    }

    /**
     * Returns saved message.
     *
     * @return saved message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns processed message.
     *
     * @return processed message.
     */
    public String getProcessedMessage() {
        return processedMessage;
    }
}
