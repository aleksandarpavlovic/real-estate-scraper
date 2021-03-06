package com.paki.realties.locations;

import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    private String id;
    private String name;
    private Location parent;
    private List<Location> sublocations;

    public void addSublocation(Location location) {
        if (sublocations == null)
            sublocations = new LinkedList<>();
        sublocations.add(location);
        location.setParent(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Location))
            return false;
        return this.getId() == ((Location) obj).getId();
    }
}
