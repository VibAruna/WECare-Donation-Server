/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import view.Home;

/**
 *
 * @author Extreme
 */
public class DonationObserverImpl extends UnicastRemoteObject implements DonationObserver{
    private Home home;
    
    public DonationObserverImpl(Home home) throws RemoteException{
        this.home = home;
    }
    
    @Override
    public void notifyObserver() throws RemoteException {
        home.update();
    }
    
}
