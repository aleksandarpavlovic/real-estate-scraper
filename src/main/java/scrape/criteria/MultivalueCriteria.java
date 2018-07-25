package scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MultivalueCriteria<T> extends BaseCriteria {
    private List<T> values;

    public MultivalueCriteria(String name, List<T> values) {
        super(name);
        this.values = values;
    }
}
