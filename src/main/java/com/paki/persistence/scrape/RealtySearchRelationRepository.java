package com.paki.persistence.scrape;

import com.paki.scrape.entities.RealtySearchRelation;
import com.paki.scrape.entities.RealtySearchRelationPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RealtySearchRelationRepository extends JpaRepository<RealtySearchRelation, RealtySearchRelationPK> {
    @Modifying
    @Query("DELETE FROM RealtySearchRelation r WHERE r.search.id = ?1")
    void deleteBySearchId(Long searchId);
}
