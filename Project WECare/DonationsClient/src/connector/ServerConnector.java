/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connector;

import controller.ConnectionController;
import controller.DonController;
import controller.DonationController;
import controller.IDController;
import controller.UserController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;
import view.ConnectError;
import view.getIPPort;

/**
 *
 * @author Extreme
 */
public class ServerConnector {
    private ConnectionController connectionController;
    private final DonationController donationController;
    private final UserController userController;
    private final DonController donController;
    private final IDController idController;
    private static ServerConnector connector;
    private String ipAddress;
    private String port;
    
    private ServerConnector() throws NotBoundException, MalformedURLException, RemoteException{
        getConnection();
        donationController = connectionController.getDonationController();
        userController = connectionController.getUserController();
        donController = connectionController.getDonController();
        idController = connectionController.getIDController();
    }
    
    public static ServerConnector getServerConnector() throws NotBoundException, MalformedURLException, RemoteException{
        if(connector == null){
            connector = new ServerConnector();
        }
        return connector;
    }
    
    public DonationController getDonationController(){
        return donationController;
    }
    
    public UserController getUserController(){
        return userController;
    }
    
    public DonController getDonController(){
        return donController;
    }
    
    public IDController getIDController(){
        return idController;
    }

    private void getConnection() throws NotBoundException, MalformedURLException, RemoteException {
        try{
            if (ipAddress == null || port == null) {
                getIpandPort();
            }else{
                writeIpPort();
            }
            
            connectionController = (ConnectionController) Naming.lookup("rmi://"+ipAddress+":"+port+"/DonationServer");
        } catch (IOException ex){
            ConnectError connectError = new ConnectError(null, true, ipAddress, port);
            connectError.setVisible(true);
            ipAddress = connectError.getIP();
            port = connectError.getPort();
            getConnection();
        }
    }
    
    private void getIpandPort() throws IOException{
        File file = new File("./src/other/conncetion.txt");
        if (file.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String[] split = bufferedReader.readLine().split(" ");
            bufferedReader.close();
            ipAddress = split[0];
            port = split[1];
        }else{
            getIPPort ipPort = new getIPPort(null, true, "192.168.1.3","5050");
            ipPort.setVisible(true);
            ipAddress = ipPort.getIP();
            port = ipPort.getPort();
            writeIpPort();
        }
    }
    
    private void writeIpPort() throws IOException{
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./src/other/conncetion.txt"));
        bufferedWriter.write(ipAddress+" "+port);
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
