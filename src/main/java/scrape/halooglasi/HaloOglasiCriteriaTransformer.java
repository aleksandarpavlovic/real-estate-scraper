package scrape.halooglasi;

import realties.enums.*;
import scrape.criteria.BaseCriteria;
import scrape.criteria.MultivalueCriteria;
import scrape.criteria.RangeCriteria;
import scrape.criteria.SingleValueCriteria;

import java.util.*;

import static scrape.criteria.definitions.CriteriaDefinitions.*;

public class HaloOglasiCriteriaTransformer {
    private static final String STRING_CONCATENATOR = "|";

    private static final HashMap<Object, String> criteriaDefinitionMappings;
    private static final HashMap<Object, List<String>> criteriaDefinitionMultiMappings;
    private static final HashMap<String, String> urlMappings;
    private static final HashMap<RoomCount, Integer> roomCountMappings;
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

        criteriaDefinitionMappings.put(ADVERTISER, "oglasivac_nekretnine_id_l");
        criteriaDefinitionMappings.put(AdvertiserType.AGENCY, "387238");
        criteriaDefinitionMappings.put(AdvertiserType.OWNER, "387237");
        criteriaDefinitionMappings.put(AdvertiserType.INVESTOR, "387300");

        criteriaDefinitionMappings.put(REALTY_TYPE, "CategoryIds");
        criteriaDefinitionMappings.put(RealtyType.APARTMENT, "12");
        criteriaDefinitionMappings.put(RealtyType.HOUSE, "24");
        criteriaDefinitionMappings.put(RealtyType.LAND, "26");

        criteriaDefinitionMappings.put(BUILD_TYPE, "tip_objekta_id_l");
        criteriaDefinitionMappings.put(BuildType.OLD_BUILD, "387234");
        criteriaDefinitionMappings.put(BuildType.NEW_BUILD, "387235");
        criteriaDefinitionMappings.put(BuildType.UNDER_CONSTRUCTION, "387296");

        criteriaDefinitionMappings.put(HEATING_TYPE, "grejanje_id_l");
        criteriaDefinitionMappings.put(HeatingType.CENTRAL, "1542");
        criteriaDefinitionMappings.put(HeatingType.OWN_CENTRAL, "1543");
        criteriaDefinitionMultiMappings.put(HeatingType.ELECTRIC, Arrays.asList(TA_PEC, NORVESKI_RADIJATOR, MERMERNI_RADIJATOR));
        criteriaDefinitionMappings.put(HeatingType.GAS, "1545");

        criteriaDefinitionMappings.put(RegistrationType.REGISTERED, "12000004");

        criteriaDefinitionMappings.put(APARTMENT_TYPE, "dodatno_id_ls");
        criteriaDefinitionMappings.put(ApartmentType.WITH_SALON, "12000012");
        criteriaDefinitionMappings.put(ApartmentType.DUPLEX, "12000013");
        criteriaDefinitionMappings.put(ApartmentType.PENTHOUSE, "12000014");
        criteriaDefinitionMappings.put(ApartmentType.LOFT, "12000021");

        criteriaDefinitionMappings.put(FACILITIES, "ostalo_id_ls");
        criteriaDefinitionMappings.put(Facilities.TERRASSE, "12100001");
        criteriaDefinitionMappings.put(Facilities.LOGGIA, "12100019");
        criteriaDefinitionMappings.put(Facilities.BALCONY, "12100018");
        criteriaDefinitionMappings.put(Facilities.FRENCH_BALCONY, "12100018");
        criteriaDefinitionMappings.put(Facilities.GARAGE, "12100016");
        criteriaDefinitionMappings.put(Facilities.PARKING, "12100017");

        criteriaDefinitionMappings.put(ROOM_COUNT, "broj_soba_order_i");

        criteriaDefinitionMappings.put(PRICE, "defaultunit_cena_d");

        criteriaDefinitionMappings.put(SURFACE_AREA, "defaultunit_kvadratura_d");

        criteriaDefinitionMappings.put(FLOOR, "sprat_order_i");

        roomCountMappings = new HashMap<>();
        roomCountMappings.put(RoomCount.RC_0, 1);
        roomCountMappings.put(RoomCount.RC_0_5, 1);
        roomCountMappings.put(RoomCount.RC_1_0, 2);
        roomCountMappings.put(RoomCount.RC_1_5, 3);
        roomCountMappings.put(RoomCount.RC_2_0, 4);
        roomCountMappings.put(RoomCount.RC_2_5, 5);
        roomCountMappings.put(RoomCount.RC_3_0, 7);
        roomCountMappings.put(RoomCount.RC_3_5, 8);
        roomCountMappings.put(RoomCount.RC_4_0, 9);
        roomCountMappings.put(RoomCount.RC_4_5, 10);
        roomCountMappings.put(RoomCount.RC_5_0, 11);
        roomCountMappings.put(RoomCount.RC_5_p, 12);

        unitIdMappings = new HashMap<>();
        unitIdMappings.put(PRICE, 4);
        unitIdMappings.put(SURFACE_AREA, 1);

