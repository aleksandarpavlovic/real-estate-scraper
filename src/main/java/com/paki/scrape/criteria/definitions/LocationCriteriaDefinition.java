package com.paki.scrape.criteria.definitions;

import com.paki.realties.locations.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LocationCriteriaDefinition extends BaseCriteriaDefinition {
    private List<Location> locations;

    public LocationCriteriaDefinition(String name, List<Location> locations) {
        super(name);
        this.locations = locations;
    }
}
