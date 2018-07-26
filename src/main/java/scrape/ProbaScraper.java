package scrape;

import realties.Realty;
import realties.enums.*;
import scrape.criteria.*;
import scrape.criteria.definitions.CriteriaDefinitions;
import scrape.halooglasi.HaloOglasiScraper;
import scrape.nekretnine_rs.NekretnineRsScraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProbaScraper {

    public static void main(String[] args) {
        ProbaScraper proba = new ProbaScraper();
        proba.scrape(new Search("search", testCriteria()));
    }

    private void scrape(Search search) {
        Scraper hoScraper = new HaloOglasiScraper(search);
        Scraper nrsScraper = new NekretnineRsScraper(search);
        try {
            List<Realty> hoResults = hoScraper.scrapeNext();
            List<Realty> nrsResults = nrsScraper.scrapeNext();
            List<Realty> nrsResults2 = nrsScraper.scrapeNext();
            hoResults.forEach(System.out::println);
            nrsResults.forEach(System.out::println);
            nrsResults2.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<BaseCriteria> testCriteria() {
        List<BaseCriteria> criteriaList = new ArrayList<>();
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.AD_TYPE, AdType.SELL));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.REALTY_TYPE, RealtyType.APARTMENT));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.REGISTERED, RegistrationType.REGISTERED));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.APARTMENT_TYPE, ApartmentType.DUPLEX));
        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.HEATING_TYPE, Arrays.asList(HeatingType.GAS)));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.PRICE, 12, 123123));
        criteriaList.add(new RangeWithUnitCriteria<>(CriteriaDefinitions.SURFACE_AREA, 12, 123, AreaMeasurementUnit.SQUARE_METER));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.ROOM_COUNT, RoomCount.RC_0_5, RoomCount.RC_5_0));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.FLOOR, CriteriaDefinitions.HIGH_GROUND_FLOOR, 1));
        return criteriaList;
    }
}
