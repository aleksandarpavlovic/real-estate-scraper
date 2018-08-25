package com.paki.scrape.scraper.nekretniners;

import com.paki.realties.*;
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
    private final NekretnineRsAdParser parser;
    NekretnineRsRequest request;
    Set<Realty> lastRequestRealties = Collections.emptySet();

    public NekretnineRsScraper(Source source, Search search) {
        super(source, search);
        parser = new NekretnineRsAdParser(source);
        request = transformer.transform(search.getCriteria());
    }

    @Override
    protected Optional<Realty> doScrapeAdditionalFields(Realty realty) throws IOException {
        Document adDoc = getAdDocument(realty.getUrl());
        return updateRealty(realty, adDoc);
    }

    @Override
    protected Set<Realty> doScrapeNext() throws IOException{
        Set<Realty> results = executeRequest(request);
        request = request.nextPageRequest();
        return results;
    }

    private Set<Realty> executeRequest(NekretnineRsRequest request) throws IOException {
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

        if (shouldStop(realties))
            return Collections.emptySet();
        lastRequestRealties = realties;

        return realties;
    }

    private boolean shouldStop(Set<Realty> realties) {
        return realties.size() == lastRequestRealties.size()
                && realties.containsAll(lastRequestRealties);
    }

    private Set<Realty> extractRealties(Document doc) {
        Set<Realty> realties = parse(doc);
        for (Realty realty: realties)
            realty.setAdType(this.adType);
        return realties;
    }

    private Set<Realty> parse(Document doc) {
        if (realtyType == RealtyType.APARTMENT)
            return parser.parseApartments(doc);
        if (realtyType == RealtyType.HOUSE)
            return  parser.parseHouses(doc);
        if (realtyType == RealtyType.LAND)
            return parser.parseLand(doc);
        else
            return Collections.emptySet();
    }

    private Optional<Realty> updateRealty(Realty realty, Document doc) {
        if (realtyType == RealtyType.APARTMENT)
            return Optional.ofNullable(parser.updateApartment((Apartment) realty, doc));
        if (realtyType == RealtyType.HOUSE)
            return  Optional.ofNullable(parser.updateHouse((House) realty, doc));
        if (realtyType == RealtyType.LAND)
            return Optional.ofNullable(parser.updateLand((Land) realty, doc));
        else
            return Optional.empty();
    }

    private Document getAdDocument(String url) {
        try {
            return Jsoup.connect(url)
                    .header("Host", "www.nekretnine.rs")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Nekretnine.rs Scraper";
    }
}
