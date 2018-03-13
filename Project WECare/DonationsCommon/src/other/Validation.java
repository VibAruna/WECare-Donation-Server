/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 *
 * @author pavilion 15
 */
public class Validation {
    private final static Email EMAIL = new Email();
    private final static Random RANDOM = new Random();
    
    public static void validateNIC(JTextField textField){
        String text = textField.getText();
        
        if (text.length() > 0) {
            if (text.length() > 10) {
                text = text.substring(0, 10);
                textField.setText(text);
            }
            if (!((text.length() == 10 && text.matches("[\\d]{9}[vVxX]") || text.matches("[\\d]{0,9}")))) {
                text = text.replaceAll("[\\D]", "");
                if (text.length() == 10) {
                    text = text.substring(0, 9);
                }
                textField.setText(text);
            }
        }
        
        if (text.length() > 5) {
            String lastDigits = text.substring(0, 2);
            int year = Integer.parseInt("19"+lastDigits);
            int dateOfYear = Integer.parseInt(text.substring(2, 5));
            if (year % 4 == 0 && !(dateOfYear <= 366 || (dateOfYear > 500 && dateOfYear <= 866))) {
                text = lastDigits.concat(text.substring(5, text.length()));
                textField.setText(text);
                textField.setCaretPosition(2);
            } else if (year % 4 != 0 && !(dateOfYear <= 365 || (dateOfYear > 500 && dateOfYear <= 865))) {
                text = lastDigits.concat(text.substring(5, text.length()));
                textField.setText(text);
                textField.setCaretPosition(2);
            }
            
        }
        
    }
    
    public static void validateTelNo(JTextField textField){
        String text = textField.getText();
        if (text.length() > 0) {
            if (text.length() > 10) {
                text = text.substring(0, 10);
                textField.setText(text);
            }
            if (!(text.startsWith("0") && text.matches("[\\d]{0,10}"))) {
                textField.setText(text.replaceAll("[\\D]", ""));
            }
        }
    }
    
    public static void limitCharacterCount(JTextComponent textField, int limit){
        String text = textField.getText();
        if (text.length()>0 && text.length() > limit) {
            textField.setText(text.substring(0, limit));
        }
    }
    
    public static void limitCharacterCount(JPasswordField passwordField, int limit){
        char[] password = passwordField.getPassword();
        if (password.length > 0 && password.length > limit) {
            String newPasword = "";
            for (int i = 0; i < limit; i++) {
                newPasword = newPasword.concat(Character.toString(password[i]));
            }
            passwordField.setText(newPasword);
            
        }
    }
    
    public static void enableDisableButtons(JButton[] buttons, JTextComponent... textComponents){
        boolean areThereEmpty=false;
        for (JTextComponent textComponent : textComponents) {
            if (textComponent.getText().length()==0) {
                areThereEmpty=true;
                break;
            }
        }
        
        for (JButton button : buttons) {
            button.setEnabled(!areThereEmpty);
        }
        
    }
    
    public static void stringsOnly(JTextField textField){
        String txt = textField.getText();
        textField.setText(txt.replaceAll("[\\d]", ""));
    }
    
    public static void noSpace(JTextField textField){
        textField.setText(textField.getText().replaceAll(" ", ""));
    }
    
    public static boolean isMailValid(String mail){
        if (mail.contains("@") && mail.endsWith(".com")) {
            int code = 10000 + RANDOM.nextInt(10000);
            Sending sending = new Sending();
            sending.setVisible(true);
            new Thread(){
                
                @Override
                public void run() {
                    try {
                        EMAIL.send(mail, "Register WECare Account", "Please put this code to complete registration. : "+code);
                    } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(null, "Please Enter a valid Email...", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    sending.dispose();
                }
                
            }.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, null, ex);
            }
            String inp = JOptionPane.showInputDialog(null, "Confirmation Code : ", "Confirm Email", JOptionPane.INFORMATION_MESSAGE);
            return inp == null ? false : inp.equals(Integer.toString(code));
        }
        return false;
    }
}
