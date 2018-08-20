package com.paki.scrape.scraper.halooglasi;

import com.paki.realties.enums.*;
import com.paki.scrape.criteria.*;
import com.paki.scrape.criteria.definitions.CriteriaDefinitions;

import java.util.*;

public class HaloOglasiCriteriaTransformer {
    private static final String STRING_CONCATENATOR = "|";

    private static final HashMap<String, String> criteriaDefinitionMappings;
    private static final HashMap<String, List<String>> criteriaDefinitionMultiMappings;
    private static final HashMap<String, String> urlMappings;
    private static final HashMap<String, Integer> roomCountMappings;
    private static final HashMap<String, Integer> unitIdMappings;
    private static final HashMap<Integer, Integer> floorMappings;

    private static final String URL_PRODAJA_STANOVA = "/nekretnine/prodaja-stanova";
    private static final String URL_IZDAVANJE_STANOVA = "/nekretnine/izdavanje-stanova";
    private static final String URL_PRODAJA_KUCA = "/nekretnine/prodaja-kuca";
    private static final String URL_IZDAVANJE_KUCA = "/nekretnine/izdavanje-kuca";
    private static final String URL_PRODAJA_PLACEVA = "/nekretnine/prodaja-zemljista";
    private static final String URL_IZDAVANJE_PLACEVA = "/nekretnine/izdavanje-zemljista";

    private static final String TA_PEC = "1544";
    private static final String NORVESKI_RADIJATOR = "1548";
    private static final String MERMERNI_RADIJATOR = "1549";

    private static final String LOKACIJA = "grad_id_l-lokacija_id_l-mikrolokacija_id_l";

