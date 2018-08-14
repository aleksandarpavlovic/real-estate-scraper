package com.paki.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LocationDTO extends ValueDTO {
    private List<LocationDTO> sublocations;

    public LocationDTO(String name, String display, List<LocationDTO> sublocations) {
        super(name, display);
        this.sublocations = sublocations;
    }
}
