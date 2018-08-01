package com.paki.scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class IntegerRangeCriteria extends RangeCriteria<Integer>{

    public IntegerRangeCriteria(String name, Integer from, Integer to) {
        super(name, from, to);
    }
}