        floorMappings = new HashMap<>();
        floorMappings.put(SUBTERRAIN, 1);
        floorMappings.put(LOW_GROUND_FLOOR, 2);
        floorMappings.put(GROUND_FLOOR, 3);
        floorMappings.put(HIGH_GROUND_FLOOR, 8);
    }

    public HaloOglasiRequest transform(List<? extends BaseCriteria> criteriaList) {
        PerRequestTransformer transformer = new PerRequestTransformer(criteriaList);
        return transformer.transform();
    }

    private class PerRequestTransformer {
        private AdType adType;
        private RealtyType realtyType;
        private final List<? extends BaseCriteria> criteriaList;

        private PerRequestTransformer(List<? extends BaseCriteria> criteriaList) {
            this.criteriaList = criteriaList;
        }

        private HaloOglasiRequest transform() {
            HaloOglasiRequest request = new HaloOglasiRequest();
            for (BaseCriteria criteria: criteriaList) {
                if (criteria instanceof SingleValueCriteria)
                    transformSingleValueCriteria((SingleValueCriteria) criteria, request);
                else if (criteria instanceof RangeCriteria)
                    transformRangeCriteria((RangeCriteria) criteria, request);
                else if (criteria instanceof MultivalueCriteria)
                    transformMultivalueCriteria((MultivalueCriteria) criteria, request);
            }
            request.setSearchTypeIds(Arrays.asList(2,3));
            request.setSortFields(Arrays.asList(new HaloOglasiRequest.SortField()));
            request.setFieldQueries(Collections.emptyList());
            request.setGeoPolygonQuery(new Object());
            request.setHasValueQueries(Collections.emptyList());
            request.setMultiFieldORQueries(Collections.emptyList());
            if (request.getFieldORQueries() == null)
                request.setFieldORQueries(Collections.emptyList());
            if (request.getRangeQueries() == null)
                request.setRangeQueries(Collections.emptyList());
            return request;
        }
        private void transformSingleValueCriteria(SingleValueCriteria criteria, HaloOglasiRequest request) {
            String criteriaName = criteria.getName();
            if (AD_TYPE.equals(criteriaName)) {
                this.adType = ((SingleValueCriteria<AdType>)criteria).getValue();
                resolveBaseTaxonomy(request);
            } else if (REALTY_TYPE.equals(criteriaName)) {
                this.realtyType = ((SingleValueCriteria<RealtyType>)criteria).getValue();
                request.setCategoryId(criteriaDefinitionMappings.get(criteria.getValue()));
                request.updateFieldOrQueries(HaloOglasiRequest.FieldORQuery.from(criteriaDefinitionMappings.get(REALTY_TYPE), criteriaDefinitionMappings.get(criteria.getValue())));
                resolveBaseTaxonomy(request);
            } else if (REGISTERED.equals(criteriaName)) {
                RegistrationType registration = ((SingleValueCriteria<RegistrationType>)criteria).getValue();
                if (registration == RegistrationType.REGISTERED)
                    request.updateFieldOrQueries(HaloOglasiRequest.FieldORQuery.from(criteriaDefinitionMappings.get(APARTMENT_TYPE), criteriaDefinitionMappings.get(RegistrationType.REGISTERED)));
            } else if (APARTMENT_TYPE.equals(criteriaName)) {
                request.updateFieldOrQueries(HaloOglasiRequest.FieldORQuery.from(criteriaDefinitionMappings.get(APARTMENT_TYPE), criteriaDefinitionMappings.get(criteria.getValue())));
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
            if (ROOM_COUNT.equals(criteriaName))
                request.updateRangeQueries(
                        HaloOglasiRequest.RangeQuery.builder()
                        .fieldName(criteriaDefinitionMappings.get(criteriaName))
                        .from(roomCountMappings.get(criteria.getFrom()))
                        .to(roomCountMappings.get(criteria.getTo()))
                        .build()
                );
            else if (PRICE.equals(criteriaName) || SURFACE_AREA.equals(criteriaName))
                request.updateRangeQueries(
                        HaloOglasiRequest.RangeQuery.builder()
                        .fieldName(criteriaDefinitionMappings.get(criteriaName))
                        .from((Integer)criteria.getFrom())
                        .to((Integer)criteria.getTo())
                        .unitId(unitIdMappings.get(criteriaName))
                        .build()
                );
            else if (FLOOR.equals(criteriaName)) {
                request.updateRangeQueries(
                        HaloOglasiRequest.RangeQuery.builder()
                        .fieldName(criteriaDefinitionMappings.get(criteriaName))
                        .from(getFloorMapping((Integer)criteria.getFrom()))
                        .to(getFloorMapping((Integer)criteria.getTo()))
                        .build()
                );
            }
        }

        private void transformMultivalueCriteria(MultivalueCriteria criteria, HaloOglasiRequest request) {
            if (Arrays.asList(ADVERTISER, BUILD_TYPE, HEATING_TYPE, FACILITIES).contains(criteria.getName()))
                request.updateFieldOrQueries(new HaloOglasiRequest.FieldORQuery(criteriaDefinitionMappings.get(criteria.getName()), getMappings(criteria.getValues())));
        }

        private List<String> getMappings(List<Object> values) {
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
            Integer floorMapping = floorMappings.get(floor);
            if (floorMapping == null) {
                floorMapping = Integer.valueOf(floor) + 10;
            }
            return floorMapping;
        }
    }
}
