package scrape;

import realties.Realty;
import scrape.criteria.*;
import scrape.halooglasi.HaloOglasiScraper;
import scrape.nekretnine_rs.NekretnineRsScraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProbaScraper {

    public static void main(String[] args) {
        ProbaScraper proba = new ProbaScraper();
        proba.scrape(testCriteria());
    }

    private void scrape(List<BaseCriteria> criteria) {
        Scraper hoScraper = new HaloOglasiScraper(criteria);
        Scraper nrsScraper = new NekretnineRsScraper(criteria);
        try {
            List<Realty> hoResults = hoScraper.scrape();
            List<Realty> nrsResults = nrsScraper.scrape();
            List<Realty> nrsResults2 = nrsScraper.scrape();
            hoResults.forEach(System.out::println);
            nrsResults.forEach(System.out::println);
            nrsResults2.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<BaseCriteria> testCriteria() {
        List<BaseCriteria> criteriaList = new ArrayList<>();
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.TIP_OGLASA, CriteriaDefinitions.PRODAJA));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.TIP_NEKRETNINE, CriteriaDefinitions.STAN));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.UKNJIZENO, true));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.TIP_STANA, CriteriaDefinitions.DUPLEX));
        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.GREJANJE, Arrays.asList(CriteriaDefinitions.GAS)));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.CENA, 12, 123123));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.KVADRATURA, 12, 123));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.BROJ_SOBA, CriteriaDefinitions.BROJ_SOBA_0_5, CriteriaDefinitions.BROJ_SOBA_5_0));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.SPRATNOST, CriteriaDefinitions.VISOKO_PRIZEMLJE, 1));
        return criteriaList;
    }
}
