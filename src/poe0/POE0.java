/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poe0;

/**
 *
 * @author RC_Student_lab
 */


import javax.swing.JOptionPane;

public class POE0 {
    // User details
    public String username, password, firstName, lastName, cellphone;

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public void checkUsername() {
        while (!checkUserName(username)) {
            JOptionPane.showMessageDialog(null,
                "Username is incorrectly formatted.\nPlease ensure that your username contains an underscore and is no more than five characters in length.");
            username = JOptionPane.showInputDialog("Enter username again:");
        }
        JOptionPane.showMessageDialog(null, "Username successfully captured.");
    }

    public boolean checkPasswordComplexity() {
        boolean hasUppercase = false, hasDigit = false, hasSpecialChar = false;
        String specialChars = "~!@#$%^&*()_+=<>?/{}[]|;:'\",.";

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) hasUppercase = true;
            if (Character.isDigit(ch)) hasDigit = true;
            if (specialChars.contains(String.valueOf(ch))) hasSpecialChar = true;
        }

        return password.length() >= 8 && hasUppercase && hasDigit && hasSpecialChar;
    }

    public boolean checkPassword(String password) {
        this.password = password;
        return checkPasswordComplexity();
    }

    public boolean checkCellPhoneNumber() {
        return cellphone.matches("^\\+\\d{1,3}\\d{7,10}$");
    }

    public String registerUser() {
        firstName = JOptionPane.showInputDialog("Enter first name:");
        lastName = JOptionPane.showInputDialog("Enter last name:");
        username = JOptionPane.showInputDialog("Enter username:");
        checkUsername();

        password = JOptionPane.showInputDialog("Enter password:");
        while (!checkPassword(password)) {
            JOptionPane.showMessageDialog(null,
                "Password is not correctly formatted;\nplease ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            password = JOptionPane.showInputDialog("Enter password again:");
        }
        JOptionPane.showMessageDialog(null, "Password successfully captured.");

        cellphone = JOptionPane.showInputDialog("Enter cellphone number with country code (e.g. +271234567890):");
        while (!checkCellPhoneNumber()) {
            JOptionPane.showMessageDialog(null,
                "Cell number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
            cellphone = JOptionPane.showInputDialog("Enter cellphone number again:");
        }
        JOptionPane.showMessageDialog(null, "Cell number successfully captured.");

        return "User registered successfully.";
    }

    public boolean loginUser(String inputUsername, String inputPassword) {
        return username != null && username.equals(inputUsername) && 
               password != null && password.equals(inputPassword);
    }

    public static void main(String[] args) {
        var poe = new POE0();
        poe.registerUser();
        
        int attempts = 0;
        boolean loggedIn = false;
        while (attempts < 3 && !loggedIn) {
            String username = JOptionPane.showInputDialog("Enter username:");
            String password = JOptionPane.showInputDialog("Enter password:");
            
            if (poe.loginUser(username, password)) {
                loggedIn = true;
                UserPortal portal = new UserPortal(poe);
                portal.startMessaging();
            } else {
                attempts++;
                JOptionPane.showMessageDialog(null, "Invalid credentials. Attempts left: " + (3 - attempts));
            }
        }
        
        if (!loggedIn) {
            JOptionPane.showMessageDialog(null, "Too many failed attempts. Exiting.");
        }
    }
}