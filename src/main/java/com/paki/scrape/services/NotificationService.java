package com.paki.scrape.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.paki.mail.GmailCredentials;
import com.paki.mail.GmailService;
import com.paki.realties.Realty;
import com.paki.scrape.entities.ScrapeSettings;
import com.paki.scrape.topad.TopAdCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    private GmailService gmailService;
    private SettingsService settingsService;
    private Environment env;

    @Autowired
    public NotificationService(GmailService gmailService, SettingsService settingsService, Environment env) {
        this.gmailService = gmailService;
        this.settingsService = settingsService;
        this.env = env;
    }

    @PostConstruct
    void init() {
        String userEmail = env.getProperty("scraper.notification.gmail.userEmail");
        String clientId = env.getProperty("scraper.notification.gmail.clientId");
        String clientSecret = env.getProperty("scraper.notification.gmail.clientSecret");
        String accessToken = env.getProperty("scraper.notification.gmail.accessToken");
        String refreshToken = env.getProperty("scraper.notification.gmail.refreshToken");

        gmailService.setGmailCredentials(GmailCredentials.builder()
                .userEmail(userEmail)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());
        try {
            gmailService.setHttpTransport(GoogleNetHttpTransport.newTrustedTransport());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notify(Map<String, Map<TopAdCondition, List<? extends Realty>>> topAds) {
        if (topAds != null && !topAds.isEmpty())
            return;

        StringBuilder sb = new StringBuilder();
        sb.append("Pronadjeni su novi oglasi koji vam mogu biti interesantni!" + System.lineSeparator());
        topAds.forEach((profileName, adMap) -> {
            sb.append(System.lineSeparator() + "Profil: " + profileName + System.lineSeparator());
            adMap.forEach((condition, ads) -> {
                sb.append("\tOglasi u kategoriji " + condition.toString() + ":" + System.lineSeparator());
                ads.forEach(ad -> sb.append("\t\t" + adDescription(ad) + System.lineSeparator()));
            });
        });

        ScrapeSettings settings = settingsService.getSettings();
        if (settings != null && settings.getEmailList() != null && !settings.getEmailList().isEmpty())
            settings.getEmailList().forEach(recipient -> {
                try {
                    gmailService.sendMessage(recipient, "Scrape stanova izvestaj", sb.toString());
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }

    private String adDescription(Realty ad) {
        return ad.getTitle() + ", povrsina: " + ad.getSurfaceArea() + ad.getAreaMeasurementUnit().getDisplayValue() + ", cena: " + ad.getPrice() + ", link: " + ad.getUrl();
    }
}
