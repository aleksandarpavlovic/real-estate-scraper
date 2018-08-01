package com.paki.realties.locations;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Location {
    @Id
    String id;
    String name;
    @ManyToOne
    Location parent;
    @OneToMany(mappedBy = "parent")
    List<Location> sublocations;

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
