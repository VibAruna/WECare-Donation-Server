/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Extreme
 */
public class ConnectionControllerImpl extends UnicastRemoteObject implements ConnectionController{
    
    public ConnectionControllerImpl() throws RemoteException{}

    @Override
    public DonationController getDonationController() throws RemoteException {
        return new DonationControllerImpl();
    }

    @Override
    public UserController getUserController() throws RemoteException {
        return new UserControllerImpl();
    }

    @Override
    public DonController getDonController() throws RemoteException {
        return new DonControllerImpl();
    }

    @Override
    public IDController getIDController() throws RemoteException {
        return new IDControllerImpl();
    }
    
}
