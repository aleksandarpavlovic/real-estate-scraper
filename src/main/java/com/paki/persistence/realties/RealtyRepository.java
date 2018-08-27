package com.paki.persistence.realties;

import com.paki.realties.Realty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface RealtyRepository<T extends Realty> extends JpaRepository<Realty, Long> {

    List<T> findByExternalIdIn(Collection<String> externalIds);

    @Query("SELECT r FROM #{#entityName} r, RealtySearchRelation rs, Search s WHERE r.id = rs.realty.id AND s.id = rs.search.id AND r.scrapeRunNumber = :scraperunnumber AND s.id = :searchid")
    List<T> findByScrapeRunNumberAndSearchId(@Param("scraperunnumber") Long scrapeRunNumber, @Param("searchid") Long searchId);

    @Query("SELECT r FROM #{#entityName} r, RealtySearchRelation rs, Search s, RealtyPriceChange pc WHERE r.id = rs.realty.id AND s.id = rs.search.id AND r.id = pc.realty.id AND pc.changeRunNumber = :scraperunnumber AND s.id = :searchid AND pc.delta < 0")
    List<T> findDiscountedByScrapeRunNumberAndSearchId(@Param("scraperunnumber") Long scrapeRunNumber, @Param("searchid") Long searchId);

    @Query("SELECT r.price FROM #{#entityName} r, RealtySearchRelation rs, Search s WHERE r.id = rs.realty.id AND s.id = rs.search.id AND s.id = :searchid ORDER BY r.price")
    List<BigDecimal> findAllPricesBySearchId(@Param("searchid") Long searchId);

    @Query("SELECT r FROM #{#entityName} r, RealtySearchRelation rs, Search s WHERE r.id = rs.realty.id AND s.id = rs.search.id AND s.id = :searchid and r.price <= :price")
    List<T> findBySearchIdAndPriceLessThanEqual(@Param("searchid") Long searchId, @Param("price") BigDecimal price);

    @Query("SELECT r FROM #{#entityName} r, RealtySearchRelation rs WHERE rs.search.id = :searchid AND rs.realty.id = r.id")
    List<T> findBySearchId(@Param("searchid") Long searchId);

    @Query("SELECT r FROM #{#entityName} r, RealtySearchRelation rs WHERE rs.search.id = :searchid AND rs.realty.id = r.id")
    Page<T> findBySearchId(@Param("searchid") Long searchId, Pageable pageRequest);

    @Query("SELECT r.id FROM #{#entityName} r WHERE 0 = (SELECT COUNT(*) FROM RealtySearchRelation rs WHERE rs.realty.id = r.id)")
    List<Long> findIdsOfHangingRealties();

    @Modifying
    void deleteByIdIn(Set<Long> ids);
}
