package com.paki.persistence.scrape;

import com.paki.scrape.entities.ScrapeSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapeSettingsRepository extends JpaRepository<ScrapeSettings, Long> {
}
