package scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RangeCriteria<T> extends BaseCriteria{
    private T from;
    private T to;

    public RangeCriteria(String name, T from, T to) {
        super(name);
        this.from = from;
        this.to = to;
    }
}
