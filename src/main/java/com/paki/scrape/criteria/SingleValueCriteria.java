package com.paki.scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof SingleValueCriteria))
            return false;

        SingleValueCriteria other = (SingleValueCriteria) obj;

        return other.getValue().equals(this.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getValue());
    }
}
