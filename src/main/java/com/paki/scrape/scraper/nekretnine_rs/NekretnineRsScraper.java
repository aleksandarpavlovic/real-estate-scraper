package com.paki.scrape.scraper.nekretnine_rs;

import com.paki.realties.Realty;
import com.paki.realties.enums.RealtyType;
import com.paki.scrape.entities.Search;
import com.paki.scrape.scraper.Scraper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;

public class NekretnineRsScraper extends Scraper {
    private final NekretnineRsCriteriaTransformer transformer = new NekretnineRsCriteriaTransformer();
    private final NekretnineRsAdParser parser = new NekretnineRsAdParser();
    NekretnineRsRequest request;

    public NekretnineRsScraper(Search search) {
        super(search);
        request = transformer.transform(search.getCriteria());
    }

    @Override
    protected List<Realty> doScrapeNext() throws IOException{
        List<Realty> results = executeRequest(request);
        request = request.nextPageRequest();
        return results;
    }

    private List<Realty> executeRequest(NekretnineRsRequest request) throws IOException {
        List<String> urls = request.getUrls();
        Set<Realty> realties = new HashSet<>();
        for (String url: urls) {
            Connection.Response response = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .header("Host", "www.nekretnine.rs")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                    .execute();

            realties.addAll(extractRealties(response.parse()));
        }
        return new ArrayList<>(realties);
    }

    private List<Realty> extractRealties(Document doc) {
        List<Realty> realties = parse(doc);
        for (Realty realty: realties)
            realty.setAdType(this.adType);
        return realties;
    }

    private List<Realty> parse(Document doc) {
        if (realtyType == RealtyType.APARTMENT)
            return parser.parseApartments(doc);
        if (realtyType == RealtyType.HOUSE)
            return  parser.parseHouses(doc);
        if (realtyType == RealtyType.LAND)
            return parser.parseLand(doc);
        else
            return Collections.emptyList();
    }
}
