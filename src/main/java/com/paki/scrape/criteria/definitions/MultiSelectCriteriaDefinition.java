package com.paki.scrape.criteria.definitions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MultiSelectCriteriaDefinition<T> extends BaseCriteriaDefinition{
    T[] values;

    MultiSelectCriteriaDefinition(String name, T[] values) {
        super(name);
        this.values = values;
    }
}
