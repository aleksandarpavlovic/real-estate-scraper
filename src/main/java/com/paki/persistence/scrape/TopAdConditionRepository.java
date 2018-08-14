package com.paki.persistence.scrape;

import com.paki.scrape.topad.TopAdCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TopAdConditionRepository extends JpaRepository<TopAdCondition, Long> {
    @Modifying
    @Query("DELETE FROM TopAdCondition t WHERE t.searchProfile.id = ?1")
    void deleteBySearchProfileId(Long searchProfileId);
}
