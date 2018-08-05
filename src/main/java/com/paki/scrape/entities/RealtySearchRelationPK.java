package com.paki.scrape.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RealtySearchRelationPK implements Serializable {
    private Long realty;
    private Long search;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof RealtySearchRelationPK) {
            RealtySearchRelationPK other = (RealtySearchRelationPK)obj;
            return realty == other.realty && search == other.search;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(realty) + Long.hashCode(search);
    }
}
