package pl.polsl.michal.smaluch.cipher.caesar.model;

import pl.polsl.michal.smaluch.cipher.caesar.controller.CipherAppController;

/**
 * Model handles the data processing and storing and has all the methods used to
 * process it.
 *
 * @author Michal Smaluch
 * @version 1.0
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

    /**
     * Single argument constructor, sets default values for private fields.
     *
     * @param cipherAppController reference to {@link CipherAppController}.
     */
    public CipherAppModel(CipherAppController cipherAppController) {
        this.cipherAppController = cipherAppController;
        messageFlag = false;
        decryptFlag = false;
        encryptFlag = false;
        keyFlag = false;
        helpFlag = false;
    }

    /**
     * Sets encryption and decryption key, limits key to 0 - 25. Sets key flag
     * to true;
     *
     * @param key not processed key.
     */
    public void setKey(int key) {
        this.encryptionKey = key % 26;
        this.decryptionKey = 26 - (key % 26);

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
    }

    /**
     * Sets decryption flag.
     */
    public void setDecryptFlag() {
        decryptFlag = true;
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
    public void parseArguments(String[] args) throws InvalidOptionException, NumberFormatException, InvalidMessageException {

        //Exeptions:
        //Wrong flags
        //Wrong characters in message
        //Wrong key (assumption: key cannot be negative)
        for (int i = 0; i < args.length; i++) {

            if ("-h".equals(args[i])) {
                helpFlag = true;
                break;

            } else if ("-k".equals(args[i])) {
                //save the key
                setKey(Integer.parseUnsignedInt(args[++i]));

            } else if ("-d".equals(args[i])) {
                //Only decrypt message
                if (!decryptFlag && !encryptFlag) {
                    decryptFlag = true;
                } else {
                    throw new InvalidOptionException("You already chose decryption/encryption!\n");
                }

            } else if ("-e".equals(args[i])) {
                //Only encrypt message
                if (!decryptFlag && !encryptFlag) {
                    encryptFlag = true;
                } else {
                    throw new InvalidOptionException("You already chose decryption/encryption!\n");
                }
            } else if ("-m".equals(args[i])) {
                setMessage(args[++i]);
            } else {
                throw new InvalidOptionException("There is no such flag.\n");
            }
        }
    }

    /**
     * Method responsible for shifting each character "key" times.
     */
    public void shiftMessage() {
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

    /**
     * Saves message in the model after verification.
     *
     * @param message message to be saved.
     * @throws InvalidMessageException when message contains illegal characters
     * (not English alphabet letters).
     */
    public void setMessage(String message) throws InvalidMessageException {
        if (message.length() == 0) {
            throw new InvalidMessageException();
        }
        for (char character : message.toCharArray()) {
            if (character != ' ' && (character < 'A' || (character > 'Z' && character < 'a') || character > 'z')) {
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
