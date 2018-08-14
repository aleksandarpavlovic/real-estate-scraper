package com.paki.scrape.scraper;

import com.google.gson.*;
import com.paki.dto.realties.RealtyDTO;
import com.paki.dto.transformers.RealtiesDTOTransformer;
import com.paki.realties.Realty;
import com.paki.realties.enums.*;
import com.paki.realties.locations.LocationsGenerator;
import com.paki.scrape.criteria.*;
import com.paki.scrape.criteria.definitions.CriteriaDefinitions;
import com.paki.scrape.entities.Search;
import com.paki.scrape.scraper.halooglasi.HaloOglasiScraper;
import com.paki.scrape.scraper.nekretniners.NekretnineRsScraper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class ProbaScraper {

    public static void main(String[] args) {
        ProbaScraper proba = new ProbaScraper();
        proba.scrape(new Search(RealtyType.APARTMENT, testCriteria()));
    }

    private void scrape(Search search) {
        Scraper hoScraper = new HaloOglasiScraper(search);
        Scraper nrsScraper = new NekretnineRsScraper(search);
        try {
            Set<Realty> hoResults = hoScraper.scrapeNext();
            Set<Realty> nrsResults = nrsScraper.scrapeNext();
            Set<Realty> nrsResults2 = nrsScraper.scrapeNext();
//            hoResults.forEach(System.out::println);
//            nrsResults.forEach(System.out::println);
//            nrsResults2.forEach(System.out::println);



            class CollectionAdapter implements JsonSerializer<Collection<?>> {
                @Override
                public JsonElement serialize(Collection<?> src, Type typeOfSrc, JsonSerializationContext context) {
                    if (src == null || src.isEmpty()) // exclusion is made here
                        return null;

                    JsonArray array = new JsonArray();

                    for (Object child : src) {
                        JsonElement element = context.serialize(child);
                        array.add(element);
                    }

                    return array;
                }
            }

            RealtiesDTOTransformer transformer = new RealtiesDTOTransformer();
            List<RealtyDTO> dtos = hoResults.stream().map(transformer::transformRealtyToDTO).collect(Collectors.toList());
            Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Collection.class, new CollectionAdapter()).create();
            List<String> jsons = dtos.stream().map(gson::toJson).collect(Collectors.toList());
            jsons.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<BaseCriteria> testCriteria() {
        Set<BaseCriteria> criteriaList = new HashSet<>();
        criteriaList.add(new SingleValueCriteria(CriteriaDefinitions.AD_TYPE, AdType.SELL.name()));
        criteriaList.add(new SingleValueCriteria(CriteriaDefinitions.REALTY_TYPE, RealtyType.APARTMENT.name()));
        criteriaList.add(new MultiValueCriteria(CriteriaDefinitions.REGISTRATION, new HashSet<>(Arrays.asList(RegistrationType.REGISTERED.name()))));
        criteriaList.add(new SingleValueCriteria(CriteriaDefinitions.APARTMENT_TYPE, ApartmentType.DUPLEX.name()));
        criteriaList.add(new MultiValueCriteria(CriteriaDefinitions.HEATING_TYPE, new HashSet<>(Arrays.asList(HeatingType.GAS.name()))));
        criteriaList.add(new IntegerRangeCriteria(CriteriaDefinitions.PRICE, 12, 123123));
        criteriaList.add(new RangeWithUnitCriteria(CriteriaDefinitions.SURFACE_M2, 12, 123, AreaMeasurementUnit.SQUARE_METER));
        criteriaList.add(new StringRangeCriteria(CriteriaDefinitions.ROOM_COUNT, RoomCount.RC_0_5.name(), RoomCount.RC_5_0.name()));
        criteriaList.add(new IntegerRangeCriteria(CriteriaDefinitions.FLOOR, CriteriaDefinitions.HIGH_GROUND_FLOOR, 1));
        criteriaList.add(new MultiValueCriteria(CriteriaDefinitions.LOCATION, new HashSet<>(Arrays.asList(LocationsGenerator.getLocations().get(0).getId(), LocationsGenerator.getLocations().get(2).getId(), LocationsGenerator.getLocations().get(1).getSublocations().get(0).getId()))));

        return criteriaList;
    }
}