    static {
        urlMappings = new HashMap<>();

        urlMappings.put(AdType.SELL + STRING_CONCATENATOR + RealtyType.APARTMENT, URL_PRODAJA_STANOVA);
        urlMappings.put(AdType.RENT + STRING_CONCATENATOR + RealtyType.APARTMENT, URL_IZDAVANJE_STANOVA);
        urlMappings.put(AdType.SELL + STRING_CONCATENATOR + RealtyType.HOUSE, URL_PRODAJA_KUCA);
        urlMappings.put(AdType.RENT + STRING_CONCATENATOR + RealtyType.HOUSE, URL_IZDAVANJE_KUCA);
        urlMappings.put(AdType.SELL + STRING_CONCATENATOR + RealtyType.LAND, URL_PRODAJA_PLACEVA);
        urlMappings.put(AdType.RENT + STRING_CONCATENATOR + RealtyType.LAND, URL_IZDAVANJE_PLACEVA);



        criteriaDefinitionMappings = new HashMap<>();
        criteriaDefinitionMultiMappings = new HashMap<>();

        criteriaDefinitionMappings.put(CriteriaDefinitions.ADVERTISER, "oglasivac_nekretnine_id_l");
        criteriaDefinitionMappings.put(AdvertiserType.AGENCY.name(), "387238");
        criteriaDefinitionMappings.put(AdvertiserType.OWNER.name(), "387237");
        criteriaDefinitionMappings.put(AdvertiserType.INVESTOR.name(), "387300");

        criteriaDefinitionMappings.put(CriteriaDefinitions.REALTY_TYPE, "CategoryIds");
        criteriaDefinitionMappings.put(RealtyType.APARTMENT.name(), "12");
        criteriaDefinitionMappings.put(RealtyType.HOUSE.name(), "24");
        criteriaDefinitionMappings.put(RealtyType.LAND.name(), "26");

        criteriaDefinitionMappings.put(CriteriaDefinitions.BUILD_TYPE, "tip_objekta_id_l");
        criteriaDefinitionMappings.put(BuildType.OLD_BUILD.name(), "387234");
        criteriaDefinitionMappings.put(BuildType.NEW_BUILD.name(), "387235");
        criteriaDefinitionMappings.put(BuildType.UNDER_CONSTRUCTION.name(), "387296");

        criteriaDefinitionMappings.put(CriteriaDefinitions.HEATING_TYPE, "grejanje_id_l");
        criteriaDefinitionMappings.put(HeatingType.CENTRAL.name(), "1542");
        criteriaDefinitionMappings.put(HeatingType.OWN_CENTRAL.name(), "1543");
        criteriaDefinitionMultiMappings.put(HeatingType.ELECTRIC.name(), Arrays.asList(TA_PEC, NORVESKI_RADIJATOR, MERMERNI_RADIJATOR));
        criteriaDefinitionMappings.put(HeatingType.GAS.name(), "1545");

        criteriaDefinitionMappings.put(CriteriaDefinitions.REGISTRATION, "dodatno_id_ls");
        criteriaDefinitionMappings.put(RegistrationType.REGISTERED.name(), "12000004");

        criteriaDefinitionMappings.put(CriteriaDefinitions.APARTMENT_TYPE, "dodatno_id_ls");
        criteriaDefinitionMappings.put(ApartmentType.WITH_SALON.name(), "12000012");
        criteriaDefinitionMappings.put(ApartmentType.DUPLEX.name(), "12000013");
        criteriaDefinitionMappings.put(ApartmentType.PENTHOUSE.name(), "12000014");
        criteriaDefinitionMappings.put(ApartmentType.LOFT.name(), "12000021");

        criteriaDefinitionMappings.put(CriteriaDefinitions.FACILITIES, "ostalo_id_ls");
        criteriaDefinitionMappings.put(Facilities.TERRASSE.name(), "12100001");
        criteriaDefinitionMappings.put(Facilities.LOGGIA.name(), "12100019");
        criteriaDefinitionMappings.put(Facilities.BALCONY.name(), "12100018");
        criteriaDefinitionMappings.put(Facilities.FRENCH_BALCONY.name(), "12100018");
        criteriaDefinitionMappings.put(Facilities.GARAGE.name(), "12100016");
        criteriaDefinitionMappings.put(Facilities.PARKING.name(), "12100017");

        criteriaDefinitionMappings.put(CriteriaDefinitions.ROOM_COUNT, "broj_soba_order_i");

        criteriaDefinitionMappings.put(CriteriaDefinitions.PRICE, "defaultunit_cena_d");

        criteriaDefinitionMappings.put(CriteriaDefinitions.SURFACE_M2, "defaultunit_kvadratura_d");
        criteriaDefinitionMappings.put(CriteriaDefinitions.SURFACE_ARE, "defaultunit_povrsina_d");

        criteriaDefinitionMappings.put(CriteriaDefinitions.FLOOR, "sprat_order_i");

        roomCountMappings = new HashMap<>();
        roomCountMappings.put(RoomCount.RC_0.name(), 1);
        roomCountMappings.put(RoomCount.RC_0_5.name(), 1);
        roomCountMappings.put(RoomCount.RC_1_0.name(), 2);
        roomCountMappings.put(RoomCount.RC_1_5.name(), 3);
        roomCountMappings.put(RoomCount.RC_2_0.name(), 4);
        roomCountMappings.put(RoomCount.RC_2_5.name(), 5);
        roomCountMappings.put(RoomCount.RC_3_0.name(), 7);
        roomCountMappings.put(RoomCount.RC_3_5.name(), 8);
        roomCountMappings.put(RoomCount.RC_4_0.name(), 9);
        roomCountMappings.put(RoomCount.RC_4_5.name(), 10);
        roomCountMappings.put(RoomCount.RC_5_0.name(), 11);
        roomCountMappings.put(RoomCount.RC_5_p.name(), 12);

        unitIdMappings = new HashMap<>();
        unitIdMappings.put(AreaMeasurementUnit.SQUARE_METER.name(), 1);
        unitIdMappings.put(AreaMeasurementUnit.ARE.name(), 2);

        floorMappings = new HashMap<>();
        floorMappings.put(CriteriaDefinitions.SUBTERRAIN, 1);
        floorMappings.put(CriteriaDefinitions.LOW_GROUND_FLOOR, 2);
        floorMappings.put(CriteriaDefinitions.GROUND_FLOOR, 3);
        floorMappings.put(CriteriaDefinitions.HIGH_GROUND_FLOOR, 8);
    }

