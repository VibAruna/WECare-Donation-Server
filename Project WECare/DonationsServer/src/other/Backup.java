/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import db.DBConnection;
import java.io.IOException;

/**
 *
 * @author pavilion 15
 */
public class Backup {
    public static boolean writeBackup(String absolutePath) throws InterruptedException, IOException{
        String password = DBConnection.getPassword();
        Process process = Runtime.getRuntime().exec("mysqldump Donation -h localhost -u root -p"+password+" -r  "+absolutePath+".sql");
        int waitFor = process.waitFor();
        return waitFor == 0;
    }
    
    public static boolean restore(String path) throws InterruptedException, IOException{
        String password = DBConnection.getPassword();
        String[] command = new String[]{"mysql", "Donation", "--user=root" , "--password="+password+"", "-e", " source "+path };
        Process exec = Runtime.getRuntime().exec(command);
        int restore = exec.waitFor();
        return restore == 0;
    }
}
