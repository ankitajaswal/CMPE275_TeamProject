package cmpe275.wiors.mail;

import javax.mail.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import cmpe275.wiors.util.*;

public class MailSender {

    static final String username = "spring.cmpe.275@outlook.com";
    static final String password = "Test1234$#";

	
    public void sendActivationMail(String recipient) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MailSender.username, MailSender.password);
            }
        });

        try {
        	
        	Activation activation = new Activation();
        	String mailBody = "To activate your account please click on this link: http://107.175.28.141:8080/activate/" + activation.encodeEmail(recipient);
        	
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Activation Mail");
            message.setText(mailBody);

            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String[] args) {
    	MailSender mailSender = new MailSender();
    	mailSender.sendActivationMail("zaber.ahmed@gmail.com"); 
    }

    
}
