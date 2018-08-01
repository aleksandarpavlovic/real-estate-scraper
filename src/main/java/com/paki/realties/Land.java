package com.paki.realties;

import com.paki.realties.enums.AdType;
import com.paki.realties.enums.AdvertiserType;
import com.paki.realties.enums.AreaMeasurementUnit;
import com.paki.realties.enums.RegistrationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Land extends Realty{
    @Builder
    public Land(String externalId, String title, String description, AdType adType, BigDecimal price, String url, String imageUrl, LocalDate publishDate, AdvertiserType advertiserType, BigDecimal surfaceArea, AreaMeasurementUnit areaMeasurementUnit, RegistrationType registered) {
        super(externalId, title, description, adType, price, url, imageUrl, publishDate, advertiserType, surfaceArea, areaMeasurementUnit, registered);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof Land;
    }
}
