package com.paki.persistence;

import com.paki.realties.Realty;
import com.paki.realties.enums.AdSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RealtyRepository<T extends Realty> extends JpaRepository<Realty, Long> {

    List<T> findBySourceAndExternalId(AdSource sources, String externalId);

    List<T> findByExternalIdIn(Collection<String> externalIds);
}
