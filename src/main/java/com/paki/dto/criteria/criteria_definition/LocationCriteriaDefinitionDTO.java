package com.paki.dto.criteria.criteria_definition;

import com.paki.dto.ValueDTO;
import com.paki.dto.criteria.CriteriaDTOType;
import com.paki.dto.criteria.LocationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LocationCriteriaDefinitionDTO {
    private ValueDTO name;
    private CriteriaDTOType criteriaDTOType;
    private List<LocationDTO> locations;

    public LocationCriteriaDefinitionDTO(ValueDTO name, CriteriaDTOType criteriaDTOType, List<LocationDTO> locations) {
        this.name = name;
        this.criteriaDTOType = criteriaDTOType;
        this.locations = locations;
    }
}
