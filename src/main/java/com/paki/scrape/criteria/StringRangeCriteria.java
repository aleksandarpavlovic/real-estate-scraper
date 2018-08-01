package com.paki.scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class StringRangeCriteria extends RangeCriteria<String>{

    public StringRangeCriteria(String name, String from, String to) {
        super(name, from, to);
    }
}
