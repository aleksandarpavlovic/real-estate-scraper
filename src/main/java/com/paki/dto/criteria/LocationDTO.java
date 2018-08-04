package com.paki.dto.criteria;

import com.paki.dto.ValueDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LocationDTO extends CriteriaDTO {
    private List<LocationDTO> sublocations;

    public LocationDTO(ValueDTO name, CriteriaDTOType criteriaType, List<LocationDTO> sublocations) {
        super(name, criteriaType);
        this.sublocations = sublocations;
    }
}
