package ui.dto.criteria;

import lombok.Getter;
import lombok.Setter;
import ui.dto.CriteriaDTOType;

@Getter
@Setter
public class RangeCriteriaDTO extends CriteriaDTO{
    private String from;
    private String to;

    public RangeCriteriaDTO(String name, CriteriaDTOType criteriaType, String from, String to) {
        super(name, criteriaType);
        this.from = from;
        this.to = to;
    }
}
