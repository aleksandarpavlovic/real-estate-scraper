package com.paki.scrape.criteria.definitions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SingleSelectCriteriaDefinition<T> extends BaseCriteriaDefinition{
    T[] values;

    SingleSelectCriteriaDefinition(String name, T[] values) {
        super(name);
        this.values = values;
    }
}
