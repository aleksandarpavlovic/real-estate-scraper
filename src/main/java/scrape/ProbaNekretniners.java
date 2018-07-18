package scrape;

import scrape.criteria.*;
import scrape.nekretnine_rs.NekretnineRsCriteriaTransformer;
import scrape.nekretnine_rs.NekretnineRsRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProbaNekretniners {
    public static void main(String[] args) {
        NekretnineRsCriteriaTransformer transformer = new NekretnineRsCriteriaTransformer();
        NekretnineRsRequest request = transformer.transform(testCriteria());
        List<String> urls = request.getUrls();
        urls.forEach(System.out::println);
    }

    public static List<BaseCriteria> testCriteria() {
        List<BaseCriteria> criteriaList = new ArrayList<>();
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.TIP_OGLASA, CriteriaDefinitions.PRODAJA));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.TIP_NEKRETNINE, CriteriaDefinitions.STAN));
        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.OGLASIVAC, Arrays.asList(CriteriaDefinitions.AGENCIJA, CriteriaDefinitions.VLASNIK, CriteriaDefinitions.INVESTITOR)));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.UKNJIZENO, true));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.TIP_STANA, CriteriaDefinitions.DUPLEX));
        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.GREJANJE, Arrays.asList(CriteriaDefinitions.GAS, CriteriaDefinitions.STRUJA, CriteriaDefinitions.CENTRALNO)));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.CENA, 12, 123123));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.KVADRATURA, 12, 123));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.BROJ_SOBA, CriteriaDefinitions.BROJ_SOBA_0_5, CriteriaDefinitions.BROJ_SOBA_5_0));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.SPRATNOST, CriteriaDefinitions.NISKO_PRIZEMLJE, 5));
        return criteriaList;
    }
}
