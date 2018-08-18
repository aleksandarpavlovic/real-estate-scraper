package com.paki.scrape.criteria;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.paki.scrape.entities.Search;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "criteria_generator")
    @SequenceGenerator(name="criteria_generator", sequenceName = "criteria_seq")
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "searchId")
    @JsonBackReference
    private Search search;

    public BaseCriteria(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof BaseCriteria))
            return false;

        BaseCriteria other = (BaseCriteria) obj;
        if (this.getName() == null) {
            if (other.getName() != null)
                return false;
        } else if (other.getName() == null) {
            return false;
        }

        return other.getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }
}
