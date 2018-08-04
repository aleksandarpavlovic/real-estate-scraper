package com.paki.dto.criteria.criteria_definition;

import com.paki.dto.ValueDTO;
import com.paki.dto.criteria.CriteriaDTOType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriteriaDefinitionDTO {
    private ValueDTO name;
    private CriteriaDTOType criteriaType;
    private ValueDTO[] values;
}
