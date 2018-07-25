package realties.locations;

import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    String id;
    String name;
    List<Location> sublocations;

    public void addSublocation(Location location) {
        if (sublocations == null)
            sublocations = new LinkedList<>();
        sublocations.add(location);
    }
}
