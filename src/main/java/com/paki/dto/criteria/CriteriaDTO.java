package com.paki.dto.criteria;

import com.paki.dto.ValueDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class CriteriaDTO {
    private ValueDTO name;
    private CriteriaDTOType criteriaType;
}
