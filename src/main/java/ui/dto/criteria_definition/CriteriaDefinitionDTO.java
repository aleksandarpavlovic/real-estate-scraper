package ui.dto.criteria_definition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ui.dto.CriteriaDTOType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriteriaDefinitionDTO {
    private String name;
    private CriteriaDTOType criteriaType;
    private String[] values;
}
