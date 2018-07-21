package realties;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import realties.enums.AdType;
import realties.enums.AdvertiserType;
import realties.enums.AreaMeasurementUnit;
import realties.enums.RoomCount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class House extends Home {

    @Builder
    public House(String externalId, String title, String description, AdType adType, BigDecimal price, String url, String imageUrl, LocalDate publishDate, AdvertiserType advertiserType, BigDecimal surfaceArea, AreaMeasurementUnit areaMeasurementUnit, Optional<Boolean> registered, RoomCount roomCount) {
        super(externalId, title, description, adType, price, url, imageUrl, publishDate, advertiserType, surfaceArea, areaMeasurementUnit, registered, roomCount);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof House;
    }
}
