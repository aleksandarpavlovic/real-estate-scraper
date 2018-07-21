package scrape;

import realties.Realty;
import realties.enums.AdType;
import realties.enums.RealtyType;
import scrape.criteria.BaseCriteria;
import scrape.criteria.CriteriaDefinitions;
import scrape.criteria.SingleValueCriteria;

import java.io.IOException;
import java.util.List;

public abstract class Scraper {
    List<BaseCriteria> criteriaList;
    protected final AdType adType;
    protected final RealtyType realtyType;

    public Scraper(List<BaseCriteria> criteriaList) {
        this.criteriaList = criteriaList;
        this.adType = determineAdType(criteriaList);
        this.realtyType = determineRealtyType(criteriaList);
    }

    public abstract List<Realty> scrape() throws IOException;

    private AdType determineAdType(List<BaseCriteria> criteriaList) {
        for (BaseCriteria criteria: criteriaList) {
            if (CriteriaDefinitions.TIP_OGLASA.equals(criteria.getName())) {
                String adType = ((SingleValueCriteria<String>)criteria).getValue();
                if (CriteriaDefinitions.PRODAJA.equals(adType))
                    return AdType.SELL;
                else
                    return AdType.RENT;
            }
        }
        return null;
    }

    private RealtyType determineRealtyType(List<BaseCriteria> criteriaList) {
        for (BaseCriteria criteria: criteriaList) {
            if (CriteriaDefinitions.TIP_NEKRETNINE.equals(criteria.getName())) {
                String realtyType = ((SingleValueCriteria<String>)criteria).getValue();
                if (CriteriaDefinitions.STAN.equals(realtyType))
                    return RealtyType.APARTMENT;
                else if (CriteriaDefinitions.KUCA.equals(realtyType))
                    return RealtyType.HOUSE;
                else
                    return RealtyType.LAND;
            }
        }
        return null;
    }
}
