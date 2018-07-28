package ui.dto.criteria_definition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import realties.locations.Location;
import ui.dto.CriteriaDTOType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LocationCriteriaDefinitionDTO {
    private String name;
    private CriteriaDTOType criteriaDTOType;
    private List<Location> locations;

    public LocationCriteriaDefinitionDTO(String name, CriteriaDTOType criteriaDTOType, List<Location> locations) {
        this.name = name;
        this.criteriaDTOType = criteriaDTOType;
        this.locations = locations;
    }
}
