package ui.dto.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ui.dto.CriteriaDTOType;

@Getter
@Setter
@AllArgsConstructor
public abstract class CriteriaDTO {
    private String name;
    private CriteriaDTOType criteriaType;
}
