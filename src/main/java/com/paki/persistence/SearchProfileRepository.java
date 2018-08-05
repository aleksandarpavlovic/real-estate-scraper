package com.paki.persistence;

import com.paki.scrape.entities.SearchProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchProfileRepository extends JpaRepository<SearchProfile, Long> {
}
