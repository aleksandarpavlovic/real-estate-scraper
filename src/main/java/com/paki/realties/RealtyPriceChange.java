package com.paki.realties;

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
@Entity
public class RealtyPriceChange {
    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "realtyId")
    private Realty realty;

    private Long changeRunNumber;

    private LocalDateTime changeDate;

    @Column(precision = 20, scale = 2)
    private BigDecimal delta;

    @Builder
    public RealtyPriceChange(Realty realty, Long changeRunNumber, LocalDateTime changeDate, BigDecimal delta) {
        this.realty = realty;
        this.changeRunNumber = changeRunNumber;
        this.changeDate = changeDate;
        this.delta = delta;
    }
}
