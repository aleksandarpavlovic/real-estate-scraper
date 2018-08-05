package com.paki.persistence;

import com.paki.scrape.entities.ScrapeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapeInfoRepository extends JpaRepository<ScrapeInfo, Long> {
}
