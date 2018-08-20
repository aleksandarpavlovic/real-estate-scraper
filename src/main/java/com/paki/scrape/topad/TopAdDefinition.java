package com.paki.scrape.topad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TopAdDefinition {
    @Id
    @Enumerated(EnumType.STRING)
    private TopAdName name;
    private TopAdType type;
    private String text;

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof TopAdDefinition))
            return false;
        TopAdDefinition other = (TopAdDefinition) obj;
        return this.name == other.getName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, text);
    }
}
