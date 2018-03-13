/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import controller.ConnectionControllerImpl;
import controller.DonControllerImpl;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import view.ChooseRecipients;
import view.ViewUserDetails;

/**
 *
 * @author Extreme
 */
public class StartMain {
    
    public static void main(String[] args) {
        try {
            Registry donationRegistry = LocateRegistry.createRegistry(5050);
            System.out.println("Server is starting...");
            donationRegistry.rebind("DonationServer", new ConnectionControllerImpl());
            System.out.println(Inet4Address.getLocalHost().getHostAddress().trim());
            DonControllerImpl.autoChooseRecipient();
            
            UIManager.setLookAndFeel(new GraphiteLookAndFeel());
            
            donationRegistry.unbind("DonationServer");
            UnicastRemoteObject.unexportObject(donationRegistry, true);
        } catch (RemoteException ex) {
            Logger.getLogger(StartMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(StartMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(StartMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(StartMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new ViewUserDetails().setVisible(true);
        new ChooseRecipients().setVisible(true);
    }
}
