package com.paki.scrape.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class ScrapeInfo {
    public static final Long SINGLETON_ID = 1L;

    @Id
    private Long id = SINGLETON_ID;

    @Setter
    private Long lastRunNumber;

    @Setter
    private LocalDateTime lastRunTime;

    @Builder
    public ScrapeInfo(Long lastRunNumber, LocalDateTime lastRunTime) {
        this.lastRunNumber = lastRunNumber;
        this.lastRunTime = lastRunTime;
    }
}
