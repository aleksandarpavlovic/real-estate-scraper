package com.paki.persistence.realties;

import com.paki.realties.RealtyPriceChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RealtyPriceChangeRepository extends JpaRepository<RealtyPriceChange, Long> {
    @Modifying
    @Query("DELETE FROM RealtyPriceChange WHERE realty.id in :ids")
    int deleteByRealtyIds(@Param("ids") Set<Long> ids);
}
