package com.paki.scrape.services;

import com.paki.realties.enums.RealtyType;
import com.paki.realties.locations.Location;
import com.paki.realties.locations.LocationsGenerator;
import com.paki.scrape.criteria.BaseCriteria;
import com.paki.scrape.criteria.MultiValueCriteria;
import com.paki.scrape.criteria.SingleValueCriteria;
import com.paki.scrape.criteria.definitions.CriteriaDefinitions;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CriteriaService {

    public void normalizeLocationCriteria(Collection<BaseCriteria> criteria) {
        criteria.stream()
                .filter(c -> c instanceof MultiValueCriteria)
                .filter(c -> CriteriaDefinitions.LOCATION.equals(c.getName()))
                .map(c -> (MultiValueCriteria) c)
                .forEach(this::normalizeLocationCriteria);
    }
    /**
     * replaces group of locations with their parent if all children of the parent are in criteria
     * @param criteria
     */
    public void normalizeLocationCriteria(MultiValueCriteria criteria) {
        Set<Location> locationsToRemove = new HashSet<>();
        for (String locationId: criteria.getValues()) {
            Location location = LocationsGenerator.getLocation(locationId);
            if (criteria.getValues().containsAll(location.getSublocations())) {
                locationsToRemove.addAll(location.getSublocations());
            }
        }
        criteria.getValues().removeAll(locationsToRemove);
    }

    public RealtyType inferRealtyType(Collection<BaseCriteria> criteria) {
        Optional<BaseCriteria> optional = criteria.stream()
                .filter(c -> CriteriaDefinitions.REALTY_TYPE.equals(c.getName()))
                .findFirst();
        if (optional.isPresent() && optional.get() instanceof SingleValueCriteria) {
            return RealtyType.get(((SingleValueCriteria) optional.get()).getValue());
        } else {
            return null;
        }
    }
}
