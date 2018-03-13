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
import model.UserModel;

/**
 *
 * @author Extreme
 */
public interface UserController extends Remote{
    boolean addNewUser(UserModel user) throws RemoteException, IOException, ClassNotFoundException, SQLException;
    boolean updateUser(UserModel user) throws RemoteException, IOException, ClassNotFoundException, SQLException;
    UserModel getUser(String nic) throws RemoteException, IOException, ClassNotFoundException, SQLException;
}
