package com.paki.scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class RangeCriteria<T> extends BaseCriteria{
    private T rangeFrom;
    private T rangeTo;

    public RangeCriteria(String name, T rangeFrom, T rangeTo) {
        super(name);
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;

        if (!(obj instanceof RangeCriteria))
            return false;

        RangeCriteria other = (RangeCriteria) obj;

        return this.getRangeFrom().equals(other.getRangeFrom()) && this.getRangeTo().equals(other.getRangeTo());
    }
}
