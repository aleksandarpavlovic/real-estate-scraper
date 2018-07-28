package ui.dto.criteria;

import lombok.Getter;
import lombok.Setter;
import ui.dto.CriteriaDTOType;

@Getter
@Setter
public class SingleValueCriteriaDTO extends CriteriaDTO{
    private String value;

    public SingleValueCriteriaDTO(String name, CriteriaDTOType criteriaType, String value) {
        super(name, criteriaType);
        this.value = value;
    }
}
