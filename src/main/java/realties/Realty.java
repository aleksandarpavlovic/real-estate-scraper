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
public abstract class Realty {
    private String title;
    private String description;
    private String realtyType;
    private String adType;
    private BigDecimal price;
    private String url;
    private String imageUrl;
    private LocalDate publishDate;
    private String advertiserType;
    private BigDecimal surfaceArea;
    private Optional<Boolean> registered;

    @Builder
    public Realty(String title, String description, String realtyType, String adType, BigDecimal price, String url, String imageUrl, LocalDate publishDate, String advertiserType, BigDecimal surfaceArea, Optional<Boolean> registered) {
        this.title = title;
        this.description = description;
        this.realtyType = realtyType;
        this.adType = adType;
        this.price = price;
        this.url = url;
        this.imageUrl = imageUrl;
        this.publishDate = publishDate;
        this.advertiserType = advertiserType;
        this.surfaceArea = surfaceArea;
        this.registered = registered;
    }

    @Override
    public String toString() {
        String registration = registered.isPresent() ? registered.get() ? "uknjizeno" : "neuknjizeno" : "";
        return "Title: \t\t\t\t" + title + System.lineSeparator() + "Description: \t\t" + description + System.lineSeparator() + "Realty type: \t\t" + realtyType
                + System.lineSeparator() + "Ad type: \t\t\t" + adType + System.lineSeparator() + "Price: \t\t\t\t" + price
                + System.lineSeparator() + "Url: \t\t\t\t" + url + System.lineSeparator() + "Thumbnail Url: \t\t" + imageUrl
                + System.lineSeparator() + "Publish date: \t\t" + publishDate + System.lineSeparator() + "Advertiser: \t\t" + advertiserType
                + System.lineSeparator() + "Area: \t\t\t\t" + surfaceArea + System.lineSeparator()
                + (registered.isPresent() ? System.lineSeparator() + "Registration: \t\t" + registration : "");
    }
}
