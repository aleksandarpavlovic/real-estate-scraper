package com.paki.persistence.scrape;

import com.paki.scrape.entities.ScrapeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapeInfoRepository extends JpaRepository<ScrapeInfo, Long> {
}
