package com.paki.dto.criteria;

import com.paki.dto.CriteriaDTOType;
import com.paki.dto.ValueDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class CriteriaDTO {
    private ValueDTO name;
    private CriteriaDTOType criteriaType;
}
