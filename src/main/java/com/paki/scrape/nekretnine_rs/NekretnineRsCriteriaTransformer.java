package com.paki.scrape.nekretnine_rs;

import com.paki.realties.enums.*;
import com.paki.realties.locations.Location;
import com.paki.realties.util.UnitConversionUtil;
import com.paki.scrape.criteria.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.paki.scrape.criteria.definitions.CriteriaDefinitions.*;

public class NekretnineRsCriteriaTransformer {

    private static final String VALUE_SEPARATOR = "_";

    private static final HashMap<Object, String> criteriaDefinitionMappings;
    private static final HashMap<Object, List<String>> criteriaDefinitionMultiMappings;
    private static final HashMap<RoomCount, Integer> roomCountMappings;
    private static final List<String> pathOrder;
    private static final HashMap<Integer, String> lowFloorMappings;

    static {
        criteriaDefinitionMappings = new HashMap<>();
        roomCountMappings = new HashMap<>();
        criteriaDefinitionMultiMappings = new HashMap<>();

        criteriaDefinitionMappings.put(AdType.SELL, "prodaja");
        criteriaDefinitionMappings.put(AdType.RENT, "izdavanje");

        criteriaDefinitionMappings.put(RealtyType.APARTMENT, "stanovi");
        criteriaDefinitionMappings.put(RealtyType.HOUSE, "kuce");
        criteriaDefinitionMappings.put(RealtyType.LAND, "zemljista");

        criteriaDefinitionMappings.put(ApartmentType.WITH_SALON, "Salonac");
        criteriaDefinitionMappings.put(ApartmentType.DUPLEX, "dupleks");
        criteriaDefinitionMappings.put(ApartmentType.PENTHOUSE, "penthaus");
        criteriaDefinitionMappings.put(ApartmentType.LOFT, "Potkrovlje");
        criteriaDefinitionMappings.put(ApartmentType.WITH_YARD, "dvorisni-stan");

        criteriaDefinitionMappings.put(AdvertiserType.AGENCY, "Zastupnik");
        criteriaDefinitionMappings.put(AdvertiserType.OWNER, "Vlasnik");
        criteriaDefinitionMappings.put(AdvertiserType.INVESTOR, "Investitor");

        criteriaDefinitionMultiMappings.put(BuildType.OLD_BUILD, Arrays.asList("standardna", "starogradnja"));
        criteriaDefinitionMappings.put(BuildType.NEW_BUILD, "novogradnja");
        criteriaDefinitionMappings.put(BuildType.UNDER_CONSTRUCTION, "u-izgradnji");

        criteriaDefinitionMappings.put(Facilities.TERRASSE, "terasa");
        criteriaDefinitionMappings.put(Facilities.LOGGIA, "lodja");
        criteriaDefinitionMappings.put(Facilities.BALCONY, "balkon");
        criteriaDefinitionMappings.put(Facilities.FRENCH_BALCONY, "francuski-balkon");
        criteriaDefinitionMappings.put(Facilities.GARAGE, "garaza");
        criteriaDefinitionMappings.put(Facilities.PARKING, "parking");

        criteriaDefinitionMultiMappings.put(HeatingType.CENTRAL, Arrays.asList("centralno-grejanje", "daljinsko-grejanje"));
        criteriaDefinitionMappings.put(HeatingType.OWN_CENTRAL, "etazno-grejanje-grejno-telo");
        criteriaDefinitionMappings.put(HeatingType.ELECTRIC, "struja");
        criteriaDefinitionMappings.put(HeatingType.GAS, "gas");

        roomCountMappings.put(RoomCount.RC_0, 0);
        roomCountMappings.put(RoomCount.RC_0_5, 1);
        roomCountMappings.put(RoomCount.RC_1_0, 1);
        roomCountMappings.put(RoomCount.RC_1_5, 2);
        roomCountMappings.put(RoomCount.RC_2_0, 2);
        roomCountMappings.put(RoomCount.RC_2_5, 3);
        roomCountMappings.put(RoomCount.RC_3_0, 3);
        roomCountMappings.put(RoomCount.RC_3_5, 4);
        roomCountMappings.put(RoomCount.RC_4_0, 4);
        roomCountMappings.put(RoomCount.RC_4_5, 5);
        roomCountMappings.put(RoomCount.RC_5_0, 5);
        roomCountMappings.put(RoomCount.RC_5_p, 1000);

        pathOrder = Arrays.asList("stambeni-objekti", "zemljista", "izdavanje-prodaja", "grad", "deo-grada", "tip-stanovi", "prateci-objekti-povrsine", "stanje-objekta", "vrsta-grejanja", "vrsta-goriva", "kvadratura", "cena", "na-spratu", "sobe");

        lowFloorMappings = new HashMap<>();
        lowFloorMappings.put(SUBTERRAIN, "suteren");
        lowFloorMappings.put(LOW_GROUND_FLOOR, "nisko-prizemlje");
        lowFloorMappings.put(GROUND_FLOOR, "Prizemlje");
        lowFloorMappings.put(HIGH_GROUND_FLOOR, "visoko-prizemlje");
    }

