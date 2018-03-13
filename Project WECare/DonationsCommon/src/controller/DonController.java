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
import java.util.ArrayList;
import model.DonModel;
import observer.DonationObserver;

/**
 *
 * @author Extreme
 */
public interface DonController extends Remote{
    boolean donate(DonModel donModel) throws RemoteException, IOException, ClassNotFoundException, SQLException;
    ArrayList<DonModel> getAllDoantions() throws RemoteException, IOException, ClassNotFoundException, SQLException;
    ArrayList<DonModel> getAllDoantions(String category) throws RemoteException, IOException, ClassNotFoundException, SQLException;
    ArrayList<DonModel> getNotReqDoantions(String nic) throws RemoteException, IOException, ClassNotFoundException, SQLException;
    ArrayList<DonModel> getReqDoantions(String nic) throws RemoteException, IOException, ClassNotFoundException, SQLException;
    void addDonationObserver(DonationObserver observer) throws RemoteException;
    boolean requestDonation(String donId, String nic) throws RemoteException, IOException, ClassNotFoundException, SQLException;
    boolean cancelRequest(String donId, String nic) throws RemoteException, IOException, ClassNotFoundException, SQLException;
}
