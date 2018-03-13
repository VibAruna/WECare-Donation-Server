/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.vcodes.vimage.image.ImageHandler;
import db.DBConnection;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import model.DonModel;
import model.UserModel;
import observer.DonationObserver;
import observerble.DonationObserverble;
import other.DateTimeHandler;
import other.Email;

/**
 *
 * @author Extreme
 */
public class DonControllerImpl extends UnicastRemoteObject implements DonController{
    public static final DonationObserverble OBSERVERBLE = new DonationObserverble();
    private static UserController userController;
    private static final Email EMAIL = new Email();
    static {
        try {
            userController= new UserControllerImpl();
        } catch (RemoteException ex) {
            Logger.getLogger(DonControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static Thread auto;
    
    public DonControllerImpl() throws RemoteException{}

    @Override
    public boolean donate(DonModel donModel) throws RemoteException, IOException, ClassNotFoundException, SQLException {
        ImageHandler.saveImage(new File("./src/donations/"+donModel.getDonId()+".png"), "png", donModel.getImg());
        
        String sql="insert into donations values(?,?,?,?,?,?,?)";
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, donModel.getDonId());
        statement.setObject(2, donModel.getDonation());
        statement.setObject(3, donModel.getCategory());
        statement.setObject(4, donModel.getDesc());
        statement.setObject(5, donModel.getDonar());
        statement.setObject(6, donModel.getRecipient());
        statement.setObject(7, DateTimeHandler.getNow());
        
        int executeUpdate = statement.executeUpdate();
        OBSERVERBLE.notifyObservers();
        return executeUpdate > 0;
    }

    @Override
    public ArrayList<DonModel> getAllDoantions() throws RemoteException, IOException, ClassNotFoundException, SQLException {
        return getDoantions("select * from donations");
    }

    @Override
    public ArrayList<DonModel> getAllDoantions(String category) throws RemoteException, IOException, ClassNotFoundException, SQLException {
        return getDoantions("select * from donations where category='"+category+"';");
    }

    @Override
    public ArrayList<DonModel> getNotReqDoantions(String nic) throws RemoteException, IOException, ClassNotFoundException, SQLException {
        return getDoantions("select * from donations where DonId not in (select DonId from requests where nic='"+nic+"');");
    }
    
    @Override
    public ArrayList<DonModel> getReqDoantions(String nic) throws RemoteException, IOException, ClassNotFoundException, SQLException {
        return getDoantions("select * from donations where DonId in (select DonId from requests where nic='"+nic+"');");
    }
    
    private ArrayList<DonModel> getDoantions(String sql) throws RemoteException, IOException, ClassNotFoundException, SQLException{
        Connection connection = DBConnection.getDBConnection();
        Statement statement = connection.createStatement();
        ResultSet executeQuery = statement.executeQuery(sql);
        ArrayList<DonModel> donList=new ArrayList<>();
        while(executeQuery.next()){
            donList.add(new DonModel(executeQuery.getString(1), executeQuery.getString(2), executeQuery.getString(3), executeQuery.getString(4), executeQuery.getString(5), executeQuery.getString(6), new ImageIcon("./src/donations/"+executeQuery.getString(1)+".png")));
        }
        return donList;
    }
    
    public boolean putRecipient(String recipient, String donId) throws ClassNotFoundException, IOException, SQLException{
        String sql = "update donations set recipient=? where DonId=?";
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, recipient);
        statement.setObject(2, donId);
        OBSERVERBLE.notifyObservers();
        sendMails(donId);
        return statement.executeUpdate() > 0;
    }

    @Override
    public void addDonationObserver(DonationObserver observer) throws RemoteException {
        OBSERVERBLE.addObserver(observer);
    }

    @Override
    public boolean requestDonation(String donId, String nic) throws RemoteException, ClassNotFoundException, IOException, SQLException {
        String sql="insert into requests values(?,?)";
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, donId);
        statement.setObject(2, nic);
        
        int executeUpdate = statement.executeUpdate();
        return executeUpdate > 0;
    }

    @Override
    public boolean cancelRequest(String donId, String nic) throws RemoteException, IOException, ClassNotFoundException, SQLException {
        String sql="delete from requests where DonId = ? and nic = ?";
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, donId);
        statement.setObject(2, nic);
        
