package com.paki.scrape.topad;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class TopAdParameterizedCondition extends TopAdCondition {
    private String parameter;

    public TopAdParameterizedCondition(TopAdName topAdName, String parameter) {
        super(topAdName);
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
        if (this.getParameter() == null) {
            if (other.getParameter() != null)
                return false;
        } else if (other.getParameter() == null) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return super.toString().replaceFirst("\\?.+?\\?", parameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parameter);
    }
}
