package com.paki.scrape.scraper;

import com.paki.realties.Source;
import com.paki.scrape.entities.Search;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

@Component
public class ScraperFactory {

    private Map<Source, BiFunction<Source, Search, Scraper>> registeredScrapers = new HashMap<>();
    private BiFunction<Source, Search, Scraper> unsupportedScraperCreator = (source, search) -> {throw new RuntimeException("Unsupported scraper exception");};

    public Scraper createScraper(Source source, Search search) {
        return registeredScrapers.getOrDefault(source, unsupportedScraperCreator).apply(source, search);
    }

    public Set<Source> getRegisteredScraperSources() {
        return registeredScrapers.keySet();
    }

    public void registerScraper(Source source, BiFunction<Source, Search, Scraper> scraperCreator) {
        registeredScrapers.put(source, scraperCreator);
    }

    public void unregisterScraper(Source source) {
        registeredScrapers.remove(source);
    }
}