    public HaloOglasiRequest transform(Set<? extends BaseCriteria> criteriaList) {
        PerRequestTransformer transformer = new PerRequestTransformer(criteriaList);
        return transformer.transform();
    }

    private class PerRequestTransformer {
        private AdType adType;
        private RealtyType realtyType;
        private final Set<? extends BaseCriteria> criteriaList;

        private PerRequestTransformer(Set<? extends BaseCriteria> criteriaList) {
            this.criteriaList = criteriaList;
        }

        private HaloOglasiRequest transform() {
            HaloOglasiRequest request = new HaloOglasiRequest();
            for (BaseCriteria criteria: criteriaList) {
                if (criteria instanceof SingleValueCriteria)
                    transformSingleValueCriteria((SingleValueCriteria) criteria, request);
                else if (criteria instanceof RangeCriteria)
                    transformRangeCriteria((RangeCriteria) criteria, request);
                else if (criteria instanceof MultiValueCriteria)
                    transformMultivalueCriteria((MultiValueCriteria) criteria, request);
            }
            request.setSearchTypeIds(Arrays.asList(2,3));
            request.setSortFields(Arrays.asList(new HaloOglasiRequest.SortField()));
            request.setFieldQueries(Collections.emptyList());
            request.setGeoPolygonQuery(new Object());
            request.setHasValueQueries(Collections.emptyList());
            if (request.getFieldORQueries() == null)
                request.setFieldORQueries(Collections.emptyList());
            if (request.getRangeQueries() == null)
                request.setRangeQueries(Collections.emptyList());
            if (request.getMultiFieldORQueries() == null)
                request.setMultiFieldORQueries(Collections.emptyList());
            return request;
        }
        private void transformSingleValueCriteria(SingleValueCriteria criteria, HaloOglasiRequest request) {
            String criteriaName = criteria.getName();
            if (CriteriaDefinitions.AD_TYPE.equals(criteriaName)) {
                this.adType = AdType.get(criteria.getValue());
                resolveBaseTaxonomy(request);
            } else if (CriteriaDefinitions.REALTY_TYPE.equals(criteriaName)) {
                this.realtyType = RealtyType.get(criteria.getValue());
                request.setCategoryId(criteriaDefinitionMappings.get(criteria.getValue()));
                request.updateFieldOrQueries(HaloOglasiRequest.FieldORQuery.from(criteriaDefinitionMappings.get(CriteriaDefinitions.REALTY_TYPE), criteriaDefinitionMappings.get(criteria.getValue())));
                resolveBaseTaxonomy(request);
            } else if (CriteriaDefinitions.APARTMENT_TYPE.equals(criteriaName)) {
                request.updateFieldOrQueries(HaloOglasiRequest.FieldORQuery.from(criteriaDefinitionMappings.get(CriteriaDefinitions.APARTMENT_TYPE), criteriaDefinitionMappings.get(criteria.getValue())));
            }
        }

        private void resolveBaseTaxonomy(HaloOglasiRequest request) {
            if (this.realtyType == null || this.adType == null)
                return;
            String baseTaxonomy = urlMappings.get(this.adType + STRING_CONCATENATOR + this.realtyType);
            if (baseTaxonomy == null)
                throw new RuntimeException("Pretraga za kombinaciju parametara " + this.adType + ", " + this.realtyType + " nije moguca!");
            request.setBaseTaxonomy(baseTaxonomy);
        }

