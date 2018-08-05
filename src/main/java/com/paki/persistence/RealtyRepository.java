package com.paki.persistence;

import com.paki.realties.Realty;
import com.paki.realties.enums.AdSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Repository
public interface RealtyRepository<T extends Realty> extends JpaRepository<Realty, Long> {

    List<T> findBySourceAndExternalId(AdSource sources, String externalId);

    List<T> findByExternalIdIn(Collection<String> externalIds);

    @Query("SELECT r FROM #{#entityName} r, RealtySearchRelation rs, Search s WHERE r.id = rs.realty.id AND s.id = rs.search.id AND scrapeRunNumber = :scraperunnumber AND s.id = :searchid")
    List<T> findByScrapeRunNumberAndSearchId(@Param("scraperunnumber") Long scrapeRunNumber, @Param("searchid") Long searchId);

    @Query("SELECT r FROM #{#entityName} r, RealtySearchRelation rs, Search s, RealtyPriceChange pc WHERE r.id = rs.realty.id AND s.id = rs.search.id AND r.id = pc.realty.id AND scrapeRunNumber = :scraperunnumber AND s.id = :searchid AND pc.delta < 0")
    List<T> findDiscountedByScrapeRunNumberAndSearchId(@Param("scraperunnumber") Long scrapeRunNumber, @Param("searchid") Long searchId);

    @Query("Select r.price FROM #{#entityName} r ORDER BY r.price")
    List<BigDecimal> findAllPrices();

    List<T> findByPriceLessThan(BigDecimal price);
}
