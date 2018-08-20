package com.paki.scrape.top_ad;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class TopAdCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private TopAdDefinition definition;

    public TopAdCondition(TopAdDefinition definition) {
        this.definition = definition;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;
        TopAdCondition other = (TopAdCondition) obj;
        return other.definition == this.definition;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}