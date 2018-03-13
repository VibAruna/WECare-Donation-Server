/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Extreme
 */
public class ManagementController {    
    private static Connection connection;
    
    private static void getConnection() throws ClassNotFoundException, IOException, SQLException{
        if (connection == null) {
            connection = DBConnection.getDBConnection();
        }
    }
    
    public static boolean changePassword(String newPassword) throws ClassNotFoundException, IOException, SQLException{
        String sql = "update management set pass = ? where num = 1";
        getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, newPassword);
        return statement.executeUpdate() > 0;
    }
    
    public static String getPassword() throws SQLException, ClassNotFoundException, IOException{
        String sql = "select pass from management";
        getConnection();
        Statement statement = connection.createStatement();
        ResultSet executeQuery = statement.executeQuery(sql);
        if (executeQuery.next()) {
            return executeQuery.getString(1);
        }
        return  "";
    }
}
