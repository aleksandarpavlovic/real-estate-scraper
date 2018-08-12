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
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.text.DecimalFormat;
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
        if (topAds == null || topAds.isEmpty())
            return;

        StringBuilder sb = new StringBuilder();
        sb.append(MAIL_TITLE_HTML);
        topAds.forEach((profileName, adMap) -> {
            sb.append(profileHtml(profileName));
            adMap.forEach((condition, ads) -> {
                sb.append(topAdCategoryHtml(condition.toString()));
                sb.append(MAIL_START_AD_LIST_HTML);
                ads.forEach(ad -> sb.append(adHtml(ad)));
                sb.append(MAIL_END_AD_LIST_HTML);
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

    private String profileHtml(String profileName) {
        return String.format(MAIL_PROFILE_HTML, profileName);
    }

    private String topAdCategoryHtml(String topAdName) {
        return String.format(MAIL_TOP_AD_HTML, topAdName);
    }

    private String adHtml(Realty ad) {
        return String.format(MAIL_AD_HTML, ad.getUrl(), ad.getTitle(), ad.getLocation(), formatPrice(ad.getPrice()), ad.getSurfaceArea().toString(), ad.getAreaMeasurementUnit().getDisplayValue());
    }

    private static DecimalFormat priceFormat = new DecimalFormat(",###");
    private String formatPrice(BigDecimal price) {
        return priceFormat.format(price).replace(',', '.');
    }

    private static final String MAIL_TITLE_HTML = "<h2>Pronadjeni su novi oglasi koji vam mogu biti interesantni!</h2>";
    private static final String MAIL_PROFILE_HTML = "<p style=\"font-size: 1.5em;\">Profil <strong><em>%s</em></strong>:</p>";
    private static final String MAIL_TOP_AD_HTML = "<p style=\"font-size: 1.3em; padding-left: 20px;\">Oglasi u kategoriji <strong><em>%s</em></strong>:</p>";
    private static final String MAIL_START_AD_LIST_HTML = "<ul>";
    private static final String MAIL_AD_HTML = "<li style=\"padding-left: 30px;\"><a href=\"%s\">%s, %s, %s &euro;, %s %s</a></li>";
    private static final String MAIL_END_AD_LIST_HTML = "</ul>";
}
