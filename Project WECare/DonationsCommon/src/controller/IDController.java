/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 *
 * @author Extreme
 */
public interface IDController extends Remote{
    String getLastID(String table, String column) throws RemoteException, IOException, ClassNotFoundException, SQLException;
}
