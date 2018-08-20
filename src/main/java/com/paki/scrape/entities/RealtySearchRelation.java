package com.paki.scrape.entities;

import com.paki.realties.Realty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(RealtySearchRelationPK.class)
public class RealtySearchRelation {

    @Id
    @ManyToOne
    @JoinColumn(name = "realtyId", referencedColumnName = "id")
    private Realty realty;

    @Id
    @ManyToOne
    @JoinColumn(name = "searchId", referencedColumnName = "id")
    private Search search;
}
