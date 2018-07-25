package scrape.criteria.definitions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SingleOrNoSelectCriteriaDefinition<T> extends BaseCriteriaDefinition{
    T[] values;

    SingleOrNoSelectCriteriaDefinition(String name, T[] values) {
        super(name);
        this.values = values;
    }
}
