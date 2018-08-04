package com.paki.scrape;

import com.paki.realties.locations.Location;
import com.paki.scrape.criteria.LocationCriteria;

import java.util.HashSet;
import java.util.Set;

public class LocationService {

    /**
     * replaces group of locations with their parent if all children of the parent are in criteria
     * @param criteria
     */
    public void normalizeLocationCriteria(LocationCriteria criteria) {
        Set<Location> locationsToRemove = new HashSet<>();
        for (Location location: criteria.getLocations()) {
            if (criteria.getLocations().containsAll(location.getSublocations())) {
                locationsToRemove.addAll(location.getSublocations());
            }
        }
        criteria.getLocations().removeAll(locationsToRemove);
    }
}
