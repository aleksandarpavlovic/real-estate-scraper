package scrape;

import com.google.gson.Gson;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import realties.Realty;
import scrape.criteria.*;
import scrape.halooglasi.HaloOglasiAdParser;
import scrape.halooglasi.HaloOglasiCriteriaTransformer;
import scrape.halooglasi.HaloOglasiRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProbaHaloOglasi {
    HaloOglasiAdParser parser = new HaloOglasiAdParser();

    public void testHaloOglasi(String request) {
        Long startTime = System.currentTimeMillis();
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.halooglasi.com/Quiddita.Widgets.Ad/AdCategoryBasicSearchWidgetAux/GetSidebarData")
                    .method(Connection.Method.POST)
                    .header("Accept", "application/json, text/javascript, */*; q=0.01")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Host", "www.halooglasi.com")
                    .header("Origin", "https://www.halooglasi.com")
                    .header("Referer", "https://www.halooglasi.com")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                    .ignoreContentType(true)
                    .requestBody(request)
                    .header("Content-Type", "application/json")
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("Page loading time with JSoup: {%f} seconds", (float) (System.currentTimeMillis() - startTime) / 1000));

        String[] ads = doc.text().replaceFirst("[{].*?ListHTML\":\"", "").replace("\",\"GridHTML.*?$", "").split("\",\"GridHTML.*?ListHTML\":\"");
        if (ads.length > 0)
            ads[ads.length - 1] = ads[ads.length - 1].replaceFirst("\",\"GridHTML.*$", "");
        Document adsOnly = Jsoup.parse(String.join("", ads));
        List<Realty> adsPOJOs = parser.parseApartments(adsOnly);
        adsPOJOs.forEach(ad -> System.out.println(ad));

        Gson gson = new Gson();
        gson.toJson(ads[0]);
        gson.toJsonTree(ads[0]);
    }

    public static void main(String[] args) {
        ProbaHaloOglasi proba = new ProbaHaloOglasi();
        HaloOglasiCriteriaTransformer transformer = new HaloOglasiCriteriaTransformer();
        HaloOglasiRequest request = transformer.transform(testCriteria());
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);
        proba.testHaloOglasi(jsonRequest);
    }

    public static List<BaseCriteria> testCriteria() {
        List<BaseCriteria> criteriaList = new ArrayList<>();
//        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.TIP_OGLASA, CriteriaDefinitions.PRODAJA));
//        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.TIP_NEKRETNINE, CriteriaDefinitions.STAN));
//        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.UKNJIZENO, true));
//        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.OGLASIVAC, Arrays.asList(CriteriaDefinitions.AGENCIJA, CriteriaDefinitions.VLASNIK, CriteriaDefinitions.INVESTITOR)));
//        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.GREJANJE, Arrays.asList(CriteriaDefinitions.STRUJA)));
//        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.PRATECI_OBJEKTI, Arrays.asList(CriteriaDefinitions.TERASA)));
//        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.GRADNJA, Arrays.asList(CriteriaDefinitions.NOVOGRADNJA, CriteriaDefinitions.IZGRADNJA, CriteriaDefinitions.STAROGRADNJA)));
//        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.CENA, 12, 123123));
//        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.KVADRATURA, 12, 123));
//        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.BROJ_SOBA, CriteriaDefinitions.BROJ_SOBA_1_5, CriteriaDefinitions.BROJ_SOBA_3_5));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.TIP_OGLASA, CriteriaDefinitions.PRODAJA));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.TIP_NEKRETNINE, CriteriaDefinitions.STAN));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.UKNJIZENO, true));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.TIP_STANA, CriteriaDefinitions.DUPLEX));
        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.GREJANJE, Arrays.asList(CriteriaDefinitions.GAS)));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.CENA, 12, 123123));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.KVADRATURA, 12, 123));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.BROJ_SOBA, CriteriaDefinitions.BROJ_SOBA_0_5, CriteriaDefinitions.BROJ_SOBA_5_0));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.SPRATNOST, CriteriaDefinitions.VISOKO_PRIZEMLJE, "1"));
        return criteriaList;
    }
}
