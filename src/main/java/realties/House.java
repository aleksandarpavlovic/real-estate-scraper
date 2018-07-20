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
public class House extends Home {

    @Builder
    public House(String title, String description, String realtyType, String adType, BigDecimal price, String url, String imageUrl, LocalDate publishDate, String advertiserType, BigDecimal surfaceArea, Optional<Boolean> registered, BigDecimal roomCount) {
        super(title, description, realtyType, adType, price, url, imageUrl, publishDate, advertiserType, surfaceArea, registered, roomCount);
    }
}
