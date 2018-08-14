package com.paki.scrape.scraper;

import com.paki.realties.enums.*;
import com.paki.scrape.criteria.*;
import com.paki.scrape.criteria.definitions.CriteriaDefinitions;
import com.paki.scrape.entities.Search;
import com.paki.scrape.scraper.halooglasi.HaloOglasiScraper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ProbaHaloOglasi {

    public static void main(String[] args) {
        HaloOglasiScraper scraper = new HaloOglasiScraper(new Search(RealtyType.APARTMENT, testCriteria()));
        try {
            scraper.scrapeNext().stream().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<BaseCriteria> testCriteria() {
        Set<BaseCriteria> criteriaList = new HashSet<>();
//        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.AD_TYPE, CriteriaDefinitions.PRODAJA));
//        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.REALTY_TYPE, CriteriaDefinitions.STAN));
//        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.REGISTRATION, true));
//        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.ADVERTISER, Arrays.asList(CriteriaDefinitions.AGENCIJA, CriteriaDefinitions.VLASNIK, CriteriaDefinitions.INVESTITOR)));
//        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.HEATING_TYPE, Arrays.asList(CriteriaDefinitions.STRUJA)));
//        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.FACILITIES, Arrays.asList(CriteriaDefinitions.TERASA)));
//        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.BUILD_TYPE, Arrays.asList(CriteriaDefinitions.NOVOGRADNJA, CriteriaDefinitions.IZGRADNJA, CriteriaDefinitions.STAROGRADNJA)));
//        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.PRICE, 12, 123123));
//        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.SURFACE_AREA, 12, 123));
//        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.ROOM_COUNT, CriteriaDefinitions.BROJ_SOBA_1_5, CriteriaDefinitions.BROJ_SOBA_3_5));
        criteriaList.add(new SingleValueCriteria(CriteriaDefinitions.AD_TYPE, AdType.SELL.toString()));
        criteriaList.add(new SingleValueCriteria(CriteriaDefinitions.REALTY_TYPE, RealtyType.APARTMENT.name()));
        criteriaList.add(new MultiValueCriteria(CriteriaDefinitions.REGISTRATION, new HashSet<>(Arrays.asList(RegistrationType.REGISTERED.name()))));
        criteriaList.add(new SingleValueCriteria(CriteriaDefinitions.APARTMENT_TYPE, ApartmentType.DUPLEX.name()));
        criteriaList.add(new MultiValueCriteria(CriteriaDefinitions.HEATING_TYPE, new HashSet<>(Arrays.asList(HeatingType.GAS.name()))));
        criteriaList.add(new IntegerRangeCriteria(CriteriaDefinitions.PRICE, 12, 123123));
        criteriaList.add(new RangeWithUnitCriteria(CriteriaDefinitions.SURFACE_M2, 12, 123, AreaMeasurementUnit.SQUARE_METER));
        criteriaList.add(new StringRangeCriteria(CriteriaDefinitions.ROOM_COUNT, RoomCount.RC_0_5.name(), RoomCount.RC_5_0.name()));
        criteriaList.add(new IntegerRangeCriteria(CriteriaDefinitions.FLOOR, CriteriaDefinitions.HIGH_GROUND_FLOOR, 20));
        return criteriaList;
    }
}
