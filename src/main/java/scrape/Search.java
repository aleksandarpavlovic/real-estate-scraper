package scrape;

import lombok.*;
import scrape.criteria.BaseCriteria;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Search {
    String name;
    List<BaseCriteria> criteria;
}
