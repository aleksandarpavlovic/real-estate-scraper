package com.paki.scrape.entities;

import com.paki.realties.enums.RealtyType;
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

    @Enumerated(EnumType.STRING)
    RealtyType realtyType;

    @OneToOne
    @JoinColumn(name = "searchProfileId")
    SearchProfile searchProfile;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "search")
    Set<BaseCriteria> criteria;

    @Builder
    public Search(RealtyType realtyType, Set<BaseCriteria> criteria) {
        this.realtyType = realtyType;
        this.criteria = criteria;
    }
}
