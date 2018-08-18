package com.paki.scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Objects;

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
        if (this == obj)
            return true;

        if (!super.equals(obj))
            return false;

        if (!(obj instanceof RangeCriteria))
            return false;

        RangeCriteria other = (RangeCriteria) obj;
        if (this.getRangeFrom() == null) {
            if (other.getRangeFrom() != null)
                return false;
        } else if (other.getRangeFrom() == null) {
            return false;
        }

        if (this.getRangeTo() == null) {
            if (other.getRangeTo() != null)
                return false;
        } else if (other.getRangeTo() == null) {
            return false;
        }

        return this.getRangeFrom().equals(other.getRangeFrom()) && this.getRangeTo().equals(other.getRangeTo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rangeFrom, rangeTo);
    }
}
