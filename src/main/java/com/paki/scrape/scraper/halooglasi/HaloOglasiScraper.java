package com.paki.scrape.scraper.halooglasi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.paki.realties.Realty;
import com.paki.realties.enums.RealtyType;
import com.paki.scrape.entities.Search;
import com.paki.scrape.scraper.Scraper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HaloOglasiScraper extends Scraper {
    private final HaloOglasiCriteriaTransformer criteriaTransformer = new HaloOglasiCriteriaTransformer();
    private final HaloOglasiAdParser parser = new HaloOglasiAdParser();
    HaloOglasiRequest request;

    public HaloOglasiScraper(Search search) {
        super(search);
        request = criteriaTransformer.transform(search.getCriteria());
    }

    @Override
    protected Optional<Realty> doScrapeAdditionalFields(Realty realty) throws IOException {
        return Optional.empty();
    }

    @Override
    protected List<Realty> doScrapeNext() throws IOException {
        Gson gson = new Gson();
        List<Realty> results = executeRequest(gson.toJson(request));
        request = request.nextPageRequest();
        return results;
    }

    private List<Realty> executeRequest(String request) throws IOException {
        Connection.Response response = Jsoup.connect("https://www.halooglasi.com/Quiddita.Widgets.Ad/AdCategoryBasicSearchWidgetAux/GetSidebarData")
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
                .execute();
        Document doc = prepareForParsing(response.body());
        return extractRealties(doc);
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

    private Document prepareForParsing(String response) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(response, JsonElement.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonAds = jsonObject.getAsJsonArray("Ads");
        StringBuilder sb = new StringBuilder();
        for (JsonElement jsonAd: jsonAds) {
            sb.append(jsonAd.getAsJsonObject().get("ListHTML").getAsString());
        }
        return Jsoup.parse(Parser.unescapeEntities(sb.toString(), true));
    }
}
