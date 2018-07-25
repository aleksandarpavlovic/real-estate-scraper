package scrape;

import realties.Realty;
import scheduler.SchedulerService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ScrapeService {
    SchedulerService schedulerService;
    ScraperFactory scraperFactory;

    public ScrapeService(SchedulerService schedulerService, ScraperFactory scraperFactory) {
        this.schedulerService = schedulerService;
        this.scraperFactory = scraperFactory;
    }

    public void scrape(Search search, ScraperType scraperType) {
        Scraper scraper = scraperFactory.createScraper(scraperType, search);
        List<Realty> searchResults = new LinkedList<>();
        while (true) {
            try {
                List<Realty> pageResults = scraper.scrapeNext();
                searchResults.addAll(pageResults);
                if (pageResults.isEmpty())
                    break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Realty realty: searchResults) {
            // fetch by id from database
            Realty dbRealty = realty; // replace realty with dao.fetch(id)
            // if price updated create RealtyPriceChange
            if (dbRealty != null) {
                if (dbRealty.getPrice() != realty.getPrice()) {
                    // new RealtyPriceChange(timestamp, date-time of scrape, realty, delta)
                }
                // dao.update(realty)
            } else {
                // dao.create(realty);
            }
            // dao.createOrUpdate(new RealtySearchRelation(realty, search);
        }
    }

}
