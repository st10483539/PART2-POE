/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe0;

/**
 *
 * @author RC_Student_lab
 */






import javax.swing.*;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MessageService {
    private static int totalMessagesSent = 0;

    public static final class Message {
        private final String messageId;
        private final int messageNumber;
        private final String recipient;
        private final String messageContent;
        private final String messageHash;

        public Message(String recipient, String messageContent) {
            this.messageId = generateMessageId();
            this.messageNumber = ++totalMessagesSent;
            this.recipient = recipient;
            this.messageContent = messageContent;
            this.messageHash = createMessageHash();
        }

        private String generateMessageId() {
            return String.format("%010d", new Random().nextInt(1_000_000_000));
        }

        public boolean checkRecipientCell() {
            return recipient != null && recipient.matches("^\\+\\d{1,3}\\d{7,10}$");
        }

        public String createMessageHash() {
            if (messageContent == null || messageContent.isEmpty()) {
                return "00:00EMPTY";
            }
            
            String[] words = messageContent.split("\\s+");
            String firstWord = words.length > 0 ? words[0] : "";
            String lastWord = words.length > 1 ? words[words.length-1] : firstWord;
            
            return String.format("%s:%d%s%s", 
                messageId.substring(0, 2),
                messageNumber,
                firstWord.toUpperCase(),
                lastWord.toUpperCase());
        }

        public String sendMessage() {
            Object[] options = {"Send Message", "Disregard Message", "Store Message"};
            int choice = JOptionPane.showOptionDialog(null, 
                "Message Options", 
                "Choose Action", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                options, 
                options[0]);
            
            switch(choice) {
                case 0: // Send
                    storeMessage();
                    return "Message successfully sent.";
                case 1: // Disregard
                    totalMessagesSent--;
                    return "Message disregarded.";
                case 2: // Store
                    storeMessage();
                    return "Message stored for later.";
                default:
                    return "Operation cancelled.";
            }
        }

        @SuppressWarnings("unchecked")
        public void storeMessage() {
            JSONObject messageJson = new JSONObject();
            messageJson.put("messageId", messageId);
            messageJson.put("messageNumber", messageNumber);
            messageJson.put("recipient", recipient);
            messageJson.put("messageContent", messageContent);
            messageJson.put("messageHash", messageHash);
            
            try (FileWriter file = new FileWriter("messages.json", true)) {
                file.write(messageJson.toJSONString() + "\n");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving message: " + e.getMessage());
            }
        }

        public void printMessageDetails() {
            String details = String.format(
                "Message Details:\nID: %s\nNumber: %d\nRecipient: %s\nMessage: %s\nHash: %s",
                messageId, messageNumber, recipient, messageContent, messageHash);
            JOptionPane.showMessageDialog(null, details);
        }
    }

    public static int getTotalMessagesSent() {
        return totalMessagesSent;
    }
}