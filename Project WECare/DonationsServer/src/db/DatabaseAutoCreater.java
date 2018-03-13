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
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;



/**
 *
 * @author pavilion 15
 */
public class DatabaseAutoCreater {
    private static int rowCount;
    public static boolean createDatabase() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
        String password = DBConnection.savePassword();
        if (password != null) {
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost", "root", password);
            String sql = "create database Donation";
            Statement statement = connection.createStatement();
            int res = statement.executeUpdate(sql);
            if (res > 0) {
                File file = new File("./src/db/DonationDatabase.txt");
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                Connection connection1 = DBConnection.getDBConnection();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    Statement statement1 = connection1.createStatement();
                    rowCount += statement1.executeUpdate(line);
                }
            }
        }
          
        JOptionPane.showMessageDialog(null, "Database Created Sucessfully. Your default password is 'management'. You can change it after login.");
        
        return rowCount >= 5;
    }
}
