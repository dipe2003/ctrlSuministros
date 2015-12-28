
package com.dperez.inalerlab.email;

import java.util.Properties;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author dperez
 */
@Named
@Stateless
public class SendMail {

    private String host;
    private final Properties properties = System.getProperties();
    private Session session;
    
    public void setupMail(String host, String user, String password){
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.user", user);
        properties.setProperty("mail.password", password);
        session = Session.getDefaultInstance(properties);
    }
    
    public boolean enviarMail(String to, String from, String mensaje, String asunto){
        try{

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            
            // Set To
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            
            // Set Asunto
            message.setSubject(asunto);
            
            // Set contenido de mail en html
            message.setContent("<h1>This is actual message</h1>", "text/html" );
            
            // Enviar mensaje
            Transport.send(message);
            System.out.println("El mensaje fue enviado.");
            return true;
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return false;
    }
}