    public NekretnineRsRequest transform(Set<? extends BaseCriteria> criteriaList) {
        NekretnineRsRequest request = new NekretnineRsRequest();
        for (BaseCriteria criteria: criteriaList) {
            if (criteria instanceof SingleValueCriteria)
                transformSingleValueCriteria((SingleValueCriteria) criteria, request);
            else if (criteria instanceof RangeCriteria)
                transformRangeCriteria((RangeCriteria) criteria, request);
            else if (criteria instanceof LocationCriteria)
                transformLocationCriteria((LocationCriteria) criteria, request);
            else if (criteria instanceof MultiValueCriteria)
                transformMultivalueCriteria((MultiValueCriteria) criteria, request);
        }
        request.setBaseUrl("https://www.nekretnine.rs");
        request.setPageNumber(1);
        request.setPathOrder(pathOrder);
        return request;
    }

    private void transformSingleValueCriteria(SingleValueCriteria criteria, NekretnineRsRequest request) {
        String criteriaName = criteria.getName();
        if (AD_TYPE.equals(criteriaName)) {
            request.updatePath(new NekretnineRsRequest.Path("izdavanje-prodaja", criteriaDefinitionMappings.get(criteria.getValue())));
        } else if (REALTY_TYPE.equals(criteriaName)) {
            if (Arrays.asList(RealtyType.APARTMENT, RealtyType.HOUSE).contains(criteria.getValue()))
                request.updatePath(new NekretnineRsRequest.Path("stambeni-objekti", criteriaDefinitionMappings.get(criteria.getValue())));
            else
                request.updatePath(new NekretnineRsRequest.Path("zemljista", null));
        } else if (REGISTRATION.equals(criteriaName)) {
            request.setRegistered(Optional.of(true));
        } else if (APARTMENT_TYPE.equals(criteriaName)) {
            request.updatePath(new NekretnineRsRequest.Path("tip-stanovi", criteriaDefinitionMappings.get(criteria.getValue())));
        }
    }

    private void transformRangeCriteria(RangeCriteria criteria, NekretnineRsRequest request) {
        String criteriaName = criteria.getName();
        if (ROOM_COUNT.equals(criteriaName)) {
            request.updatePath(new NekretnineRsRequest.Path("sobe", concatenate(roomCountMappings.get(criteria.getFrom()), roomCountMappings.get(criteria.getTo()))));
        } else if (PRICE.equals(criteriaName)) {
            request.updatePath(new NekretnineRsRequest.Path("cena", concatenate(criteria.getFrom(), criteria.getTo())));
        } else if (SURFACE_M2.equals(criteriaName) || SURFACE_ARE.equals(criteriaName)) {
            RangeWithUnitCriteria areaCriteria = (RangeWithUnitCriteria)criteria;
            request.updatePath(new NekretnineRsRequest.Path("kvadratura", concatenate(convertAreaToM2(areaCriteria.getFrom(), areaCriteria.getUnit()), convertAreaToM2(areaCriteria.getTo(), areaCriteria.getUnit()))));
        } else if (FLOOR.equals(criteriaName)) {
            updateFloors(criteria, request);
        }
    }

    private Integer convertAreaToM2(Integer value, AreaMeasurementUnit unit) {
        return UnitConversionUtil.convertArea(BigDecimal.valueOf(value), unit, AreaMeasurementUnit.SQUARE_METER).toBigInteger().intValueExact();
    }

