package scrape.criteria.definitions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RangeCriteriaDefinition extends BaseCriteriaDefinition{

    RangeCriteriaDefinition(String name) {
        super(name);
    }
}
