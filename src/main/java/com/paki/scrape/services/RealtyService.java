package com.paki.scrape.services;

import com.paki.persistence.realties.*;
import com.paki.persistence.scrape.RealtySearchRelationRepository;
import com.paki.persistence.scrape.SearchRepository;
import com.paki.realties.Realty;
import com.paki.realties.RealtyPriceChange;
import com.paki.realties.enums.RealtyType;
import com.paki.scrape.entities.RealtySearchRelation;
import com.paki.scrape.entities.ScrapeInfo;
import com.paki.scrape.entities.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RealtyService {
    private ApartmentRepository apartmentRepository;
    private HouseRepository houseRepository;
    private LandRepository landRepository;
    private RealtyPriceChangeRepository priceChangeRepository;
    private RealtySearchRelationRepository realtySearchRepository;
    private SearchRepository searchRepository;

    private CriteriaService criteriaService;

    @Autowired
    public RealtyService(ApartmentRepository apartmentRepository, HouseRepository houseRepository, LandRepository landRepository, RealtyPriceChangeRepository priceChangeRepository, RealtySearchRelationRepository realtySearchRepository, CriteriaService criteriaService, SearchRepository searchRepository) {
        this.apartmentRepository = apartmentRepository;
        this.houseRepository = houseRepository;
        this.landRepository = landRepository;
        this.priceChangeRepository = priceChangeRepository;
        this.realtySearchRepository = realtySearchRepository;
        this.criteriaService = criteriaService;
        this.searchRepository = searchRepository;
    }

    public Page<? extends Realty> findPaginatedAndSorted(Long searchId, Pageable pageRequest) {
        Optional<Search> search = searchRepository.findById(searchId);
        if (!search.isPresent()) {
            return Page.empty();
        }

        RealtyRepository realtyRepository = inferRealtyRepository(search.get());
        return realtyRepository.findBySearchId(searchId, pageRequest);
    }

    public Set<Realty> processScrapedRealties(ScrapeInfo scrapeInfo, Search search, Set<Realty> realties) {
        RealtyRepository<? extends Realty> realtyRepository = inferRealtyRepository(search);
        Map<String, ? extends Realty> dbRealtiesMap = realtyRepository
                .findByExternalIdIn(mapToIdList(realties))
                .stream()
                .collect(Collectors.toMap(Realty::getExternalId, r -> r));

        Set<Realty> newRealties = new HashSet<>();

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
                newRealties.add(dbRealty);
            }
            realtySearchRepository.save(new RealtySearchRelation(dbRealty, search));
        }

        return newRealties;
    }

    public Set<Realty> updateRealties(Search search, Set<Realty> realties) {
        RealtyRepository<? extends Realty> realtyRepository = inferRealtyRepository(search);
        return new HashSet<>(realtyRepository.saveAll(realties));
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

    private List<String> mapToIdList(Set<Realty> realties) {
        return realties.stream().map(Realty::getExternalId).collect(Collectors.toList());
    }
}
