package com.paki;

import com.paki.persistence.realties.ApartmentRepository;
import com.paki.realties.Apartment;
import com.paki.realties.enums.AdSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class StanoviWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(StanoviWebApplication.class, args).registerShutdownHook();
    }

    @Bean
    public CommandLineRunner demo(ApartmentRepository repository) {
        return(args) -> {
            Apartment stan1 = Apartment.builder()
                    .title("stan1-nrs")
                    .price(BigDecimal.valueOf(69000))
                    .source(AdSource.NEKRETNINE_RS)
                    .externalId("1")
                    .build();
            Apartment stan2 = Apartment.builder()
                    .title("stan2-nrs")
                    .price(BigDecimal.valueOf(29000))
                    .source(AdSource.NEKRETNINE_RS)
                    .externalId("2")
                    .build();
            Apartment stan3 = Apartment.builder()
                    .title("stan3-halo")
                    .price(BigDecimal.valueOf(55000))
                    .source(AdSource.HALO_OGLASI)
                    .externalId("1")
                    .build();
            Apartment stan4 = Apartment.builder()
                    .title("stan4-halo")
                    .price(BigDecimal.valueOf(45000))
                    .source(AdSource.HALO_OGLASI)
                    .externalId("2")
                    .build();
            repository.saveAll(Arrays.asList(stan1, stan2, stan3, stan4));
            System.out.println("Nesto valjda radi");
            repository.findByExternalIdIn(Arrays.asList(AdSource.HALO_OGLASI + "1", AdSource.NEKRETNINE_RS + "1")).forEach(System.out::println);
            repository.findById(2L).ifPresent(System.out::println);
            System.out.println("nesto je uradio");
        };
    }
}
