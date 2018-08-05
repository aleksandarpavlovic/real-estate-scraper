package com.paki.realties;

import com.paki.realties.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public abstract class Home extends Realty {
    @Enumerated(EnumType.STRING)
    private RoomCount roomCount;

    public Home(AdSource source, String externalId, String title, String description, String location, AdType adType, BigDecimal price, String url, String imageUrl, LocalDate publishDate, AdvertiserType advertiserType, BigDecimal surfaceArea, AreaMeasurementUnit areaMeasurementUnit, RegistrationType registered, RoomCount roomCount) {
        super(source, externalId, title, description, location, adType, price, url, imageUrl, publishDate, advertiserType, surfaceArea, areaMeasurementUnit, registered);
        this.roomCount = roomCount;
    }

    @Override
    public String toString() {
        return super.toString() + System.lineSeparator() + "Room count: \t\t" + roomCount;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof Home;
    }
}
