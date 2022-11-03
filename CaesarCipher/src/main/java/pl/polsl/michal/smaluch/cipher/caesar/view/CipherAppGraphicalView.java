/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.michal.smaluch.cipher.caesar.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import pl.polsl.michal.smaluch.cipher.caesar.controller.CipherAppController;

/**
 * A class that handles the GUI.
 * 
 * @author drago
 */
public class CipherAppGraphicalView extends JPanel
                                    implements ActionListener{
    
    private final CipherAppController cipherAppController;
    
    public CipherAppGraphicalView(CipherAppController cipherAppController) {
        super(new BorderLayout());
        this.cipherAppController = cipherAppController;
        
        JRadioButton encryptOptionButton = new JRadioButton("Enrcypt");
        JRadioButton decryptOptionButton = new JRadioButton("Decrypt");
        encryptOptionButton.setSelected(true);
        
        encryptOptionButton.addActionListener(this);
        decryptOptionButton.addActionListener(this);
        
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        ButtonGroup optionGroup = new ButtonGroup();
        optionGroup.add(encryptOptionButton);
        optionGroup.add(decryptOptionButton);
        
        JTextField messageField = new JTextField();
        
        JPanel appPanel = new JPanel(new GridLayout(1, 0));
        appPanel.add(messageField);
        appPanel.add(encryptOptionButton);
        appPanel.add(decryptOptionButton);
        appPanel.add(submitButton);
        
        add(appPanel, BorderLayout.LINE_START);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println("Button Clicked");
    }
    
}
