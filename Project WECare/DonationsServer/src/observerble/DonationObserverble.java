/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerble;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Donation;
import observer.DonationObserver;

/**
 *
 * @author Extreme
 */
public class DonationObserverble {
    ArrayList<DonationObserver> observerList = new ArrayList<>();
    
    public void addObserver(DonationObserver observer){
        observerList.add(observer);
    }
    
    public void notifyObservers() throws RemoteException{
        for(DonationObserver donationObserver : observerList){
            
            new Thread() {
                public void run() {
                    try {
                        donationObserver.notifyObserver();
                    } catch (RemoteException ex) {
                        Logger.getLogger(DonationObserverble.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Notified");
                }
            }.start();
        }
    }
}
