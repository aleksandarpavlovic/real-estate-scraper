package com.paki.dto.criteria;

import com.paki.dto.ValueDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RangeCriteriaDTO extends CriteriaDTO{
    private ValueDTO from;
    private ValueDTO to;

    public RangeCriteriaDTO(ValueDTO name, CriteriaDTOType criteriaType, ValueDTO from, ValueDTO to) {
        super(name, criteriaType);
        this.from = from;
        this.to = to;
    }
}
