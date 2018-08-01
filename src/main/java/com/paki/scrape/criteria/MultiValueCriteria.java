package com.paki.scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MultiValueCriteria extends BaseCriteria {
    @ElementCollection
    private List<String> values;

    public MultiValueCriteria(String name, List<String> values) {
        super(name);
        this.values = values;
    }
}
