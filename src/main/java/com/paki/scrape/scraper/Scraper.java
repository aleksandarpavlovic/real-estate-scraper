package com.paki.scrape.scraper;

import com.paki.realties.Realty;
import com.paki.realties.Source;
import com.paki.realties.enums.AdType;
import com.paki.realties.enums.AreaMeasurementUnit;
import com.paki.realties.enums.RealtyType;
import com.paki.realties.util.UnitConversionUtil;
import com.paki.scrape.criteria.BaseCriteria;
import com.paki.scrape.criteria.RangeWithUnitCriteria;
import com.paki.scrape.criteria.SingleValueCriteria;
import com.paki.scrape.criteria.definitions.CriteriaDefinitions;
import com.paki.scrape.entities.Search;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Scraper {
    protected final Source source;
    protected final Search search;
    protected final AdType adType;
    protected final RealtyType realtyType;

    private final int MAX_RETRY = 3;
    private final int SLEEP_PERIOD = 2000;

    public Scraper(Source source, Search search) {
        this.source = source;
        this.search = search;
        this.adType = determineAdType(search.getCriteria());
        this.realtyType = determineRealtyType(search.getCriteria());
    }

    public final Set<Realty> scrapeNext() throws IOException {
        return filterResults(scrapeNextWithRetry());
    }

    public final Set<Realty> scrapeAdditionalFields(Set<Realty> realties) {
        Set<Realty> updatedRealties = new HashSet<>();
        for (Realty realty: realties) {
            scrapeAdditionalFieldsWithRetry(realty).ifPresent(updatedRealties::add);
        }
        return updatedRealties;
    }

    private Optional<Realty> scrapeAdditionalFieldsWithRetry(Realty realty) {
        int tryCount = 0;
        while (tryCount < MAX_RETRY) {
            tryCount++;
            try {
                return doScrapeAdditionalFields(realty);
            } catch (IOException | NullPointerException e) {
                if (tryCount == MAX_RETRY) {
                    System.out.println(String.format(this.toString() + " failed to populate additional fields after %d attemps", MAX_RETRY));
                    return Optional.empty();
                } else {
                    try {
                        // sleep some time in order to prevent service denial
                        System.out.println(String.format(this.toString() + " failed scraping of page for ad: %s in attempt #%d. Will reattempt in %d seconds...", realty.getExternalId(), tryCount, tryCount * SLEEP_PERIOD / 1000));
                        Thread.sleep(SLEEP_PERIOD * tryCount);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        return Optional.empty();
    }

    private Set<Realty> scrapeNextWithRetry() throws IOException {
        int tryCount = 0;
        while (tryCount < MAX_RETRY) {
            tryCount++;
            try {
                return doScrapeNext();
            } catch (IOException e) {
                if (tryCount == MAX_RETRY) {
                    System.out.println(String.format(this.toString() + " failed to scrape page after %d attemps. Aborting this scraper...", MAX_RETRY));
                    throw e;
                }
                else {
                    try {
                        // sleep some time in order to prevent service denial
                        System.out.println(String.format(this.toString() +  "failed scraping of ads page in attempt #%d. Will reattempt in %d seconds...", tryCount, tryCount * SLEEP_PERIOD / 1000));
                        Thread.sleep(SLEEP_PERIOD * tryCount);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        return Collections.emptySet();
    }

    protected abstract Set<Realty> doScrapeNext() throws IOException;

    protected abstract Optional<Realty> doScrapeAdditionalFields(Realty realty) throws IOException;

    private Set<Realty> filterResults(Set<Realty> results) {
        Set<Realty> filteredResults = results;
        for (BaseCriteria criteria: search.getCriteria()) {
            if (CriteriaDefinitions.PRICE_PER_M2.equals(criteria.getName()) || CriteriaDefinitions.PRICE_PER_ARE.equals(criteria.getName())) {
                RangeWithUnitCriteria rangeCriteria = (RangeWithUnitCriteria) criteria;
                AreaMeasurementUnit criteriaUnit = rangeCriteria.getUnit();
                BigDecimal from = BigDecimal.valueOf(rangeCriteria.getRangeFrom() != null ? rangeCriteria.getRangeFrom() : 0);
                BigDecimal to = BigDecimal.valueOf(rangeCriteria.getRangeTo() != null ? rangeCriteria.getRangeTo() : Long.MAX_VALUE);
                filteredResults = filteredResults.stream().filter(realty -> isPricePerAreaUnitInRange(realty, from, to, criteriaUnit)).collect(Collectors.toSet());
                break;
            }
        }
        return filteredResults.stream().filter(realty -> realty.getExternalId() != null).collect(Collectors.toSet());
    }

    private boolean isPricePerAreaUnitInRange(Realty realty, BigDecimal from, BigDecimal to, AreaMeasurementUnit rangeUnit) {
        if (realty.getSurfaceArea() == null || realty.getPrice() == null)
            return true;
        BigDecimal pricePerAreaUnit = realty.getPrice().divide(realty.getSurfaceArea(), 2, RoundingMode.CEILING);
        return isInRange(UnitConversionUtil.convertArea(pricePerAreaUnit, rangeUnit, realty.getAreaMeasurementUnit()), from, to);
    }

    private boolean isInRange(BigDecimal value, BigDecimal from, BigDecimal to) {
        return value.compareTo(from) >= 0
                && value.compareTo(to) <= 0;
    }

    private AdType determineAdType(Set<BaseCriteria> criteriaList) {
        for (BaseCriteria criteria: criteriaList) {
            if (CriteriaDefinitions.AD_TYPE.equals(criteria.getName())) {
                AdType adType = AdType.get(((SingleValueCriteria)criteria).getValue());
                if (AdType.SELL == adType)
                    return AdType.SELL;
                else
                    return AdType.RENT;
            }
        }
        return null;
    }

    private RealtyType determineRealtyType(Set<BaseCriteria> criteriaList) {
        for (BaseCriteria criteria: criteriaList) {
            if (CriteriaDefinitions.REALTY_TYPE.equals(criteria.getName())) {
                return RealtyType.get(((SingleValueCriteria)criteria).getValue());
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Abstract scraper";
    }
}
