package scrape.halooglasi;

import scrape.criteria.BaseCriteria;
import scrape.criteria.MultivalueCriteria;
import scrape.criteria.RangeCriteria;
import scrape.criteria.SingleValueCriteria;

import java.util.*;

import static scrape.criteria.CriteriaDefinitions.*;

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
    private static final String URL_IZDAVANJE_SOBA = "/nekretnine/izdavanje-soba";
    private static final String URL_PRODAJA_PLACEVA = "/nekretnine/prodaja-zemljista";
    private static final String URL_IZDAVANJE_PLACEVA = "/nekretnine/izdavanje-zemljista";

    private static final String TA_PEC = "1544";
    private static final String NORVESKI_RADIJATOR = "1548";
    private static final String MERMERNI_RADIJATOR = "1549";

    static {
        urlMappings = new HashMap<>();

        urlMappings.put(PRODAJA + STRING_CONCATENATOR + STAN, URL_PRODAJA_STANOVA);
        urlMappings.put(IZDAVANJE + STRING_CONCATENATOR + STAN, URL_IZDAVANJE_STANOVA);
        urlMappings.put(PRODAJA + STRING_CONCATENATOR + KUCA, URL_PRODAJA_KUCA);
        urlMappings.put(IZDAVANJE + STRING_CONCATENATOR + KUCA, URL_IZDAVANJE_KUCA);
        urlMappings.put(IZDAVANJE + STRING_CONCATENATOR + SOBA, URL_IZDAVANJE_SOBA);
        urlMappings.put(PRODAJA + STRING_CONCATENATOR + PLAC, URL_PRODAJA_PLACEVA);
        urlMappings.put(IZDAVANJE + STRING_CONCATENATOR + PLAC, URL_IZDAVANJE_PLACEVA);



        criteriaDefinitionMappings = new HashMap<>();
        criteriaDefinitionMultiMappings = new HashMap<>();

        criteriaDefinitionMappings.put(OGLASIVAC, "oglasivac_nekretnine_id_l");
        criteriaDefinitionMappings.put(AGENCIJA, "387238");
        criteriaDefinitionMappings.put(VLASNIK, "387237");
        criteriaDefinitionMappings.put(INVESTITOR, "387300");

        criteriaDefinitionMappings.put(TIP_NEKRETNINE, "CategoryIds");
        criteriaDefinitionMappings.put(STAN, "12");
        criteriaDefinitionMappings.put(KUCA, "24");
        criteriaDefinitionMappings.put(SOBA, "62");
        criteriaDefinitionMappings.put(PLAC, "26");

        criteriaDefinitionMappings.put(GRADNJA, "tip_objekta_id_l");
        criteriaDefinitionMappings.put(STAROGRADNJA, "387234");
        criteriaDefinitionMappings.put(NOVOGRADNJA, "387235");
        criteriaDefinitionMappings.put(IZGRADNJA, "387296");

        criteriaDefinitionMappings.put(GREJANJE, "grejanje_id_l");
        criteriaDefinitionMappings.put(CENTRALNO, "1542");
        criteriaDefinitionMappings.put(ETAZNO, "1543");
        criteriaDefinitionMultiMappings.put(STRUJA, Arrays.asList(TA_PEC, NORVESKI_RADIJATOR, MERMERNI_RADIJATOR));
        criteriaDefinitionMappings.put(GAS, "1545");

        criteriaDefinitionMappings.put(UKNJIZENO, "12000004");

        criteriaDefinitionMappings.put(TIP_STANA, "dodatno_id_ls");
        criteriaDefinitionMappings.put(SALONAC, "12000012");
        criteriaDefinitionMappings.put(DUPLEX, "12000013");
        criteriaDefinitionMappings.put(PENTHOUSE, "12000014");
        criteriaDefinitionMappings.put(POTKROVLJE, "12000021");

        criteriaDefinitionMappings.put(PRATECI_OBJEKTI, "ostalo_id_ls");
        criteriaDefinitionMappings.put(TERASA, "12100001");
        criteriaDefinitionMappings.put(LODJA, "12100019");
        criteriaDefinitionMappings.put(BALKON, "12100018");
        criteriaDefinitionMappings.put(FRANCUSKI_BALKON, "12100018");
        criteriaDefinitionMappings.put(GARAZA, "12100016");
        criteriaDefinitionMappings.put(PARKING, "12100017");

        criteriaDefinitionMappings.put(BROJ_SOBA, "broj_soba_order_i");

        criteriaDefinitionMappings.put(CENA, "defaultunit_cena_d");

        criteriaDefinitionMappings.put(KVADRATURA, "defaultunit_kvadratura_d");

        criteriaDefinitionMappings.put(SPRATNOST, "sprat_order_i");

        roomCountMappings = new HashMap<>();
        roomCountMappings.put(BROJ_SOBA_0, 1);
        roomCountMappings.put(BROJ_SOBA_0_5, 1);
        roomCountMappings.put(BROJ_SOBA_1_0, 2);
        roomCountMappings.put(BROJ_SOBA_1_5, 3);
        roomCountMappings.put(BROJ_SOBA_2_0, 4);
        roomCountMappings.put(BROJ_SOBA_2_5, 5);
        roomCountMappings.put(BROJ_SOBA_3_0, 7);
        roomCountMappings.put(BROJ_SOBA_3_5, 8);
        roomCountMappings.put(BROJ_SOBA_4_0, 9);
        roomCountMappings.put(BROJ_SOBA_4_5, 10);
        roomCountMappings.put(BROJ_SOBA_5_0, 11);
        roomCountMappings.put(BROJ_SOBA_5_p, 12);

        unitIdMappings = new HashMap<>();
        unitIdMappings.put(CENA, 4);
        unitIdMappings.put(KVADRATURA, 1);

        floorMappings = new HashMap<>();
        floorMappings.put(SUTEREN, 1);
        floorMappings.put(NISKO_PRIZEMLJE, 2);
        floorMappings.put(PRIZEMLJE, 3);
        floorMappings.put(VISOKO_PRIZEMLJE, 8);
    }

    public HaloOglasiRequest transform(List<? extends BaseCriteria> criteriaList) {
        PerRequestTransformer transformer = new PerRequestTransformer(criteriaList);
        return transformer.transform();
    }

    private class PerRequestTransformer {
        private String adType;
        private String realtyType;
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
            if (TIP_OGLASA.equals(criteriaName)) {
                this.adType = (String) criteria.getValue();
                resolveBaseTaxonomy(request);
            } else if (TIP_NEKRETNINE.equals(criteriaName)) {
                this.realtyType = (String) criteria.getValue();
                request.setCategoryId(criteriaDefinitionMappings.get(criteria.getValue()));
                request.updateFieldOrQueries(HaloOglasiRequest.FieldORQuery.from(criteriaDefinitionMappings.get(TIP_NEKRETNINE), criteriaDefinitionMappings.get(criteria.getValue())));
                resolveBaseTaxonomy(request);
            } else if (UKNJIZENO.equals(criteriaName)) {
                Boolean criteriaValue = (Boolean) criteria.getValue();
                if (criteriaValue == Boolean.TRUE)
                    request.updateFieldOrQueries(HaloOglasiRequest.FieldORQuery.from(criteriaDefinitionMappings.get(TIP_STANA), criteriaDefinitionMappings.get(UKNJIZENO)));
            } else if (TIP_STANA.equals(criteriaName)) {
                request.updateFieldOrQueries(HaloOglasiRequest.FieldORQuery.from(criteriaDefinitionMappings.get(TIP_STANA), criteriaDefinitionMappings.get(criteria.getValue())));
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
            if (BROJ_SOBA.equals(criteriaName))
                request.updateRangeQueries(
                        HaloOglasiRequest.RangeQuery.builder()
                        .fieldName(criteriaDefinitionMappings.get(criteriaName))
                        .from(roomCountMappings.get(criteria.getFrom()))
                        .to(roomCountMappings.get(criteria.getTo()))
                        .build()
                );
            else if (CENA.equals(criteriaName) || KVADRATURA.equals(criteriaName))
                request.updateRangeQueries(
                        HaloOglasiRequest.RangeQuery.builder()
                        .fieldName(criteriaDefinitionMappings.get(criteriaName))
                        .from((Integer)criteria.getFrom())
                        .to((Integer)criteria.getTo())
                        .unitId(unitIdMappings.get(criteriaName))
                        .build()
                );
            else if (SPRATNOST.equals(criteriaName)) {
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
            if (Arrays.asList(OGLASIVAC, GRADNJA, GREJANJE, PRATECI_OBJEKTI).contains(criteria.getName()))
                request.updateFieldOrQueries(new HaloOglasiRequest.FieldORQuery(criteriaDefinitionMappings.get(criteria.getName()), getMappings(criteria.getValues())));
        }

        private List<String> getMappings(List<String> values) {
            List<String> mappings = new ArrayList<>();
            for (String value: values) {
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
