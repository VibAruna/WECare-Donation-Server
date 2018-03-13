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
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import model.UserModel;
import view.ViewUserDetails;

/**
 *
 * @author Extreme
 */
public class UserControllerImpl extends UnicastRemoteObject implements UserController{
    private  static ArrayList<ViewUserDetails> viewers = new ArrayList<>();
    private  static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    
    public UserControllerImpl() throws RemoteException{}
    
    @Override
    public boolean addNewUser(UserModel user) throws RemoteException, IOException, ClassNotFoundException, SQLException {
        try{
            rwLock.writeLock().lock();
            ImageHandler.saveImage(new File("./src/profPics/"+user.getNic()+".png"), "png", user.getProfpic());

            String sql="insert into user values(?,?,?,?,?,?,?)";
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, user.getfName());
            statement.setObject(2, user.getlName());
            statement.setObject(3, user.getNic());
            statement.setObject(4, user.getTp());
            statement.setObject(5, user.getMail());
            statement.setObject(6, user.getAddress());
            statement.setObject(7, user.getPassword());
            int executeUpdate = statement.executeUpdate();

            for (ViewUserDetails viewer : viewers) {
                viewer.updateList();
            }

            return executeUpdate > 0;
        }finally{
            rwLock.writeLock().unlock();
        }
        
    }
    
    public ArrayList<UserModel> getAllUsers() throws SQLException, ClassNotFoundException, IOException{
        try{
            rwLock.readLock().lock();
            String sql="select * from user";
            Connection connection = DBConnection.getDBConnection();
            Statement statement = connection.createStatement();
            ResultSet executeQuery = statement.executeQuery(sql);
            ArrayList<UserModel> userList=new ArrayList<>();
            while(executeQuery.next()){
                userList.add(new UserModel(executeQuery.getString(1), executeQuery.getString(2), executeQuery.getString(3), executeQuery.getInt(4), executeQuery.getString(5), executeQuery.getString(6),executeQuery.getString(7),new ImageIcon(ImageIO.read(new File("./src/profPics/"+executeQuery.getString(3)+".png")))));
            }
            return userList;
        }finally{
            rwLock.readLock().unlock();
        }
    }
    
    public static void addViewer(ViewUserDetails viewer){
        viewers.add(viewer);
    }
    
    public static void removeViewer(ViewUserDetails viewer){
        viewers.remove(viewer);
    }

    @Override
    public UserModel getUser(String nic) throws RemoteException, IOException, ClassNotFoundException, SQLException {
        try{
            rwLock.readLock().lock();
            String sql="select * from user where nic = '"+nic+"'";
            Connection connection = DBConnection.getDBConnection();
            Statement statement = connection.createStatement();
            ResultSet executeQuery = statement.executeQuery(sql);
            if (executeQuery.next()){
                return new UserModel(executeQuery.getString(1), executeQuery.getString(2), executeQuery.getString(3), executeQuery.getInt(4), executeQuery.getString(5), executeQuery.getString(6),executeQuery.getString(7),new ImageIcon(ImageIO.read(new File("./src/profPics/"+executeQuery.getString(3)+".png"))));
            }
            return null;
        }finally {
            rwLock.readLock().unlock();
        }
    }

    @Override
    public boolean updateUser(UserModel user) throws RemoteException, IOException, ClassNotFoundException, SQLException {
        try{
            rwLock.writeLock().lock();
            ImageHandler.saveImage(new File("./src/profPics/"+user.getNic()+".png"), "png", user.getProfpic());

            String sql="update user set fName=? , lName=? , tp=? , mail=? , address=? , password=? where nic = ?" ;
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, user.getfName());
            statement.setObject(2, user.getlName());
            statement.setObject(3, user.getTp());
            statement.setObject(4, user.getMail());
            statement.setObject(5, user.getAddress());
            statement.setObject(6, user.getPassword());
            statement.setObject(7, user.getNic());

            int executeUpdate = statement.executeUpdate();
            return executeUpdate > 0;
        }finally{
           rwLock.writeLock().unlock();
        }
    }
}
