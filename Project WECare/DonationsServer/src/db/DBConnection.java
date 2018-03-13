/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Extreme
 */
public class DBConnection {
    public static String getPassword() throws FileNotFoundException, IOException {
        File passwordTxt = new File("./src/db/mysqlPassword.txt");
        if (passwordTxt.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(passwordTxt));
            return bufferedReader.readLine();
        } else {
            return savePassword();
        }

    }

    public static String savePassword() throws IOException {
        File passwordTxt = new File("./src/db/mysqlPassword.txt");
        String password = JOptionPane.showInputDialog("MySQL password : ");
        FileWriter fileWriter = new FileWriter(passwordTxt);
        fileWriter.write(password);
        fileWriter.flush();
        fileWriter.close();
        return password;
    }
    
    public static Connection getDBConnection() throws ClassNotFoundException, IOException, SQLException{
        String mysqlPassword = getPassword();
        if (mysqlPassword!=null) {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Donation", "root", "mysql");  
            return conn;
        }
        return null;
    }
}
