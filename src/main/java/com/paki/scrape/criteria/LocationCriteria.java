package com.paki.scrape.criteria;

import com.paki.realties.locations.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class LocationCriteria extends BaseCriteria {
    @ManyToMany
    private Set<Location> locations;

    public LocationCriteria(String name, Set<Location> locations) {
        super(name);
        this.locations = locations;
    }
}
