package com.paki.scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class SingleValueCriteria extends BaseCriteria {
    private String value;

    public SingleValueCriteria(String name, String value) {
        super(name);
        this.value = value;
    }
}
