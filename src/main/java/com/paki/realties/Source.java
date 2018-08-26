package com.paki.realties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "source_generator")
    @SequenceGenerator(name="source_generator", sequenceName = "source_seq")
    private Long id;

    private String name;

    public Source(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Source))
            return false;

        Source other = (Source) obj;

        if (this.getName() == null) {
            if (other.getName() == null)
                return true;
            else
                return false;
        } else if (other.getName() == null)
            return false;

        return this.getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
