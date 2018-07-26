package scrape;

import realties.Realty;
import realties.enums.AdType;
import realties.enums.AreaMeasurementUnit;
import realties.enums.RealtyType;
import realties.util.UnitConversionUtil;
import scrape.criteria.BaseCriteria;
import scrape.criteria.RangeWithUnitCriteria;
import scrape.criteria.SingleValueCriteria;
import scrape.criteria.definitions.CriteriaDefinitions;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Scraper {
    protected final Search search;
    protected final AdType adType;
    protected final RealtyType realtyType;

    public Scraper(Search search) {
        this.search = search;
        this.adType = determineAdType(search.getCriteria());
        this.realtyType = determineRealtyType(search.getCriteria());
    }

    public List<Realty> scrapeNext() throws IOException {
        return filterResults(doScrapeNext());
    }

    protected abstract List<Realty> doScrapeNext() throws IOException;

    private List<Realty> filterResults(List<Realty> results) {
        List<Realty> filteredResults = results;
        for (BaseCriteria criteria: search.getCriteria()) {
            if (CriteriaDefinitions.PRICE_PER_AREA_UNIT.equals(criteria.getName())) {
                AreaMeasurementUnit criteriaUnit = ((RangeWithUnitCriteria<Integer, AreaMeasurementUnit>)criteria).getUnit();
                BigDecimal from = BigDecimal.valueOf(((RangeWithUnitCriteria<Integer, AreaMeasurementUnit>) criteria).getFrom());
                BigDecimal to = BigDecimal.valueOf(((RangeWithUnitCriteria<Integer, AreaMeasurementUnit>) criteria).getTo());
                filteredResults = filteredResults.stream().filter(realty -> isPricePerAreaUnitInRange(realty, from, to, criteriaUnit)).collect(Collectors.toList());
            }
            break;
        }
        return filteredResults;
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

    private AdType determineAdType(List<BaseCriteria> criteriaList) {
        for (BaseCriteria criteria: criteriaList) {
            if (CriteriaDefinitions.AD_TYPE.equals(criteria.getName())) {
                AdType adType = ((SingleValueCriteria<AdType>)criteria).getValue();
                if (AdType.SELL == adType)
                    return AdType.SELL;
                else
                    return AdType.RENT;
            }
        }
        return null;
    }

    private RealtyType determineRealtyType(List<BaseCriteria> criteriaList) {
        for (BaseCriteria criteria: criteriaList) {
            if (CriteriaDefinitions.REALTY_TYPE.equals(criteria.getName())) {
                return ((SingleValueCriteria<RealtyType>)criteria).getValue();
            }
        }
        return null;
    }
}
