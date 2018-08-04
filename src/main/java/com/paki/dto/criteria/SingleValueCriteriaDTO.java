package com.paki.dto.criteria;

import com.paki.dto.ValueDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SingleValueCriteriaDTO extends CriteriaDTO{
    private ValueDTO value;

    public SingleValueCriteriaDTO(ValueDTO name, CriteriaDTOType criteriaType, ValueDTO value) {
        super(name, criteriaType);
        this.value = value;
    }
}
