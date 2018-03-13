/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Extreme
 */
public class Donation implements Serializable{
    
    private String name;
    private double donatin;

    public Donation() {
    }

    public Donation(String name, double donatin) {
        this.name = name;
        this.donatin = donatin;
    }
    
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the donatin
     */
    public double getDonatin() {
        return donatin;
    }

    /**
     * @param donatin the donatin to set
     */
    public void setDonatin(double donatin) {
        this.donatin = donatin;
    }
    
}
