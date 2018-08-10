package com.paki.mail;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import lombok.Setter;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

public final class GmailService {

    private static final String APPLICATION_NAME = "Realty Scraper";

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Setter
    private HttpTransport httpTransport;
    @Setter
    private GmailCredentials gmailCredentials;

    public boolean sendMessage(String recipientAddress, String subject, String body) throws MessagingException,
            IOException {
        Message message = createMessageWithEmail(
                createEmail(recipientAddress, gmailCredentials.getUserEmail(), subject, body));

        return createGmail().users()
                .messages()
                .send(gmailCredentials.getUserEmail(), message)
                .execute()
                .getLabelIds().contains("SENT");
    }

    private Gmail createGmail() {
        Credential credential = authorize();
        return new Gmail.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
        MimeMessage email = new MimeMessage(Session.getDefaultInstance(new Properties(), null));
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    private Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);

        return new Message()
                .setRaw(Base64.encodeBase64URLSafeString(buffer.toByteArray()));
    }

    private Credential authorize() {
        return new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setClientSecrets(gmailCredentials.getClientId(), gmailCredentials.getClientSecret())
                .build()
                .setAccessToken(gmailCredentials.getAccessToken())
                .setRefreshToken(gmailCredentials.getRefreshToken());
    }

    public static void main(String[] args) {
        try {
            GmailService gmailService = new GmailService();
            gmailService.setHttpTransport(GoogleNetHttpTransport.newTrustedTransport());
            gmailService.setGmailCredentials(GmailCredentials.builder()
                    .userEmail("alex.pavlovic92@gmail.com")
                    .clientId("1045577651056-2qpdjdrngrt4olh7p6lvo8s2eu045ksk.apps.googleusercontent.com")
                    .clientSecret("6dvVahlbjBbwzqQYhHSfa984")
                    .accessToken("ya29.Glv1BS3Nap8q1IlkSGRkxuuurVKeT0mWJmr6mh39MexHJSa3KjO1BEzo3RKk29AwWry7dEop7QuMPVtVlQYYkekGGR4SxRITiBmUp6OAm6LdWrUHTR5HrcjVYFmP")
                    .refreshToken("1/DENnbv10UuvOaiWUSPf9AMuvNB4pOfHFsxviviN1NdX-UGF5wqct7KUbtuy8ZhVJ")
                    .build());

            gmailService.sendMessage("alex.pavlovic92@gmail.com", "Subject", "body text");
        } catch (GeneralSecurityException | IOException | MessagingException e) {
            e.printStackTrace();
        }
    }
}