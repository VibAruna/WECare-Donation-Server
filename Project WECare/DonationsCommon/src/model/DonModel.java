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
public class DonModel implements Serializable{
    private String donId;
    private String donation;
    private String category;
    private String desc;
    private String donar;
    private String recipient;
    private ImageIcon img;

    public DonModel() {
    }

    public DonModel(String donId, String donation, String category, String desc, String donar, String recipient, ImageIcon img) {
        this.donId = donId;
        this.donation = donation;
        this.category = category;
        this.desc = desc;
        this.donar = donar;
        this.recipient = recipient;
        this.img = img;
    }

    
    /**
     * @return the donId
     */
    public String getDonId() {
        return donId;
    }

    /**
     * @param donId the donId to set
     */
    public void setDonId(String donId) {
        this.donId = donId;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the donar
     */
    public String getDonar() {
        return donar;
    }

    /**
     * @param donar the donar to set
     */
    public void setDonar(String donar) {
        this.donar = donar;
    }

    /**
     * @return the recipient
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * @param recipient the recipient to set
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * @return the img
     */
    public ImageIcon getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(ImageIcon img) {
        this.img = img;
    }

    /**
     * @return the donation
     */
    public String getDonation() {
        return donation;
    }

    /**
     * @param donation the donation to set
     */
    public void setDonation(String donation) {
        this.donation = donation;
    }
}
