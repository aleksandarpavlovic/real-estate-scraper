package com.paki.scrape.criteria.definitions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RangeSingleSelectCriteriaDefinition<T> extends BaseCriteriaDefinition{
    T[] values;

    RangeSingleSelectCriteriaDefinition(String name, T[] values) {
        super(name);
        this.values = values;
    }
}
