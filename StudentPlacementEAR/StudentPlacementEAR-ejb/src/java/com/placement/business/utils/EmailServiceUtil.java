/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business.utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author kemele
 */
public class EmailServiceUtil {

    public static boolean sendEmail(String email, String text, String subject) {
        final String username = "infoictBPMCourse@gmail.com";
        final String password = "infoictBPMCours";
        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");

            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            try {
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(username));
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                msg.setSubject(subject);
                msg.setText(text);
                Transport.send(msg);
                return true;
            } catch (MessagingException me) {
                me.printStackTrace();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
