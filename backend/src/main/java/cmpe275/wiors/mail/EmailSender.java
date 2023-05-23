package cmpe275.wiors.mail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Properties;

public class EmailSender {

    private static final String APPLICATION_NAME = "Gmail API Email Sender";
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";
    private static final String USER_EMAIL = "spring.cmpe.272@gmail.com";



    private static Gmail createGmailService() throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                EmailSender.class.getResourceAsStream(CREDENTIALS_FILE_PATH)
        ).createScoped(Collections.singleton(GmailScopes.GMAIL_SEND));

        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

        return new Gmail.Builder(httpTransport, GsonFactory.getDefaultInstance(), requestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static void sendEmail(Gmail service, String fromEmail, String toEmail, String subject, String body)
            throws MessagingException, IOException {
        MimeMessage mimeMessage = createMimeMessage(fromEmail, toEmail, subject, body);
        Message message = createMessageWithEmail(mimeMessage);
        service.users().messages().send(USER_EMAIL, message).execute();
        System.out.println("Email sent successfully.");
    }

    private static MimeMessage createMimeMessage(String fromEmail, String toEmail, String subject, String body)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(fromEmail));
        mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toEmail));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(body);
        return mimeMessage;
    }

    private static Message createMessageWithEmail(MimeMessage email) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);

        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
    
    
    
    public static void main(String[] args) {
        try {
            Gmail service = createGmailService();
            sendEmail(service, USER_EMAIL, "zaber.ahmed@gmail.com", "Test Subject", "This is a test email.");
        } catch (IOException | GeneralSecurityException | MessagingException e) {
            e.printStackTrace();
        }
    }
    
    
    
}
