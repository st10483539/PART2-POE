/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe0;

/**
 *
 * @author RC_Student_lab
 */

import javax.swing.JOptionPane;



public class UserPortal {
    private final POE0 poe;
    
    public UserPortal(POE0 poe) {
        this.poe = poe;
    }

    public void startMessaging() {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat, " + poe.firstName + "!");
        
        while (true) {
            String[] options = {"Send Message", "View Stats", "Exit"};
            int choice = JOptionPane.showOptionDialog(null,
                "Main Menu", "Choose Option",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
            
            switch (choice) {
                case 0:
                    sendMessage();
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null,
                        "Total messages sent: " + MessageService.getTotalMessagesSent());
                    break;
                case 2:
                    return;
                default:
                    return;
            }
        }
    }
    
    private void sendMessage() {
        String recipient = JOptionPane.showInputDialog("Enter recipient phone number (+country code):");
        if (recipient == null) return;
        
        String content = JOptionPane.showInputDialog("Enter message (max 250 chars):");
        if (content == null) return;
        
        while (content.length() > 250) {
            content = JOptionPane.showInputDialog("Message too long. Enter message (max 250 chars):");
            if (content == null) return;
        }
        
        MessageService.Message message = new MessageService.Message(recipient, content);
        
        while (!message.checkRecipientCell()) {
            recipient = JOptionPane.showInputDialog("Invalid number format. Enter with country code:");
            if (recipient == null) return;
            message = new MessageService.Message(recipient, content);
        }
        
        String result = message.sendMessage();
        JOptionPane.showMessageDialog(null, result);
        
        if (result.contains("sent")) {
            message.printMessageDetails();
        }
    }
}