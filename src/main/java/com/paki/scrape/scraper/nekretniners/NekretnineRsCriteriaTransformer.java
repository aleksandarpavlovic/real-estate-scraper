package com.paki.scrape.scraper.nekretniners;

import com.paki.realties.enums.*;
import com.paki.realties.util.UnitConversionUtil;
import com.paki.scrape.criteria.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.paki.scrape.criteria.definitions.CriteriaDefinitions.*;

public class NekretnineRsCriteriaTransformer {

    private static final String VALUE_SEPARATOR = "_";

    private static final HashMap<String, String> criteriaDefinitionMappings;
    private static final HashMap<String, List<String>> criteriaDefinitionMultiMappings;
    private static final HashMap<String, Integer> roomCountMappings;
    private static final List<String> pathOrder;
    private static final HashMap<Integer, String> lowFloorMappings;

    static {
        criteriaDefinitionMappings = new HashMap<>();
        roomCountMappings = new HashMap<>();
        criteriaDefinitionMultiMappings = new HashMap<>();

        criteriaDefinitionMappings.put(AdType.SELL.name(), "prodaja");
        criteriaDefinitionMappings.put(AdType.RENT.name(), "izdavanje");

        criteriaDefinitionMappings.put(RealtyType.APARTMENT.name(), "stanovi");
        criteriaDefinitionMappings.put(RealtyType.HOUSE.name(), "kuce");
        criteriaDefinitionMappings.put(RealtyType.LAND.name(), "zemljista");

        criteriaDefinitionMappings.put(ApartmentType.WITH_SALON.name(), "Salonac");
        criteriaDefinitionMappings.put(ApartmentType.DUPLEX.name(), "dupleks");
        criteriaDefinitionMappings.put(ApartmentType.PENTHOUSE.name(), "penthaus");
        criteriaDefinitionMappings.put(ApartmentType.LOFT.name(), "Potkrovlje");
        criteriaDefinitionMappings.put(ApartmentType.WITH_YARD.name(), "dvorisni-stan");

        criteriaDefinitionMappings.put(AdvertiserType.AGENCY.name(), "Zastupnik");
        criteriaDefinitionMappings.put(AdvertiserType.OWNER.name(), "Vlasnik");
        criteriaDefinitionMappings.put(AdvertiserType.INVESTOR.name(), "Investitor");

        criteriaDefinitionMultiMappings.put(BuildType.OLD_BUILD.name(), Arrays.asList("standardna", "starogradnja"));
        criteriaDefinitionMappings.put(BuildType.NEW_BUILD.name(), "novogradnja");
        criteriaDefinitionMappings.put(BuildType.UNDER_CONSTRUCTION.name(), "u-izgradnji");

        criteriaDefinitionMappings.put(Facilities.TERRASSE.name(), "terasa");
        criteriaDefinitionMappings.put(Facilities.LOGGIA.name(), "lodja");
        criteriaDefinitionMappings.put(Facilities.BALCONY.name(), "balkon");
        criteriaDefinitionMappings.put(Facilities.FRENCH_BALCONY.name(), "francuski-balkon");
        criteriaDefinitionMappings.put(Facilities.GARAGE.name(), "garaza");
        criteriaDefinitionMappings.put(Facilities.PARKING.name(), "parking");

        criteriaDefinitionMultiMappings.put(HeatingType.CENTRAL.name(), Arrays.asList("centralno-grejanje", "daljinsko-grejanje"));
        criteriaDefinitionMappings.put(HeatingType.OWN_CENTRAL.name(), "etazno-grejanje-grejno-telo");
        criteriaDefinitionMappings.put(HeatingType.ELECTRIC.name(), "struja");
        criteriaDefinitionMappings.put(HeatingType.GAS.name(), "gas");

        roomCountMappings.put(RoomCount.RC_0.name(), 0);
        roomCountMappings.put(RoomCount.RC_0_5.name(), 1);
        roomCountMappings.put(RoomCount.RC_1_0.name(), 1);
        roomCountMappings.put(RoomCount.RC_1_5.name(), 2);
        roomCountMappings.put(RoomCount.RC_2_0.name(), 2);
        roomCountMappings.put(RoomCount.RC_2_5.name(), 3);
        roomCountMappings.put(RoomCount.RC_3_0.name(), 3);
        roomCountMappings.put(RoomCount.RC_3_5.name(), 4);
        roomCountMappings.put(RoomCount.RC_4_0.name(), 4);
        roomCountMappings.put(RoomCount.RC_4_5.name(), 5);
        roomCountMappings.put(RoomCount.RC_5_0.name(), 5);
        roomCountMappings.put(RoomCount.RC_5_p.name(), 1000);

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
            if (Arrays.asList(RealtyType.APARTMENT.name(), RealtyType.HOUSE.name()).contains(criteria.getValue()))
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
            request.updatePath(new NekretnineRsRequest.Path("sobe", concatenate(roomCountMappings.get(criteria.getRangeFrom()), roomCountMappings.get(criteria.getRangeTo()))));
        } else if (PRICE.equals(criteriaName)) {
            request.updatePath(new NekretnineRsRequest.Path("cena", concatenate(criteria.getRangeFrom(), criteria.getRangeTo())));
        } else if (SURFACE_M2.equals(criteriaName) || SURFACE_ARE.equals(criteriaName)) {
            RangeWithUnitCriteria areaCriteria = (RangeWithUnitCriteria)criteria;
            request.updatePath(new NekretnineRsRequest.Path("kvadratura", concatenate(convertAreaToM2(areaCriteria.getRangeFrom(), areaCriteria.getUnit()), convertAreaToM2(areaCriteria.getRangeTo(), areaCriteria.getUnit()))));
        } else if (FLOOR.equals(criteriaName)) {
            updateFloors(criteria, request);
        }
    }

    private Integer convertAreaToM2(Integer value, AreaMeasurementUnit unit) {
        return UnitConversionUtil.convertArea(BigDecimal.valueOf(value), unit, AreaMeasurementUnit.SQUARE_METER).toBigInteger().intValueExact();
    }

    private void updateFloors(RangeCriteria<Integer> criteria, NekretnineRsRequest request) {
        if (criteria.getRangeFrom() == null && criteria.getRangeTo() == null)
            return;

        int lower = criteria.getRangeFrom() != null ? criteria.getRangeFrom() : SUBTERRAIN;
        int upper = criteria.getRangeTo() != null ? criteria.getRangeTo() : 1000;
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
            List<String> mappings = criteria.getValues().stream().map(criteriaDefinitionMappings::get).collect(Collectors.toList());
            request.setAdvertiser(Optional.of(mappings));
        } else if (BUILD_TYPE.equals(criteriaName)) {
            request.updatePath(new NekretnineRsRequest.Path("stanje-objekta", concatenate(getMappings(criteria.getValues()))));
        } else if (HEATING_TYPE.equals(criteriaName)) {
            NekretnineRsRequest.Path heatingTypePath = new NekretnineRsRequest.Path("vrsta-grejanja", concatenate(getMappings(intersection(criteria.getValues(), Arrays.asList(HeatingType.CENTRAL.name(), HeatingType.OWN_CENTRAL.name())))));
            NekretnineRsRequest.Path fuelTypePath = new NekretnineRsRequest.Path("vrsta-goriva", concatenate(getMappings(intersection(criteria.getValues(), Arrays.asList(HeatingType.ELECTRIC.name(), HeatingType.GAS.name())))));
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
        for (String locationId: criteria.getLocations()) {
            String sublocation = NekretnineRsLocationMapper.getLocation(locationId);
            if (sublocation != null)
                sublocations.add(sublocation);
            else {
                String city = NekretnineRsLocationMapper.getCity(locationId);
                if (city != null)
                    cities.add(city);
            }
        }
        List<NekretnineRsRequest.Path> locationPaths = new ArrayList<>();
        if (!cities.isEmpty())
            locationPaths.add(new NekretnineRsRequest.Path("grad", concatenate(cities)));
        if (!sublocations.isEmpty())
            locationPaths.add(new NekretnineRsRequest.Path("deo-grada", concatenate(sublocations)));
        request.getDivergentPaths().add(new NekretnineRsRequest.DivergentPaths(locationPaths));
    }

    private Set<String> intersection(Collection<String> first, Collection<String> second) {
        Set<String> firstCopy = new HashSet<>(first);
        firstCopy.retainAll(second);
        return firstCopy;
    }

    private String concatenate(List<String> values) {
        return String.join(VALUE_SEPARATOR, values);
    }

    private <T> String concatenate(T value1, T value2) {
        return value1.toString() + VALUE_SEPARATOR + value2.toString();
    }

    private List<String> getMappings(Set<String> values) {
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
