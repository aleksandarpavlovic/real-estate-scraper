package com.paki.dto.criteria;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.paki.dto.ValueDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LocationDTO.class, name = "Location"),
        @JsonSubTypes.Type(value = SingleValueCriteriaDTO.class, name = "Single Value"),
        @JsonSubTypes.Type(value = MultiValueCriteriaDTO.class, name = "Multi Value"),
        @JsonSubTypes.Type(value = RangeCriteriaDTO.class, name = "Range")
})
public abstract class CriteriaDTO {
    private ValueDTO name;
    private CriteriaDTOType criteriaType;
}
