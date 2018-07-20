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
public class Land extends Realty{
    private String landType;

    @Builder
    public Land(String title, String description, String realtyType, String adType, BigDecimal price, String url, String imageUrl, LocalDate publishDate, String advertiserType, BigDecimal surfaceArea, Optional<Boolean> registered, String landType) {
        super(title, description, realtyType, adType, price, url, imageUrl, publishDate, advertiserType, surfaceArea, registered);
        this.landType = landType;
    }

    @Override
    public String toString() {
        return super.toString() + System.lineSeparator() + "Land type: " + landType;
    }
}
