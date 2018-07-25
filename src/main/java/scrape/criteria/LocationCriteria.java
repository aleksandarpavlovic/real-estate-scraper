package scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import realties.locations.Location;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LocationCriteria extends MultivalueCriteria<Location>{

    public LocationCriteria(String name, List<Location> locations) {
        super(name, locations);
    }
}
