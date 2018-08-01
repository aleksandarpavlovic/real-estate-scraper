package com.paki.scrape.top_ad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TopAdDefinition {
    @Id
    @Enumerated(EnumType.STRING)
    private TopAdName name;
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
}
