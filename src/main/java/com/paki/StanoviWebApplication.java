package com.paki;

import com.paki.persistence.realties.SourceRepository;
import com.paki.persistence.scrape.ScrapeSettingsRepository;
import com.paki.realties.Source;
import com.paki.scheduler.SchedulerService;
import com.paki.scrape.entities.ScrapeSettings;
import com.paki.scrape.scraper.ScraperFactory;
import com.paki.scrape.scraper.halooglasi.HaloOglasiScraper;
import com.paki.scrape.scraper.nekretniners.NekretnineRsScraper;
import com.paki.scrape.services.ScrapeService;
import com.paki.scrape.synchronization.GlobalLock;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class StanoviWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(StanoviWebApplication.class, args).registerShutdownHook();
    }

    @Bean
    public CommandLineRunner commandLineRunner(GlobalLock globalLock, SourceRepository sourceRepository, ScraperFactory scraperFactory, SchedulerService schedulerService, ScrapeService scrapeService, ScrapeSettingsRepository scrapeSettingsRepository) {
        return(args) -> {
            try {
                globalLock.lock();

                Source haloOglasiSource = new Source("HALO_OGLASI");
                Source nekretnineRsSource = new Source("NEKRETNINE_RS");

                haloOglasiSource = createSource(haloOglasiSource, sourceRepository);
                nekretnineRsSource = createSource(nekretnineRsSource, sourceRepository);

                System.out.println("******** Registering scrapers... ********");
                scraperFactory.registerScraper(haloOglasiSource, (source, search) -> new HaloOglasiScraper(source, search));
                scraperFactory.registerScraper(nekretnineRsSource, (source, search) -> new NekretnineRsScraper(source, search));

                Optional<ScrapeSettings> settingsOptional = scrapeSettingsRepository.findById(ScrapeSettings.SINGLETON_ID);
                int period = 0;
                if (settingsOptional.isPresent())
                    period = settingsOptional.get().getScheduledPeriod();
                schedulerService.schedule(scrapeService::scrape, period);

                System.out.println("******** System up and running... ********");
            } finally {
                globalLock.unlock();
            }
        };
    }

    public Source createSource(Source source, SourceRepository sourceRepository) {
        Optional<Source> dbSource = sourceRepository.findByName(source.getName());
        if (dbSource.isPresent())
            return dbSource.get();
        else
            return sourceRepository.save(source);
    }
}
