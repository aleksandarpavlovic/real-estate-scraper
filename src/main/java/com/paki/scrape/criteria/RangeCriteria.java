package com.paki.scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class RangeCriteria<T> extends BaseCriteria{
    private T from;
    private T to;

    public RangeCriteria(String name, T from, T to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;

        if (!(obj instanceof RangeCriteria))
            return false;

        RangeCriteria other = (RangeCriteria) obj;

        return this.getFrom().equals(other.getFrom()) && this.getTo().equals(other.getTo());
    }
}
