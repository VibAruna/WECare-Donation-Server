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
public interface RequestController extends Remote{
    void request(String nic, String DonId) throws RemoteException;
    void removeRequest(String nic, String DonId) throws RemoteException;
}
