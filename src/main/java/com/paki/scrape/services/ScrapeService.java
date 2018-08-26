package com.paki.scrape.services;

import com.paki.persistence.scrape.ScrapeInfoRepository;
import com.paki.persistence.scrape.SearchProfileRepository;
import com.paki.realties.Realty;
import com.paki.realties.Source;
import com.paki.scrape.entities.ScrapeInfo;
import com.paki.scrape.entities.Search;
import com.paki.scrape.entities.SearchProfile;
import com.paki.scrape.scraper.Scraper;
import com.paki.scrape.scraper.ScraperFactory;
import com.paki.scrape.synchronization.GlobalLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ScrapeService {
    private ScraperFactory scraperFactory;
    private SearchProfileRepository searchProfileRepository;
    private ScrapeInfoRepository scrapeInfoRepository;
    private RealtyService realtyService;
    private TopAdService topAdService;
    private NotificationService notificationService;
    private GlobalLock globalLock;

    @Autowired
    public ScrapeService(ScraperFactory scraperFactory, SearchProfileRepository searchProfileRepository, ScrapeInfoRepository scrapeInfoRepository, RealtyService realtyService, TopAdService topAdService, NotificationService notificationService, GlobalLock globalLock) {
        this.scraperFactory = scraperFactory;
        this.searchProfileRepository = searchProfileRepository;
        this.scrapeInfoRepository = scrapeInfoRepository;
        this.realtyService = realtyService;
        this.topAdService = topAdService;
        this.notificationService = notificationService;
        this.globalLock = globalLock;
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
                for (Source source : scraperFactory.getRegisteredScraperSources()) {
                    scrape(scrapeInfo, search, source);
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

    public void scrape(ScrapeInfo scrapeInfo, Search search, Source source) {
        Scraper scraper = scraperFactory.createScraper(source, search);
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
