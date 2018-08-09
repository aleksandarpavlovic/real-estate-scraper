package com.paki.persistence.scrape;

import com.paki.scrape.entities.RealtySearchRelation;
import com.paki.scrape.entities.RealtySearchRelationPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealtySearchRelationRepository extends JpaRepository<RealtySearchRelation, RealtySearchRelationPK> { }
