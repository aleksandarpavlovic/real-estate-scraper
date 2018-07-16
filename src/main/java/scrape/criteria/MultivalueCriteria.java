package scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MultivalueCriteria extends BaseCriteria {
    private List<String> values;

    public MultivalueCriteria(String name, List<String> values) {
        super(name);
        this.values = values;
    }
}
