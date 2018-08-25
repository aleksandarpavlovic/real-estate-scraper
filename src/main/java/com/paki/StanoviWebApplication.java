package com.paki;

import com.paki.realties.Source;
import com.paki.scrape.scraper.ScraperFactory;
import com.paki.scrape.scraper.halooglasi.HaloOglasiScraper;
import com.paki.scrape.scraper.nekretniners.NekretnineRsScraper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StanoviWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(StanoviWebApplication.class, args).registerShutdownHook();
    }

    @Bean
    public CommandLineRunner commandLineRunner(ScraperFactory scraperFactory) {
        return(args) -> {
            Source haloOglasiSource = new Source("HALO_OGLASI");
            Source nekretnineRsSource = new Source("NEKRETNINE_RS");

            System.out.println("******** Registering scrapers... ********");
            scraperFactory.registerScraper(haloOglasiSource, (source, search) -> new HaloOglasiScraper(source, search));
            scraperFactory.registerScraper(nekretnineRsSource, (source, search) -> new NekretnineRsScraper(source, search));
            System.out.println("******** System up and running... ********");
        };
    }
}
