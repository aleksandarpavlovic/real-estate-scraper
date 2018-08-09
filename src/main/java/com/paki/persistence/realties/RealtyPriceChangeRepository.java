package com.paki.persistence.realties;

import com.paki.realties.RealtyPriceChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealtyPriceChangeRepository extends JpaRepository<RealtyPriceChange, Long> {
}
