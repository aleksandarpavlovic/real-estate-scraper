package com.paki.scrape.services;

import com.paki.persistence.scrape.ScrapeInfoRepository;
import com.paki.persistence.scrape.ScrapeSettingsRepository;
import com.paki.persistence.scrape.SearchProfileRepository;
import com.paki.realties.Realty;
import com.paki.scheduler.SchedulerService;
import com.paki.scrape.entities.*;
import com.paki.scrape.scraper.Scraper;
import com.paki.scrape.scraper.ScraperFactory;
import com.paki.scrape.synchronization.GlobalLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ScrapeService {
    private SchedulerService schedulerService;
    private ScraperFactory scraperFactory;
    private SearchProfileRepository searchProfileRepository;
    private ScrapeSettingsRepository scrapeSettingsRepository;
    private ScrapeInfoRepository scrapeInfoRepository;
    private RealtyService realtyService;
    private TopAdService topAdService;
    private NotificationService notificationService;
    private GlobalLock globalLock;

    @Autowired
    public ScrapeService(SchedulerService schedulerService, ScraperFactory scraperFactory, SearchProfileRepository searchProfileRepository, ScrapeSettingsRepository scrapeSettingsRepository, ScrapeInfoRepository scrapeInfoRepository, RealtyService realtyService, TopAdService topAdService, NotificationService notificationService, GlobalLock globalLock) {
        this.schedulerService = schedulerService;
        this.scraperFactory = scraperFactory;
        this.searchProfileRepository = searchProfileRepository;
        this.scrapeSettingsRepository = scrapeSettingsRepository;
        this.scrapeInfoRepository = scrapeInfoRepository;
        this.realtyService = realtyService;
        this.topAdService = topAdService;
        this.notificationService = notificationService;
        this.globalLock = globalLock;
    }

    @PostConstruct
    public void scheduleScraping() {
        Optional<ScrapeSettings> settingsOptional = scrapeSettingsRepository.findById(ScrapeSettings.SINGLETON_ID);
        int period = 0;
        if (settingsOptional.isPresent())
            period = settingsOptional.get().getScheduledPeriod();
        schedulerService.schedule(this::scrape, period);
    }

    public void scrape() {
        globalLock.lock();
        try {
            List<SearchProfile> profiles = searchProfileRepository.findAll();
            if (profiles.isEmpty()) {
                System.out.println("********* No profiles found. Scraping skipped *********");
                return;
            }
            ScrapeInfo scrapeInfo = prepareScrapeInfo();
            System.out.println("********* Scrape #" + scrapeInfo.getLastRunNumber() + " started at " + scrapeInfo.getLastRunTime() + " *********");
            Map<String, Map<String, List<? extends Realty>>> topAds = new HashMap<>();

            for (SearchProfile profile : profiles) {
                System.out.println("***** Scraping for profile: " + profile.getName() + "... *****");
                Search search = profile.getSearch();
                for (ScraperType scraperType : ScraperType.values()) {
                    scrape(scrapeInfo, search, scraperType);
                }
                topAds.put(profile.getName(), topAdService.getTopAds(scrapeInfo, profile));
                System.out.println("***** Scrape for profile: " + profile.getName() + " completed. *****");
            }
            notificationService.notify(topAds);
            System.out.println("********* Scrape #" + scrapeInfo.getLastRunNumber() + " finished at " + LocalDateTime.now() + " *********");
        } catch (Exception e) {
            System.out.println("********* Scrape interrupted due to unexpected error *********");
            e.printStackTrace();
        } finally {
            globalLock.unlock();
        }
    }

    public void scrape(ScrapeInfo scrapeInfo, Search search, ScraperType scraperType) {
        Scraper scraper = scraperFactory.createScraper(scraperType, search);
        try {
            while (true) {
                Set<Realty> pageResults = scraper.scrapeNext();
                if (pageResults.isEmpty())
                    break;
                Set<Realty> newRealties = realtyService.processScrapedRealties(scrapeInfo, search, pageResults);
                Set<Realty> completeNewRealties = scraper.scrapeAdditionalFields(newRealties);
                realtyService.updateRealties(search, completeNewRealties);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ScrapeInfo prepareScrapeInfo() {
        ScrapeInfo scrapeInfo;
        Optional<ScrapeInfo> infoOptional = scrapeInfoRepository.findById(ScrapeInfo.SINGLETON_ID);
        if (infoOptional.isPresent()) {
            scrapeInfo = infoOptional.get();
            scrapeInfo.setLastRunNumber(scrapeInfo.getLastRunNumber() + 1);
            scrapeInfo.setLastRunTime(LocalDateTime.now());
        } else {
            scrapeInfo = ScrapeInfo.builder()
                    .lastRunNumber(1L)
                    .lastRunTime(LocalDateTime.now())
                    .build();
        }
        scrapeInfoRepository.save(scrapeInfo);

        return scrapeInfo;
    }

    public void deleteScrapeInfo() {
        scrapeInfoRepository.deleteAll();
    }
}
