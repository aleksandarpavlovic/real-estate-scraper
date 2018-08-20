package com.paki.scrape.criteria;

import com.paki.realties.enums.AreaMeasurementUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class RangeWithUnitCriteria extends IntegerRangeCriteria {
    @Enumerated(EnumType.STRING)
    private AreaMeasurementUnit unit;

    public RangeWithUnitCriteria(String name, Integer from, Integer to, AreaMeasurementUnit unit) {
        super(name, from, to);
        this.unit = unit;
    }
}