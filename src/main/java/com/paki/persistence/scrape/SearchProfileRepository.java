package com.paki.persistence.scrape;

import com.paki.scrape.entities.SearchProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchProfileRepository extends JpaRepository<SearchProfile, Long> {

    List<SearchProfile> findByName(String name);

    void deleteById(Long id);

}
