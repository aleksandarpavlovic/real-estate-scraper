package com.paki.scrape;

import com.paki.realties.Realty;
import com.paki.realties.enums.*;
import com.paki.scrape.criteria.*;
import com.paki.scrape.criteria.definitions.CriteriaDefinitions;
import com.paki.scrape.halooglasi.HaloOglasiScraper;
import com.paki.scrape.nekretnine_rs.NekretnineRsScraper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProbaScraper {

    public static void main(String[] args) {
        ProbaScraper proba = new ProbaScraper();
        proba.scrape(new Search(testCriteria()));
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

    public static Set<BaseCriteria> testCriteria() {
        Set<BaseCriteria> criteriaList = new HashSet<>();
        criteriaList.add(new SingleValueCriteria(CriteriaDefinitions.AD_TYPE, AdType.SELL.name()));
        criteriaList.add(new SingleValueCriteria(CriteriaDefinitions.REALTY_TYPE, RealtyType.APARTMENT.name()));
        criteriaList.add(new SingleValueCriteria(CriteriaDefinitions.REGISTRATION, RegistrationType.REGISTERED.name()));
        criteriaList.add(new SingleValueCriteria(CriteriaDefinitions.APARTMENT_TYPE, ApartmentType.DUPLEX.name()));
        criteriaList.add(new MultiValueCriteria(CriteriaDefinitions.HEATING_TYPE, Arrays.asList(HeatingType.GAS.name())));
        criteriaList.add(new IntegerRangeCriteria(CriteriaDefinitions.PRICE, 12, 123123));
        criteriaList.add(new RangeWithUnitCriteria(CriteriaDefinitions.SURFACE_M2, 12, 123, AreaMeasurementUnit.SQUARE_METER));
        criteriaList.add(new StringRangeCriteria(CriteriaDefinitions.ROOM_COUNT, RoomCount.RC_0_5.name(), RoomCount.RC_5_0.name()));
        criteriaList.add(new IntegerRangeCriteria(CriteriaDefinitions.FLOOR, CriteriaDefinitions.HIGH_GROUND_FLOOR, 1));
        return criteriaList;
    }
}