    private void updateFloors(RangeCriteria<Integer> criteria, NekretnineRsRequest request) {
        if (criteria.getFrom() == null && criteria.getTo() == null)
            return;

        int lower = criteria.getFrom() != null ? criteria.getFrom() : SUBTERRAIN;
        int upper = criteria.getTo() != null ? criteria.getTo() : 1000;
        if (lower > 0) {
            request.updatePath(new NekretnineRsRequest.Path("na_spratu", concatenate(lower, upper)));
        } else {
            List<NekretnineRsRequest.Path> paths = IntStream.range(lower, Math.min(0, upper))
                    .mapToObj(lowFloorMappings::get)
                    .filter(e -> e != null)
                    .map(floor -> new NekretnineRsRequest.Path("tip-stanovi",floor))
                    .collect(Collectors.toList());
            if (upper > 0)
                paths.add(new NekretnineRsRequest.Path("na-spratu", concatenate(0, upper)));
            request.getDivergentPaths().add(new NekretnineRsRequest.DivergentPaths(paths));
        }
    }

    private void transformMultivalueCriteria(MultiValueCriteria criteria, NekretnineRsRequest request) {
        String criteriaName = criteria.getName();
        if (ADVERTISER.equals(criteriaName)) {
            List<String> mappings = (List<String>) criteria.getValues().stream().map(criteriaDefinitionMappings::get).collect(Collectors.toList());
            request.setAdvertiser(Optional.of(mappings));
        } else if (BUILD_TYPE.equals(criteriaName)) {
            request.updatePath(new NekretnineRsRequest.Path("stanje-objekta", concatenate(getMappings(criteria.getValues()))));
        } else if (HEATING_TYPE.equals(criteriaName)) {
            NekretnineRsRequest.Path heatingTypePath = new NekretnineRsRequest.Path("vrsta-grejanja", concatenate(getMappings(listIntersection(criteria.getValues(), Arrays.asList(HeatingType.CENTRAL.name(), HeatingType.OWN_CENTRAL.name())))));
            NekretnineRsRequest.Path fuelTypePath = new NekretnineRsRequest.Path("vrsta-goriva", concatenate(getMappings(listIntersection(criteria.getValues(), Arrays.asList(HeatingType.ELECTRIC.name(), HeatingType.GAS.name())))));
            if (heatingTypePath.getValue().isEmpty()) {
                request.updatePath(fuelTypePath);
            } else if (fuelTypePath.getValue().isEmpty()) {
                request.updatePath(heatingTypePath);
            } else {
                request.getDivergentPaths().add(new NekretnineRsRequest.DivergentPaths(Arrays.asList(heatingTypePath, fuelTypePath)));
            }
        } else if (FACILITIES.equals(criteriaName)) {
            request.updatePath(new NekretnineRsRequest.Path("prateci-objekti-povrsine", concatenate(getMappings(criteria.getValues()))));
        }
    }

    public void transformLocationCriteria(LocationCriteria criteria, NekretnineRsRequest request) {
        List<String> sublocations = new LinkedList<>();
        List<String> cities = new LinkedList<>();
        for (Location location: criteria.getLocations()) {
            String sublocation = NekretnineRsLocationMapper.getLocation(location.getId());
            if (sublocation != null)
                sublocations.add(sublocation);
            else {
                String city = NekretnineRsLocationMapper.getCity(location.getId());
                if (city != null)
                    cities.add(city);
            }
        }
        if (!cities.isEmpty())
            request.updatePath(new NekretnineRsRequest.Path("grad", concatenate(cities)));
        if (!sublocations.isEmpty())
            request.updatePath(new NekretnineRsRequest.Path("deo-grada", concatenate(sublocations)));
    }

    private List<String> listIntersection(List<String> first, List<String> second) {
        List<String> firstCopy = new ArrayList<>(first);
        firstCopy.retainAll(second);
        return firstCopy;
    }

    private String concatenate(List<String> values) {
        return String.join(VALUE_SEPARATOR, values);
    }

    private <T> String concatenate(T value1, T value2) {
        return value1.toString() + VALUE_SEPARATOR + value2.toString();
    }

    private List<String> getMappings(List<String> values) {
        List<String> mappings = new ArrayList<>();
        for (Object value: values) {
            String mapping = criteriaDefinitionMappings.get(value);
            if (mapping != null)
                mappings.add(mapping);
            else {
                List<String> multiMapping = criteriaDefinitionMultiMappings.get(value);
                if (multiMapping != null)
                    mappings.addAll(multiMapping);
            }
        }
        return mappings;
    }
}
