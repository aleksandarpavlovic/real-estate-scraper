package com.paki.scrape.entities;

import com.paki.scrape.topad.TopAdCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SearchProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "searchProfile", cascade = CascadeType.ALL)
    private Search search;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "searchProfile", cascade = CascadeType.ALL)
    private Set<TopAdCondition> topAdConditions;
}
