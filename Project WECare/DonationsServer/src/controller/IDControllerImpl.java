/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.DBConnection;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Extreme
 */
public class IDControllerImpl extends UnicastRemoteObject implements IDController{
    
    public IDControllerImpl() throws RemoteException{}

    @Override
    public String getLastID(String table, String column) throws RemoteException, IOException, ClassNotFoundException, SQLException {
        String sql="select "+ column +" from "+table+" order by "+column+" desc limit 1";
        Connection connection = DBConnection.getDBConnection();
        Statement statement=connection.createStatement();
        ResultSet executeQuery = statement.executeQuery(sql);
        if(executeQuery.next()){
            return executeQuery.getString(column);
        }
        return null;
    }
    
}
