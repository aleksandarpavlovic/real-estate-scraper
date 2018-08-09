package com.paki.scrape.criteria;

import com.paki.scrape.entities.Search;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "searchId")
    private Search search;

    public BaseCriteria(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof BaseCriteria))
            return false;

        BaseCriteria other = (BaseCriteria) obj;

        return other.getName().equals(this.getName());
    }
}