        int executeUpdate = statement.executeUpdate();
        return executeUpdate > 0;
    }
    
    public static void autoChooseRecipient(){
        if (auto == null) {
            auto = new Thread(){

                @Override
                public void run() {
                    while (true) {                    
                        try {
                            Map<String, Integer> reqCounts = getReqCounts();
                            Set<String> keySet = reqCounts.keySet();
                            for (String key : keySet) {
                                if (reqCounts.get(key) == 1) {
                                    putRecipientAutomatically(key);
                                }else{
                                    notifyManagement();
                                }
                            }

                            Thread.sleep(86400000);                     //wait for a day
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(DonControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(DonControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(DonControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(DonControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            };
            auto.start();
        }
            
    }
    
    private static  Map<String, Integer> getReqCounts() throws ClassNotFoundException, IOException, SQLException{
        String sql = "select * from requests where DonId in(select DonId from donations where recipient = '' and DATEDIFF('"+DateTimeHandler.getNow()+"',donDate) > 30)";
        
        Connection connection = DBConnection.getDBConnection();
        Statement statement = connection.createStatement();
        ResultSet executeQuery = statement.executeQuery(sql);
        Map<String, Integer> reqCounts = new HashMap<>();
        while (executeQuery.next()) {
            String donId = executeQuery.getString(1);
            if (reqCounts.keySet().contains(donId)) {
                reqCounts.put(donId, reqCounts.get(donId)+1);
            }else{
                reqCounts.put(donId, 1);
            }
        }
        return reqCounts;
    }
    
    private static boolean putRecipientAutomatically(String donId) throws ClassNotFoundException, IOException, SQLException{
        String sql = "update donations set recipient=(select nic from requests where DonId = ?) where DonId=?";
        Connection connection = DBConnection.getDBConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1, donId);
        statement.setObject(2, donId);
        OBSERVERBLE.notifyObservers();
        sendMails(donId);
        return statement.executeUpdate() > 0;
    }
    
    private static void notifyManagement(){
        
    }
    
    public ArrayList<DonModel> getDonation(String nic) throws IOException, RemoteException, ClassNotFoundException, SQLException{
        return getDoantions("select * from donations where donar = '"+nic+"';");
    }
    
    public Map<DonModel, ArrayList<UserModel>> getRequestsToChooseRecipient() throws ClassNotFoundException, IOException, SQLException{
        String sql = "select * from donations where recipient = '' and DATEDIFF('"+DateTimeHandler.getNow()+"',donDate) > 30;";
        
        Connection connection = DBConnection.getDBConnection();
        Statement statement = connection.createStatement();
        ResultSet executeQuery = statement.executeQuery(sql);
        
        ArrayList<DonModel> dons = new ArrayList<>();
        while (executeQuery.next()) {
            dons.add(new DonModel(executeQuery.getString(1), executeQuery.getString(2), executeQuery.getString(3), executeQuery.getString(4), executeQuery.getString(5), executeQuery.getString(6), new ImageIcon("./src/donations/"+executeQuery.getString(1)+".png")));
        }
        
        Map<DonModel, ArrayList<UserModel>> requests = new HashMap<>();
        for (DonModel don : dons) {
            sql = "select * from user where nic in (select nic from requests where DonId = '"+don.getDonId()+"')";
            statement = connection.createStatement();
            executeQuery = statement.executeQuery(sql);

            ArrayList<UserModel> user = new ArrayList<>();
            while (executeQuery.next()) {
                user.add(new UserModel(executeQuery.getString(1), executeQuery.getString(2), executeQuery.getString(3), executeQuery.getInt(4), executeQuery.getString(5), executeQuery.getString(6),executeQuery.getString(7),new ImageIcon(ImageIO.read(new File("./src/profPics/"+executeQuery.getString(3)+".png")))));
            }
            
            requests.put(don, user);
        }
        
        return requests;
    }
    
    private static void sendMails(String DonId) throws ClassNotFoundException, IOException, SQLException{
        
        new Thread(){
            
            @Override
            public void run() {
                try {
                    String sql = "select * from donations where DonId = '"+DonId+"';";
                    Connection connection = DBConnection.getDBConnection();
                    Statement statement = connection.createStatement();
                    ResultSet executeQuery = statement.executeQuery(sql);
                    executeQuery.next();
                    DonModel donModel = new DonModel(executeQuery.getString(1), executeQuery.getString(2), executeQuery.getString(3), executeQuery.getString(4), executeQuery.getString(5), executeQuery.getString(6), new ImageIcon("./src/donations/"+executeQuery.getString(1)+".png"));
                    
                    UserModel donnar = userController.getUser(donModel.getDonar());
                    UserModel recipient = userController.getUser(donModel.getRecipient());
                    
                    EMAIL.send(donnar.getMail(), "", "Dear "+donnar.getfName()+",\n "+recipient.getfName()+" "+recipient.getlName()+" (NIC : "+recipient.getNic()+") is choosen for your donation "+donModel.getDonation()+". Thank you for your donation.");
                    EMAIL.send(recipient.getMail(), "", "Dear "+recipient.getfName()+",\n You are choosen for the donation "+donModel.getDonation());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DonControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(DonControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(DonControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                    Logger.getLogger(DonControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
        
    }
}