        private void transformRangeCriteria(RangeCriteria criteria, HaloOglasiRequest request) {
            String criteriaName = criteria.getName();
            if (CriteriaDefinitions.ROOM_COUNT.equals(criteriaName))
                request.updateRangeQueries(
                        HaloOglasiRequest.RangeQuery.builder()
                        .fieldName(criteriaDefinitionMappings.get(criteriaName))
                        .from(roomCountMappings.get(criteria.getRangeFrom()))
                        .to(roomCountMappings.get(criteria.getRangeTo()))
                        .build()
                );
            else if (CriteriaDefinitions.PRICE.equals(criteriaName))
                request.updateRangeQueries(
                        HaloOglasiRequest.RangeQuery.builder()
                        .fieldName(criteriaDefinitionMappings.get(criteriaName))
                        .from((Integer)criteria.getRangeFrom())
                        .to((Integer)criteria.getRangeTo())
                        .unitId(4)
                        .build()
                );
            else if (CriteriaDefinitions.SURFACE_M2.equals(criteriaName) || CriteriaDefinitions.SURFACE_ARE.equals(criteriaName))
                request.updateRangeQueries(
                        HaloOglasiRequest.RangeQuery.builder()
                                .fieldName(criteriaDefinitionMappings.get(criteriaName))
                                .from((Integer)criteria.getRangeFrom())
                                .to((Integer)criteria.getRangeTo())
                                .unitId(unitIdMappings.getOrDefault(((RangeWithUnitCriteria)criteria).getUnit().name(), 1))
                                .build()
                );
            else if (CriteriaDefinitions.FLOOR.equals(criteriaName)) {
                request.updateRangeQueries(
                        HaloOglasiRequest.RangeQuery.builder()
                        .fieldName(criteriaDefinitionMappings.get(criteriaName))
                        .from(getFloorMapping((Integer)criteria.getRangeFrom()))
                        .to(getFloorMapping((Integer)criteria.getRangeTo()))
                        .build()
                );
            }
        }

        private void transformLocationCriteria(MultiValueCriteria criteria, HaloOglasiRequest request) {
            List<String> locations = new LinkedList<>();
            for (String locationId: criteria.getValues()) {
                List<String> haloOglasiLocation = HaloOglasiLocationMapper.getLocation(locationId);
                if (haloOglasiLocation != null)
                    locations.addAll(haloOglasiLocation);
            }
            if (!locations.isEmpty())
                request.updateMultiFieldOrQueries(new HaloOglasiRequest.FieldORQuery(LOKACIJA, locations));
        }

        private void transformMultivalueCriteria(MultiValueCriteria criteria, HaloOglasiRequest request) {
            if (CriteriaDefinitions.LOCATION.equals(criteria.getName())) {
                transformLocationCriteria(criteria, request);
            } else if (Arrays.asList(CriteriaDefinitions.ADVERTISER, CriteriaDefinitions.BUILD_TYPE, CriteriaDefinitions.HEATING_TYPE, CriteriaDefinitions.FACILITIES).contains(criteria.getName())) {
                request.updateFieldOrQueries(new HaloOglasiRequest.FieldORQuery(criteriaDefinitionMappings.get(criteria.getName()), getMappings(criteria.getValues())));
            } else if (CriteriaDefinitions.REGISTRATION.equals(criteria.getName())) {
                if (criteria.getValues().contains(RegistrationType.REGISTERED.name()))
                    request.updateFieldOrQueries(HaloOglasiRequest.FieldORQuery.from(criteriaDefinitionMappings.get(CriteriaDefinitions.REGISTRATION), criteriaDefinitionMappings.get(RegistrationType.REGISTERED.name())));
            }
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

        private Integer getFloorMapping(Integer floor) {
            if (floor == null)
                return null;
            Integer floorMapping = floorMappings.get(floor);
            if (floorMapping == null) {
                floorMapping = Integer.valueOf(floor) + 10;
            }
            return floorMapping;
        }
    }
}
