package other;

import java.util.Properties;  
import javax.mail.*;  
import javax.mail.internet.*;  
  
public class Email {  
    public  void  send(String address, String subject, String msg) throws MessagingException{  

        final String user="wecare227@gmail.com";//change accordingly  
        final String password="MSDprojectwecare";//change accordingly  

        String to=address; 

         //Get the session object  
        Properties props = new Properties();  
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");  

        Session session = Session.getDefaultInstance(props,  
             new javax.mail.Authenticator() {  
                 protected PasswordAuthentication getPasswordAuthentication() {  
                     return new PasswordAuthentication(user,password);  
                 }  
             }
        );  

         //Compose the message  
         
        MimeMessage message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(user));  
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
        message.setSubject(subject);  
        message.setText(msg);  

       //send the message  
        Transport.send(message);  

        
         
    }  
}  