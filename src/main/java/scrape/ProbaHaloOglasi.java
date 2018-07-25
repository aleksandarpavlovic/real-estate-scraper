package scrape;

import realties.enums.*;
import scrape.criteria.BaseCriteria;
import scrape.criteria.MultivalueCriteria;
import scrape.criteria.RangeCriteria;
import scrape.criteria.SingleValueCriteria;
import scrape.criteria.definitions.CriteriaDefinitions;
import scrape.halooglasi.HaloOglasiScraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProbaHaloOglasi {

    public static void main(String[] args) {
        HaloOglasiScraper scraper = new HaloOglasiScraper(new Search("pretraga", testCriteria()));
        try {
            scraper.scrapeNext().stream().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<BaseCriteria> testCriteria() {
        List<BaseCriteria> criteriaList = new ArrayList<>();
//        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.AD_TYPE, CriteriaDefinitions.PRODAJA));
//        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.REALTY_TYPE, CriteriaDefinitions.STAN));
//        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.REGISTERED, true));
//        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.ADVERTISER, Arrays.asList(CriteriaDefinitions.AGENCIJA, CriteriaDefinitions.VLASNIK, CriteriaDefinitions.INVESTITOR)));
//        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.HEATING_TYPE, Arrays.asList(CriteriaDefinitions.STRUJA)));
//        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.FACILITIES, Arrays.asList(CriteriaDefinitions.TERASA)));
//        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.BUILD_TYPE, Arrays.asList(CriteriaDefinitions.NOVOGRADNJA, CriteriaDefinitions.IZGRADNJA, CriteriaDefinitions.STAROGRADNJA)));
//        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.PRICE, 12, 123123));
//        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.SURFACE_AREA, 12, 123));
//        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.ROOM_COUNT, CriteriaDefinitions.BROJ_SOBA_1_5, CriteriaDefinitions.BROJ_SOBA_3_5));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.AD_TYPE, AdType.SELL));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.REALTY_TYPE, RealtyType.APARTMENT));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.REGISTERED, RegistrationType.REGISTERED));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.APARTMENT_TYPE, ApartmentType.DUPLEX));
        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.HEATING_TYPE, Arrays.asList(HeatingType.GAS)));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.PRICE, 12, 123123));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.SURFACE_AREA, 12, 123));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.ROOM_COUNT, RoomCount.RC_0_5, RoomCount.RC_5_0));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.FLOOR, CriteriaDefinitions.HIGH_GROUND_FLOOR, 20));
        return criteriaList;
    }
}
