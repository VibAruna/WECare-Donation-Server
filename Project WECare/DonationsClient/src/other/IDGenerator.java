/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import connector.ServerConnector;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.text.NumberFormat;

/**
 *
 * @author Extreme
 */
public class IDGenerator {
    
    public static String getNextID(String table,String column, String prefix) throws ClassNotFoundException, SQLException, IOException, NotBoundException {
        String lastID = ServerConnector.getServerConnector().getIDController().getLastID(table, column);
        return IDGenerator(lastID, prefix);        
    }
    
    private static String IDGenerator(String lastID,String prefix){
        if(lastID != null){
            String idSuffix = lastID.split(prefix)[1];
            int sufix=Integer.parseInt(lastID.split(prefix)[1]);
            sufix++;
            NumberFormat integerInstance = NumberFormat.getIntegerInstance();
            if (idSuffix.length() < 9) {
                integerInstance.setMinimumIntegerDigits(9);
            }else{
                integerInstance.setMinimumIntegerDigits(idSuffix.length());
            }
            integerInstance.setGroupingUsed(false);
            String formatedId = integerInstance.format(sufix);
            String nextID = prefix + formatedId;
            if (nextID.length() == 10) {
                return nextID;
            } else {
                return null;
            }
        }
        return prefix+"000000001";
    }
}
