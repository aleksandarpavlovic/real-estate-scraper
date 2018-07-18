package scrape.nekretnine_rs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import scrape.criteria.BaseCriteria;
import scrape.criteria.MultivalueCriteria;
import scrape.criteria.RangeCriteria;
import scrape.criteria.SingleValueCriteria;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static scrape.criteria.CriteriaDefinitions.*;

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

        criteriaDefinitionMappings.put(PRODAJA, "prodaja");
        criteriaDefinitionMappings.put(IZDAVANJE, "izdavanje");

        criteriaDefinitionMappings.put(STAN, "stanovi");
        criteriaDefinitionMappings.put(KUCA, "kuce");
        criteriaDefinitionMappings.put(SOBA, "sobe");
        criteriaDefinitionMappings.put(PLAC, "zemljista");

        criteriaDefinitionMappings.put(SALONAC, "Salonac");
        criteriaDefinitionMappings.put(DUPLEX, "dupleks");
        criteriaDefinitionMappings.put(PENTHOUSE, "penthaus");
        criteriaDefinitionMappings.put(POTKROVLJE, "Potkrovlje");

        criteriaDefinitionMappings.put(AGENCIJA, "Zastupnik");
        criteriaDefinitionMappings.put(VLASNIK, "Vlasnik");
        criteriaDefinitionMappings.put(INVESTITOR, "Investitor");

        criteriaDefinitionMultiMappings.put(STAROGRADNJA, Arrays.asList("standardna", "starogradnja"));
        criteriaDefinitionMappings.put(NOVOGRADNJA, "novogradnja");
        criteriaDefinitionMappings.put(IZGRADNJA, "u-izgradnji");

        criteriaDefinitionMappings.put(TERASA, "terasa");
        criteriaDefinitionMappings.put(LODJA, "lodja");
        criteriaDefinitionMappings.put(BALKON, "balkon");
        criteriaDefinitionMappings.put(FRANCUSKI_BALKON, "francuski-balkon");
        criteriaDefinitionMappings.put(GARAZA, "garaza");
        criteriaDefinitionMappings.put(PARKING, "parking");

        criteriaDefinitionMultiMappings.put(CENTRALNO, Arrays.asList("centralno-grejanje", "daljinsko-grejanje"));
        criteriaDefinitionMappings.put(ETAZNO, "etazno-grejanje-grejno-telo");
        criteriaDefinitionMappings.put(STRUJA, "struja");
        criteriaDefinitionMappings.put(GAS, "gas");

        roomCountMappings.put(BROJ_SOBA_0, 0);
        roomCountMappings.put(BROJ_SOBA_0_5, 1);
        roomCountMappings.put(BROJ_SOBA_1_0, 1);
        roomCountMappings.put(BROJ_SOBA_1_5, 2);
        roomCountMappings.put(BROJ_SOBA_2_0, 2);
        roomCountMappings.put(BROJ_SOBA_2_5, 3);
        roomCountMappings.put(BROJ_SOBA_3_0, 3);
        roomCountMappings.put(BROJ_SOBA_3_5, 4);
        roomCountMappings.put(BROJ_SOBA_4_0, 4);
        roomCountMappings.put(BROJ_SOBA_4_5, 5);
        roomCountMappings.put(BROJ_SOBA_5_0, 5);
        roomCountMappings.put(BROJ_SOBA_5_p, 1000);

        pathOrder = Arrays.asList("stambeni-objekti", "zemljista", "izdavanje-prodaja", "tip-stanovi", "prateci-objekti-povrsine", "stanje-objekta", "vrsta-grejanja", "vrsta-goriva", "kvadratura", "cena", "na-spratu", "sobe");

        lowFloorMappings = new HashMap<>();
        lowFloorMappings.put(SUTEREN, "suteren");
        lowFloorMappings.put(NISKO_PRIZEMLJE, "nisko-prizemlje");
        lowFloorMappings.put(PRIZEMLJE, "Prizemlje");
        lowFloorMappings.put(VISOKO_PRIZEMLJE, "visoko-prizemlje");
    }

    public NekretnineRsRequest transform(List<? extends BaseCriteria> criteriaList) {
        NekretnineRsRequest request = new NekretnineRsRequest();
        for (BaseCriteria criteria: criteriaList) {
            if (criteria instanceof SingleValueCriteria)
                transformSingleValueCriteria((SingleValueCriteria) criteria, request);
            else if (criteria instanceof RangeCriteria)
                transformRangeCriteria((RangeCriteria) criteria, request);
            else if (criteria instanceof MultivalueCriteria)
                transformMultivalueCriteria((MultivalueCriteria) criteria, request);
        }
        request.setBaseUrl("https://www.nekretnine.rs");
        request.setPageNumber(1);
        request.setPathOrder(pathOrder);
        return request;
    }

    private void transformSingleValueCriteria(SingleValueCriteria criteria, NekretnineRsRequest request) {
        String criteriaName = criteria.getName();
        if (TIP_OGLASA.equals(criteriaName)) {
            request.updatePath(new NekretnineRsRequest.Path("izdavanje-prodaja", criteriaDefinitionMappings.get(criteria.getValue())));
        } else if (TIP_NEKRETNINE.equals(criteriaName)) {
            if (Arrays.asList(STAN, KUCA, SOBA).contains(criteria.getValue()))
                request.updatePath(new NekretnineRsRequest.Path("stambeni-objekti", criteriaDefinitionMappings.get(criteria.getValue())));
            else
                request.updatePath(new NekretnineRsRequest.Path("zemljista", null));
        } else if (UKNJIZENO.equals(criteriaName)) {
            request.setRegistered(Optional.of(true));
        } else if (TIP_STANA.equals(criteriaName)) {
            request.updatePath(new NekretnineRsRequest.Path("tip-stanovi", criteriaDefinitionMappings.get(criteria.getValue())));
        }
    }

    private void transformRangeCriteria(RangeCriteria criteria, NekretnineRsRequest request) {
        String criteriaName = criteria.getName();
        if (BROJ_SOBA.equals(criteriaName)) {
            request.updatePath(new NekretnineRsRequest.Path("sobe", concatenate(roomCountMappings.get(criteria.getFrom()), roomCountMappings.get(criteria.getTo()))));
        } else if (CENA.equals(criteriaName)) {
            request.updatePath(new NekretnineRsRequest.Path("cena", concatenate(criteria.getFrom(), criteria.getTo())));
        } else if (KVADRATURA.equals(criteriaName)) {
            request.updatePath(new NekretnineRsRequest.Path("kvadratura", concatenate(criteria.getFrom(), criteria.getTo())));
        } else if (SPRATNOST.equals(criteriaName)) {
            updateFloors(criteria, request);
        }
    }

    private void updateFloors(RangeCriteria<Integer> criteria, NekretnineRsRequest request) {
        if (criteria.getFrom() == null && criteria.getTo() == null)
            return;

        int lower = criteria.getFrom() != null ? criteria.getFrom() : SUTEREN;
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

    private void transformMultivalueCriteria(MultivalueCriteria criteria, NekretnineRsRequest request) {
        String criteriaName = criteria.getName();
        if (OGLASIVAC.equals(criteriaName)) {
            List<String> mappings = criteria.getValues().stream().map(criteriaDefinitionMappings::get).collect(Collectors.toList());
            request.setAdvertiser(Optional.of(mappings));
        } else if (GRADNJA.equals(criteriaName)) {
            request.updatePath(new NekretnineRsRequest.Path("stanje-objekta", concatenate(getMappings(criteria.getValues()))));
        } else if (GREJANJE.equals(criteriaName)) {
            NekretnineRsRequest.Path heatingTypePath = new NekretnineRsRequest.Path("vrsta-grejanja", concatenate(getMappings(listIntersection(criteria.getValues(), Arrays.asList(CENTRALNO, ETAZNO)))));
            NekretnineRsRequest.Path fuelTypePath = new NekretnineRsRequest.Path("vrsta-goriva", concatenate(getMappings(listIntersection(criteria.getValues(), Arrays.asList(STRUJA, GAS)))));
            if (heatingTypePath.getValue().isEmpty()) {
                request.updatePath(fuelTypePath);
            } else if (fuelTypePath.getValue().isEmpty()) {
                request.updatePath(heatingTypePath);
            } else {
                request.getDivergentPaths().add(new NekretnineRsRequest.DivergentPaths(Arrays.asList(heatingTypePath, fuelTypePath)));
            }
        } else if (PRATECI_OBJEKTI.equals(criteriaName)) {
            request.updatePath(new NekretnineRsRequest.Path("prateci-objekti-povrsine", concatenate(getMappings(criteria.getValues()))));
        }
    }

    private <T> List<T> listIntersection(List<T> first, List<T> second) {
        List<T> firstCopy = new ArrayList<>(first);
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

    @Getter
    @Setter
    @AllArgsConstructor
    private static class Range<T> {
        private T from;
        private T to;
    }
}
