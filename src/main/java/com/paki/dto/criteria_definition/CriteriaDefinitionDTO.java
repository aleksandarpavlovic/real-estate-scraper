package com.paki.dto.criteria_definition;

import com.paki.dto.CriteriaDTOType;
import com.paki.dto.ValueDTO;
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
