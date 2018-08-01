package com.paki.dto.criteria;

import com.paki.dto.CriteriaDTOType;
import com.paki.dto.ValueDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MultiValueCriteriaDTO extends CriteriaDTO{
    private List<ValueDTO> values;

    public MultiValueCriteriaDTO(ValueDTO name, CriteriaDTOType criteriaType, List<ValueDTO> values) {
        super(name, criteriaType);
        this.values = values;
    }
}
