package com.paki.scrape.topad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.paki.scrape.entities.SearchProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = TopAdCondition.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TopAdParameterizedCondition.class, name = "Parameterized")
})
public class TopAdCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topad_condition_generator")
    @SequenceGenerator(name="topad_condition_generator", sequenceName = "topad_condition_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "searchProfileId")
    @JsonBackReference
    private SearchProfile searchProfile;

    @ManyToOne
    private TopAdDefinition definition;

    public TopAdCondition(TopAdDefinition definition) {
        this.definition = definition;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;
        TopAdCondition other = (TopAdCondition) obj;
        return other.definition == this.definition;
    }

    @Override
    public int hashCode() {
        return definition.hashCode();
    }

    @Override
    public String toString() {
        return definition.getText();
    }
}
