package com.paki.scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class LocationCriteria extends BaseCriteria {
    @ElementCollection
    private Set<String> locations;

    public LocationCriteria(String name, Set<String> locations) {
        super(name);
        this.locations = locations;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;

        if (!(obj instanceof LocationCriteria))
            return false;

        LocationCriteria other = (LocationCriteria) obj;

        if (this.getLocations().size() != other.getLocations().size())
            return false;

        Set<String> vals = new HashSet<>(this.getLocations());
        vals.removeAll(other.getLocations());
        return vals.isEmpty();
    }
}
