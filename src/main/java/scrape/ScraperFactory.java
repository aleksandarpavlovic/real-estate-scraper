package scrape;

import scrape.halooglasi.HaloOglasiScraper;
import scrape.nekretnine_rs.NekretnineRsScraper;

public class ScraperFactory {
    public Scraper createScraper(ScraperType scraperType, Search search) {
        switch (scraperType) {
            case HALO_OGLASI:
                return new HaloOglasiScraper(search);
            case NEKRETNINE_RS:
                return new NekretnineRsScraper(search);
            default:
                throw new RuntimeException("Unsupported scraper type exception");
        }
    }
}
