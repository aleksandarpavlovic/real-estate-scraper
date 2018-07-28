package ui.dto.criteria_definition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriteriaDefinitionsDTO {
    private List<CriteriaDefinitionDTO> criteriaDefinitions;
    private LocationCriteriaDefinitionDTO locationDefinition;
}
