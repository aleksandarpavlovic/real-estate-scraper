package realties;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public abstract class Home extends Realty {
    private BigDecimal roomCount;

    @Builder
    public Home(String title, String description, String realtyType, String adType, BigDecimal price, String url, String imageUrl, LocalDate publishDate, String advertiserType, BigDecimal surfaceArea, Optional<Boolean> registered, BigDecimal roomCount) {
        super(title, description, realtyType, adType, price, url, imageUrl, publishDate, advertiserType, surfaceArea, registered);
        this.roomCount = roomCount;
    }

    @Override
    public String toString() {
        return super.toString() + System.lineSeparator() + "Room count: " + roomCount;
    }
}
