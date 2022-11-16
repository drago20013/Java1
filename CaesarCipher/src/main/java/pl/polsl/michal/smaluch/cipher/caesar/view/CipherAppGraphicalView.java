package pl.polsl.michal.smaluch.cipher.caesar.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import pl.polsl.michal.smaluch.cipher.caesar.controller.CipherAppControllerGUI;

/**
 * A class that handles the GUI.
 * 
 * @author Michal Smaluch
 * @version 1.0
 */
public class CipherAppGraphicalView extends JPanel
                                    implements ActionListener{
    
    private JSplitPane appSplitPane;
    private JScrollPane appPanelDown;  
    private JTextArea messageField;
    private JTable appTable;
    private JComboBox keyCombo;
    private JPanel appPanelUp;
    private JRadioButtonMenuItem rbMenuItemEnc;
    private JRadioButtonMenuItem rbMenuItemDec;
    private JFrame appFrame;
    private CipherAppControllerGUI cipherAppController;
    
    /**
     * One argument constructor.
     */
    public CipherAppGraphicalView(){}
    
    /**
     * One parameter constructor, creates both panels and connect them.
     * @param controller controller reference used in action listener.
     */
    public CipherAppGraphicalView(CipherAppControllerGUI controller) {  
        super(new BorderLayout());
        cipherAppController = controller;
        
        Border padding = BorderFactory.createEmptyBorder(20,20,20,20);
        Dimension mainMinimumSize = new Dimension(500, 300);
        
        appPanelUp = createUpPanel();
        appPanelDown = createDownPanel();
       
        appPanelUp.setBorder(padding);
        appPanelDown.setBorder(padding);

        appSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
                
        appSplitPane.setDividerLocation(120);
        appSplitPane.add(appPanelUp);
        appSplitPane.add(appPanelDown);
        
        add(appSplitPane, BorderLayout.CENTER);
        setMinimumSize(mainMinimumSize);
    }
    
    /**
     * Method responsible for creation of upper JPanel, containing message input, key selection and convert button.
     * @return created JPanel
     */
    private JPanel createUpPanel(){
        JPanel appPanel = new JPanel();
        Dimension minimumUpSize = new Dimension(200, 120);
        JButton submitButton =  new JButton("Convert");
        JLabel messageLabel = new JLabel("Message:");
        messageField = new JTextArea(4, 20); 
        JScrollPane messageScroll = new JScrollPane(messageField);
        JLabel keyLabel = new JLabel("Key:");
        String[] possibleKeys = { "0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26" };
        keyCombo = new JComboBox(possibleKeys);

        keyCombo.setActionCommand("key");
        submitButton.setActionCommand("convert");
        submitButton.addActionListener(this);
        keyCombo.addActionListener(this);
        
        messageField.setLineWrap(true);
        
        appPanel.setMinimumSize(minimumUpSize);
        appPanel.add(messageLabel);
        appPanel.add(messageScroll);
        appPanel.add(keyLabel);
        appPanel.add(keyCombo);
        appPanel.add(submitButton);
        return appPanel;
    }
    
    /**
     * Method responsible for creation of lower JPanel, containing table used for history of operations.
     * @return JScrollPane with the table.
     */
    private JScrollPane createDownPanel(){
        String[] columnNames = {"Operation",
                                "Message",
                                "Key",
                                "Output"};
        appTable = new JTable(new MyTableModel(columnNames, 0));
        appTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        appTable.setCellSelectionEnabled(true);
        return new JScrollPane(appTable);
    }
    
    /**
     * Method showing message dialog with the help message.
     */
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
        
       showDialog(help);
    }
    
    /**
     * Method responsible for showing message dialog.
     * @param message message to be showed
     */
    public void showDialog(String message){        
        JOptionPane.showMessageDialog(appFrame,
                message, "Info",JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Method responsible for showing error message dialog.
     * @param message message to be showed
     */
    public void showDialogError(String message){        
        JOptionPane.showMessageDialog(appFrame,
                message, "Error",JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Method used to add new row to the history table
     * @param processedMessage processed message from model.
     */
    public void addResult(String processedMessage){
        DefaultTableModel model = (DefaultTableModel) appTable.getModel();
        String operation = (rbMenuItemEnc.isSelected() ? "Encryption" : "Decryption");
        model.addRow(new Object[]{operation, messageField.getText(), keyCombo.getSelectedItem(), processedMessage});
    }
    
    /**
     * Method which sets initial values if they were passed as run arguments.
     */
    public void setInit(){    
        messageField.setText(cipherAppController.getMessage());
        keyCombo.setSelectedIndex(cipherAppController.getKey());
    }
    
    /**
     * GUIView Action Listener method
     * @param ae Action Event
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
       switch (ae.getActionCommand()) {
            case "quit":
                System.exit(0);
            case "encrypt":
                //SET OERATION TO ENCRYPTION
                cipherAppController.setEncryption();
                break;
            case "decrypt":
                //SET OERATION TO DECRYPTION
                cipherAppController.setDecryption();
                break;
            case "convert":
                //convert
                cipherAppController.shiftMessage(messageField.getText());
                break;
            case "key":
                //change key
                String keyVal = (String)keyCombo.getSelectedItem();
                cipherAppController.setKey(keyVal);
                break;
            default:
                System.out.println("Button Clicked");
                break;
        }
    }
    
    /**
     * Method responsible for creation of a menu.
     * @return Created JMenuBar.
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        ButtonGroup group = new ButtonGroup();

        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        rbMenuItemEnc = new JRadioButtonMenuItem("Encrypt");
        rbMenuItemEnc.setMnemonic(KeyEvent.VK_E);
        rbMenuItemEnc.setActionCommand("encrypt");
        rbMenuItemEnc.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_E, ActionEvent.ALT_MASK));
        group.add(rbMenuItemEnc);
        rbMenuItemEnc.addActionListener(this);
        menu.add(rbMenuItemEnc);

        rbMenuItemDec = new JRadioButtonMenuItem("Decrypt");
        rbMenuItemDec.setMnemonic(KeyEvent.VK_D);
        rbMenuItemDec.setActionCommand("decrypt");
        rbMenuItemDec.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_D, ActionEvent.ALT_MASK));
        group.add(rbMenuItemDec);
        rbMenuItemDec.addActionListener(this);
        
        if(cipherAppController.getEncryptionFlag())
            rbMenuItemEnc.setSelected(true);
        else if(cipherAppController.getDecryptionFlag())
            rbMenuItemDec.setSelected(true);
        else{
            rbMenuItemEnc.setSelected(true);
            cipherAppController.setEncryption();
        }
        
        menu.add(rbMenuItemDec);

        JMenuItem menuItem = new JMenuItem("Quit");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        return menuBar;
    }
    
    /**
     * Method which creates main frame, sets its options and adds view Panel to it.
     */
    public void createAndShowGUI() {        
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("Caesar Cipher App");
        this.appFrame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        frame.setJMenuBar(createMenuBar());
       
        setOpaque(true); 
        frame.setContentPane(this);

        frame.setVisible(true);
    }
    
    /**
     * Custom table model class, used only to specify which cells are editable. (For visibility reasons)
     */
    class MyTableModel extends DefaultTableModel {
        
        /** Two argument constructor
         * 
         * @param columnNames column names
         * @param rowCount start row count
         */
        MyTableModel(Object[] columnNames, int rowCount){
            super(columnNames, rowCount);
        }
        
        /** Override method used to set which cells are editable
         * @true when cell is editable
         * @param row nr
         * @param col nr
         */
        @Override
        public boolean isCellEditable(int row, int col) {
            return (col == 1 || col == 3);
        }
    }
}
