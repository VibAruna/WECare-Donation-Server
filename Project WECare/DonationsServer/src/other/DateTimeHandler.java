/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Extreme
 */
public class DateTimeHandler {
    public static String getNow(){
        Date date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("YYYY-MM-dd");
        return formatDate.format(date);
    }
    
    public static int getMonthDifference(String date1, String date2) throws ParseException{
        String[] split = date1.split("-");
        int yr1 = Integer.parseInt(split[0]);
        int mn1 = Integer.parseInt(split[1]);
        int dt1 = Integer.parseInt(split[2]);

        split = date2.split("-");
        int yr2 = Integer.parseInt(split[0]);
        int mn2 = Integer.parseInt(split[1]);
        int dt2 = Integer.parseInt(split[2]);
        
        if (yr1 > yr2) {
            return -1;
        }else if (yr1 < yr2) {
            return getDifference(yr1, mn1, dt1, yr2, mn2, dt2);
        }else{
            if (mn1 > mn2) {
                return -1;
            }else if (mn1 < mn2) {
                return getDifference(yr1, mn1, dt1, yr2, mn2, dt2);
            }else{
                if (dt2 >= dt1) {
                    return 0;
                }else{
                    return -1;
                }
            }
        }
        
    }
    
    private static int getDifference(int yr1, int mn1, int dt1,int yr2,int mn2, int dt2){
        int diff;
        if (yr2 > yr1) {
            diff = ((mn2 + (12*(yr2-yr1)))*100+dt2) - (mn1*100+dt1);
        }else{
            diff = (mn2*100+dt2) - (mn1*100+dt1);
        }
        return diff/100;
    }
    
    public static void main(String[] args) throws ParseException {
        int monthDifference = getMonthDifference("2017-11-24", "2018-12-26");
        System.out.println(monthDifference);
    }
}
