package com.paki.scrape.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class ScrapeSettings {
    public static final Long SINGLETON_ID = 1L;

    @Id
    @JsonIgnore
    private Long id = SINGLETON_ID;

    // period in minutes between consecutive scrapings
    @Setter
    private int scheduledPeriod;

    // email recipients to be notified on top-ad occurrence
    @Setter
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> emailList;

    @Builder
    public ScrapeSettings(int scheduledPeriod, List<String> emailList) {
        this.id = SINGLETON_ID;
        this.scheduledPeriod = scheduledPeriod;
        this.emailList = emailList;
    }
}
