package com.paki.scrape.entities;

import com.paki.realties.Realty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RealtyPriceChange {
    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Realty realty;

    private LocalDateTime changeDate;

    @Column(precision = 20, scale = 2)
    private BigDecimal delta;

    @Builder
    public RealtyPriceChange(Realty realty, LocalDateTime changeDate, BigDecimal delta) {
        this.realty = realty;
        this.changeDate = changeDate;
        this.delta = delta;
    }
}
