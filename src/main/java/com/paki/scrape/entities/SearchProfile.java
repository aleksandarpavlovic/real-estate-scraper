package com.paki.scrape.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "search_profile_generator")
    @SequenceGenerator(name="search_profile_generator", sequenceName = "search_profile_seq")
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "searchProfile", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Search search;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "searchProfile", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<TopAdCondition> topAdConditions;
}
