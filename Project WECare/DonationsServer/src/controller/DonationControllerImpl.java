/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observable;
import model.Donation;
import observer.DonationObserver;
import observerble.DonationObserverble;

/**
 *
 * @author Extreme
 */
public class DonationControllerImpl extends UnicastRemoteObject implements DonationController{
    private final static DonationObserverble OBSERVABLE = new DonationObserverble();
    
    public DonationControllerImpl() throws RemoteException { 
        
    }
    
    @Override
    public void donate(Donation donation) throws RemoteException{
        System.out.println(donation.getName()+ " : " +donation.getDonatin());
        OBSERVABLE.notifyObservers();
    }

    @Override
    public void addObserver(DonationObserver observer) throws RemoteException {
        OBSERVABLE.addObserver(observer);
    }
    
}
