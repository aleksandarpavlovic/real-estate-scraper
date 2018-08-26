package com.paki.scrape.scraper;

import com.paki.realties.*;
import lombok.Getter;
import org.jsoup.nodes.Document;

import java.util.Set;

public abstract class AdParser {

    @Getter
    private final Source source;

    public AdParser(Source source) {
        this.source = source;
    }

    public abstract Set<Realty> parseApartments(Document doc);

    public abstract Set<Realty> parseHouses(Document doc);

    public abstract Set<Realty> parseLand(Document doc);

    protected Apartment updateApartment(Apartment apartment, Document adDoc) {
        return null;
    }

    protected House updateHouse(House house, Document adDoc) {
        return null;
    }

    protected Land updateLand(Land land, Document adDoc) {
        return null;
    }
}
