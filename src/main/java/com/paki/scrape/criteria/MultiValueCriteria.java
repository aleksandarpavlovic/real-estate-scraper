package com.paki.scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MultiValueCriteria extends BaseCriteria {
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> values;

    public MultiValueCriteria(String name, Set<String> values) {
        super(name);
        this.values = values;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;

        if (!(obj instanceof MultiValueCriteria))
            return false;

        MultiValueCriteria other = (MultiValueCriteria) obj;

        if (this.getValues().size() != other.getValues().size())
            return false;

        Set<String> vals = new HashSet<>(this.getValues());
        vals.removeAll(other.getValues());
        return vals.isEmpty();
    }
}
