package scrape;

import realties.Realty;
import realties.enums.AdType;
import realties.enums.RealtyType;
import scrape.criteria.BaseCriteria;
import scrape.criteria.SingleValueCriteria;
import scrape.criteria.definitions.CriteriaDefinitions;

import java.io.IOException;
import java.util.List;

public abstract class Scraper {
    protected final Search search;
    protected final AdType adType;
    protected final RealtyType realtyType;

    public Scraper(Search search) {
        this.search = search;
        this.adType = determineAdType(search.getCriteria());
        this.realtyType = determineRealtyType(search.getCriteria());
    }

    public abstract List<Realty> scrapeNext() throws IOException;

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
