package realties;

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
public abstract class Home extends Realty {
    private RoomCount roomCount;

    public Home(String externalId, String title, String description, AdType adType, BigDecimal price, String url, String imageUrl, LocalDate publishDate, AdvertiserType advertiserType, BigDecimal surfaceArea, AreaMeasurementUnit areaMeasurementUnit, Optional<Boolean> registered, RoomCount roomCount) {
        super(externalId, title, description, adType, price, url, imageUrl, publishDate, advertiserType, surfaceArea, areaMeasurementUnit, registered);
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
