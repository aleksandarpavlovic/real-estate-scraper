package scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RangeWithUnitCriteria<T, U> extends RangeCriteria<T> {
    private U unit;

    public RangeWithUnitCriteria(String name, T from, T to, U unit) {
        super(name, from, to);
        this.unit = unit;
    }
}
