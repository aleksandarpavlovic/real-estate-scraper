package scrape;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Advertisement {
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
    private BigDecimal roomCount;

    public Advertisement(String title, String description, String realtyType, String adType, BigDecimal price, String url, String imageUrl, LocalDate publishDate, String advertiserType, BigDecimal surfaceArea, BigDecimal roomCount) {
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
        this.roomCount = roomCount;
    }

    @Override
    public String toString() {
        return title + System.lineSeparator() + description + System.lineSeparator() + realtyType
                + System.lineSeparator() + adType + System.lineSeparator() + price
                + System.lineSeparator() + url + System.lineSeparator() + imageUrl
                + System.lineSeparator() + publishDate + System.lineSeparator() + advertiserType
                + System.lineSeparator() + surfaceArea + System.lineSeparator() + roomCount;
    }
}
