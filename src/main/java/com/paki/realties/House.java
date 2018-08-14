package com.paki.realties;

import com.paki.realties.enums.*;
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
public class House extends Home {

    @Builder
    public House(AdSource source, String externalId, String title, String description, String location, AdType adType, BigDecimal price, String url, String imageUrl, LocalDate publishDate, AdvertiserType advertiserType, BigDecimal surfaceArea, AreaMeasurementUnit areaMeasurementUnit, RegistrationType registered, RoomCount roomCount) {
        super(source, externalId, title, description, location, adType, price, url, imageUrl, publishDate, advertiserType, surfaceArea, areaMeasurementUnit, registered, roomCount);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof House;
    }
}
