package ui.dto.criteria;

import lombok.Getter;
import lombok.Setter;
import ui.dto.CriteriaDTOType;

import java.util.List;

@Getter
@Setter
public class MultiValueCriteriaDTO extends CriteriaDTO{
    private List<String> values;

    public MultiValueCriteriaDTO(String name, CriteriaDTOType criteriaType, List<String> values) {
        super(name, criteriaType);
        this.values = values;
    }
}
