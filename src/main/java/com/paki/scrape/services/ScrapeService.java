package com.paki.scrape.services;

import com.paki.persistence.*;
import com.paki.realties.Realty;
import com.paki.realties.enums.RealtyType;
import com.paki.scheduler.SchedulerService;
import com.paki.scrape.entities.*;
import com.paki.scrape.scraper.Scraper;
import com.paki.scrape.scraper.ScraperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScrapeService {
    private SchedulerService schedulerService;
    private ScraperFactory scraperFactory;
    private ApartmentRepository apartmentRepository;
    private HouseRepository houseRepository;
    private LandRepository landRepository;
    private RealtyPriceChangeRepository priceChangeRepository;
    private RealtySearchRelationRepository realtySearchRepository;
    private CriteriaService criteriaService;
    private SearchProfileRepository searchProfileRepository;
    private ScrapeSettingsRepository scrapeSettingsRepository;
    private ScrapeInfoRepository scrapeInfoRepository;

    @Autowired
    public ScrapeService(SchedulerService schedulerService, ScraperFactory scraperFactory
            , ApartmentRepository apartmentRepository, HouseRepository houseRepository
            , LandRepository landRepository, RealtyPriceChangeRepository priceChangeRepository
            , RealtySearchRelationRepository realtySearchRepository, CriteriaService criteriaService
            , SearchProfileRepository searchProfileRepository, ScrapeSettingsRepository scrapeSettingsRepository
            , ScrapeInfoRepository scrapeInfoRepository) {
        this.schedulerService = schedulerService;
        this.scraperFactory = scraperFactory;
        this.apartmentRepository = apartmentRepository;
        this.houseRepository = houseRepository;
        this.landRepository = landRepository;
        this.priceChangeRepository = priceChangeRepository;
        this.realtySearchRepository = realtySearchRepository;
        this.criteriaService = criteriaService;
        this.searchProfileRepository = searchProfileRepository;
        this.scrapeSettingsRepository = scrapeSettingsRepository;
        this.scrapeInfoRepository = scrapeInfoRepository;
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
        ScrapeInfo scrapeInfo = prepareScrapeInfo();
        List<SearchProfile> profiles = searchProfileRepository.findAll();
        for(SearchProfile profile: profiles) {
            Search search = profile.getSearch();
            for (ScraperType scraperType: ScraperType.values()) {
                scrape(scrapeInfo, search, scraperType);
            }
        }
    }

    public void scrape(ScrapeInfo scrapeInfo, Search search, ScraperType scraperType) {
        Scraper scraper = scraperFactory.createScraper(scraperType, search);
        while (true) {
            try {
                List<Realty> pageResults = scraper.scrapeNext();
                if (pageResults.isEmpty())
                    break;
                processScrapedRealties(scrapeInfo, search, pageResults);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processScrapedRealties(ScrapeInfo scrapeInfo, Search search, List<Realty> realties) {
        RealtyRepository<? extends Realty> realtyRepository = inferRealtyRepository(search);
        Map<String, ? extends Realty> dbRealtiesMap = realtyRepository
                .findByExternalIdIn(mapToIdList(realties))
                .stream()
                .collect(Collectors.toMap(Realty::getExternalId, r -> r));

        for (Realty realty: realties) {
            Realty dbRealty = dbRealtiesMap.get(realty.getExternalId());
            // if price updated create RealtyPriceChange
            if (dbRealty != null) {
                if (dbRealty.getPrice() != realty.getPrice()) {
                    BigDecimal priceDelta = realty.getPrice().subtract(dbRealty.getPrice());
                    RealtyPriceChange priceChange = RealtyPriceChange.builder()
                            .realty(dbRealty)
                            .changeRunNumber(scrapeInfo.getLastRunNumber())
                            .changeDate(scrapeInfo.getLastRunTime())
                            .delta(priceDelta)
                            .build();
                    priceChangeRepository.save(priceChange);
                    dbRealty.setPrice(realty.getPrice());
                    realtyRepository.save(dbRealty);
                }
            } else { // create new realty
                realty.setScrapeRunNumber(scrapeInfo.getLastRunNumber());
                dbRealty = realtyRepository.save(realty);
            }
            realtySearchRepository.save(new RealtySearchRelation(dbRealty, search));
        }

    }

    private RealtyRepository<? extends Realty> inferRealtyRepository(Search search) {
        RealtyRepository realtyRepository = inferRealtyRepository(search.getRealtyType());
        if (realtyRepository == null)
            realtyRepository = inferRealtyRepository(criteriaService.inferRealtyType(search.getCriteria()));
        return realtyRepository;
    }

    private RealtyRepository<? extends Realty> inferRealtyRepository(RealtyType realtyType) {
        switch (realtyType) {
            case APARTMENT:
                return apartmentRepository;
            case HOUSE:
                return houseRepository;
            case LAND:
                return landRepository;
            default:
                return null;
        }
    }

    private List<String> mapToIdList(List<Realty> realties) {
        return realties.stream().map(Realty::getExternalId).collect(Collectors.toList());
    }

    private ScrapeInfo prepareScrapeInfo() {
        ScrapeInfo scrapeInfo = null;
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



}
