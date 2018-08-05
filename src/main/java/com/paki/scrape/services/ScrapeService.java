package com.paki.scrape.services;

import com.paki.persistence.*;
import com.paki.realties.Realty;
import com.paki.realties.enums.RealtyType;
import com.paki.scheduler.SchedulerService;
import com.paki.scrape.entities.RealtyPriceChange;
import com.paki.scrape.entities.RealtySearchRelation;
import com.paki.scrape.entities.ScraperType;
import com.paki.scrape.entities.Search;
import com.paki.scrape.scraper.Scraper;
import com.paki.scrape.scraper.ScraperFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScrapeService {
    private SchedulerService schedulerService;
    private ScraperFactory scraperFactory;
    private ApartmentRepository apartmentRepository;
    private HouseRepository houseRepository;
    private LandRepository landRepository;
    private RealtyPriceChangeRepository priceChangeRepository;
    private RealtySearchRelationRepository realtySearchRepository;
    private CriteriaService criteriaService;

    @Autowired
    public ScrapeService(SchedulerService schedulerService, ScraperFactory scraperFactory, ApartmentRepository apartmentRepository, HouseRepository houseRepository, LandRepository landRepository, RealtyPriceChangeRepository priceChangeRepository, RealtySearchRelationRepository realtySearchRepository, CriteriaService criteriaService) {
        this.schedulerService = schedulerService;
        this.scraperFactory = scraperFactory;
        this.apartmentRepository = apartmentRepository;
        this.houseRepository = houseRepository;
        this.landRepository = landRepository;
        this.priceChangeRepository = priceChangeRepository;
        this.realtySearchRepository = realtySearchRepository;
        this.criteriaService = criteriaService;
    }

    public void scrape(Search search, ScraperType scraperType) {
        Scraper scraper = scraperFactory.createScraper(scraperType, search);
        List<Realty> searchResults = new LinkedList<>();
        while (true) {
            try {
                List<Realty> pageResults = scraper.scrapeNext();
                searchResults.addAll(pageResults);
                if (pageResults.isEmpty())
                    break;
                processScrapedRealties(search, pageResults);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processScrapedRealties(Search search, List<Realty> realties) {
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
                            .changeDate(LocalDateTime.now())
                            .delta(priceDelta)
                            .build();
                    priceChangeRepository.save(priceChange);
                    dbRealty.setPrice(realty.getPrice());
                    realtyRepository.save(dbRealty);
                }
            } else { // create new realty
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

}
