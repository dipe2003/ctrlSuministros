
package com.dperez.inalerlab.email;

import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
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
    @Inject
    private ControladorPropiedad prop;
    private static String user;
    private static String pass;
    private static String mail;
    
    private static Properties props;
    private static Session session;
    static {
        props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.host", "m.outlook.com");
        //props.put("mail.smtp.host", "smtp.outlook365.com");
        props.put("mail.smtp.auth", "true");

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, pass);
                    }
                });

    }
    
    @PostConstruct
    public void init(){
        user = prop.getMailUser();
        pass = prop.getMailPass();
        mail = prop.getMailFrom();
    }
    
    public boolean enviarMail(String to,String mensaje, String asunto){
        
        try{
            Transport transport =session.getTransport();
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(prop.getMailFrom()));
            
            // Set To
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            
            // Set Asunto
            message.setSubject(asunto);
            
            // Set contenido de mail en html
            message.setContent(mensaje, "text/html" );
            
            // Enviar mensaje
            transport.connect(user, pass);
            transport.send(message, user, pass);
            transport.close();
            System.out.println("El mensaje fue enviado.");
            return true;
        }catch (MessagingException mex) {
            //mex.printStackTrace();
            System.out.println("El mensaje no fue enviado: " + mex.getMessage());
        }
        return false;
    }
}

