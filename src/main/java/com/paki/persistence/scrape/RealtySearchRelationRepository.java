package com.paki.persistence.scrape;

import com.paki.scrape.entities.RealtySearchRelation;
import com.paki.scrape.entities.RealtySearchRelationPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RealtySearchRelationRepository extends JpaRepository<RealtySearchRelation, RealtySearchRelationPK> {
    @Query("DELETE FROM #{#entityName} WHERE search.id = :searchid")
    void deleteBySearchId(@Param("searchid")Long searchId);
}
