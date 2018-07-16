package scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SingleValueCriteria<T> extends BaseCriteria {
    private T value;

    public SingleValueCriteria(String name, T value) {
        super(name);
        this.value = value;
    }
}
