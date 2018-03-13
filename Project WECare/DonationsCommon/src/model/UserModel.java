/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author Extreme
 */
public class UserModel implements Serializable{
    private String fName;
    private String lName;
    private String nic;
    private int tp;
    private String mail;
    private String address;
    private String password;
    private ImageIcon profpic;

    public UserModel() {
    }

    public UserModel(String fName, String lName, String nic, int tp, String mail, String address, String password, ImageIcon profpic) {
        this.fName = fName;
        this.lName = lName;
        this.nic = nic;
        this.tp = tp;
        this.mail = mail;
        this.address = address;
        this.password = password;
        this.profpic = profpic;
    }
    
    

    /**
     * @return the fName
     */
    public String getfName() {
        return fName;
    }

    /**
     * @param fName the fName to set
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * @return the lName
     */
    public String getlName() {
        return lName;
    }

    /**
     * @param lName the lName to set
     */
    public void setlName(String lName) {
        this.lName = lName;
    }

    /**
     * @return the nic
     */
    public String getNic() {
        return nic;
    }

    /**
     * @param nic the nic to set
     */
    public void setNic(String nic) {
        this.nic = nic;
    }

    /**
     * @return the tp
     */
    public int getTp() {
        return tp;
    }

    /**
     * @param tp the tp to set
     */
    public void setTp(int tp) {
        this.tp = tp;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the profpic
     */
    public ImageIcon getProfpic() {
        return profpic;
    }

    /**
     * @param profpic the profpic to set
     */
    public void setProfpic(ImageIcon profpic) {
        this.profpic = profpic;
    }
}
