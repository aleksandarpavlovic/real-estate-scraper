package com.paki.persistence.realties;

import com.paki.realties.RealtyPriceChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealtyPriceChangeRepository extends JpaRepository<RealtyPriceChange, Long> {
    @Query("DELETE FROM #{#entityName} WHERE realty.id in :ids")
    void deleteByRealtyIds(@Param("ids") List<Long> ids);
}
