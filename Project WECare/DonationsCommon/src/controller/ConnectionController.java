/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Extreme
 */
public interface ConnectionController extends Remote{
    DonationController getDonationController() throws RemoteException;
    UserController getUserController() throws RemoteException;
    DonController getDonController() throws RemoteException;
    IDController getIDController() throws RemoteException;
}
