package scrape;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import realties.Realty;
import realties.enums.*;
import scrape.criteria.BaseCriteria;
import scrape.criteria.MultivalueCriteria;
import scrape.criteria.RangeCriteria;
import scrape.criteria.SingleValueCriteria;
import scrape.criteria.definitions.CriteriaDefinitions;
import scrape.nekretnine_rs.NekretnineRsAdParser;
import scrape.nekretnine_rs.NekretnineRsCriteriaTransformer;
import scrape.nekretnine_rs.NekretnineRsRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProbaNekretniners {
    public static void main(String[] args) {
        NekretnineRsCriteriaTransformer transformer = new NekretnineRsCriteriaTransformer();
        NekretnineRsRequest request = transformer.transform(testCriteria());
        List<String> urls = request.getUrls();
        urls.forEach(System.out::println);

        Long startTime = System.currentTimeMillis();
        try {
            Connection.Response response = Jsoup.connect(urls.get(0))
                    .method(Connection.Method.GET)
                    .header("Host", "www.nekretnine.rs")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                    .execute();
            System.out.println(String.format("Page loading time with JSoup: {%f} seconds", (float) (System.currentTimeMillis() - startTime) / 1000));
            Document doc = response.parse();
            NekretnineRsAdParser parser = new NekretnineRsAdParser();
            List<Realty> ads = parser.parseApartments(doc);
            ads.forEach(System.out::println);
            System.out.println("kraj");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<BaseCriteria> testCriteria() {
        List<BaseCriteria> criteriaList = new ArrayList<>();
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.AD_TYPE, AdType.SELL));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.REALTY_TYPE, RealtyType.APARTMENT));
        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.ADVERTISER, Arrays.asList(AdvertiserType.values())));
        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.REGISTERED, true));
//        criteriaList.add(new SingleValueCriteria<>(CriteriaDefinitions.APARTMENT_TYPE, CriteriaDefinitions.DUPLEX));
        criteriaList.add(new MultivalueCriteria(CriteriaDefinitions.HEATING_TYPE, Arrays.asList(HeatingType.GAS, HeatingType.ELECTRIC, HeatingType.CENTRAL)));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.PRICE, 12, 123123));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.SURFACE_AREA, 12, 123));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.ROOM_COUNT, RoomCount.RC_0_5, RoomCount.RC_5_0));
        criteriaList.add(new RangeCriteria<>(CriteriaDefinitions.FLOOR, CriteriaDefinitions.LOW_GROUND_FLOOR, 5));
        return criteriaList;
    }
}
