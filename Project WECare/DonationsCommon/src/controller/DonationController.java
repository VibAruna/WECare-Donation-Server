/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.Donation;
import observer.DonationObserver;

/**
 *
 * @author Extreme
 */
public interface DonationController extends Remote{
    void donate(Donation donation) throws RemoteException;
    void addObserver(DonationObserver observer) throws RemoteException;
}
