package com.paki.scrape.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "search_generator")
    @SequenceGenerator(name="search_generator", sequenceName = "search_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    RealtyType realtyType;

    @OneToOne
    @JoinColumn(name = "searchProfileId")
    @JsonBackReference
    SearchProfile searchProfile;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "search")
    Set<BaseCriteria> criteria;

    @Builder
    public Search(RealtyType realtyType, Set<BaseCriteria> criteria) {
        this.realtyType = realtyType;
        this.criteria = criteria;
    }
}
