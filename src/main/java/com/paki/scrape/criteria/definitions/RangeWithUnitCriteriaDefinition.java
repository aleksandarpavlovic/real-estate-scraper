package com.paki.scrape.criteria.definitions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RangeWithUnitCriteriaDefinition<T> extends RangeCriteriaDefinition {
    T unit;

    RangeWithUnitCriteriaDefinition(String name, T unit) {
        super(name);
        this.unit = unit;
    }
}
