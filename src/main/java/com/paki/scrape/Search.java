package com.paki.scrape;

import com.paki.scrape.criteria.BaseCriteria;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "searchId")
    Set<BaseCriteria> criteria;

    @Builder
    public Search(Set<BaseCriteria> criteria) {
        this.criteria = criteria;
    }
}
