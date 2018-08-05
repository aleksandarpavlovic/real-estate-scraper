package com.paki.persistence;

import com.paki.scrape.entities.RealtyPriceChange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealtyPriceChangeRepository extends JpaRepository<RealtyPriceChange, Long> {
}
