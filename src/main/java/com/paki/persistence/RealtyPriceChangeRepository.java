package com.paki.persistence;

import com.paki.scrape.entities.RealtyPriceChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealtyPriceChangeRepository extends JpaRepository<RealtyPriceChange, Long> {
}
