package com.paki.scrape.top_ad;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class TopAdParameterizedCondition extends TopAdCondition {
    private String parameter;

    public TopAdParameterizedCondition(TopAdDefinition definition, String parameter) {
        super(definition);
        this.parameter = parameter;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!super.equals(obj))
            return false;
        if (obj.getClass() != this.getClass())
            return false;
        TopAdParameterizedCondition other = (TopAdParameterizedCondition) obj;
        return other.getParameter().equals(this.getParameter());
    }
}
