package realties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import realties.enums.AdSource;
import realties.enums.AdType;
import realties.enums.AdvertiserType;
import realties.enums.AreaMeasurementUnit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public abstract class Realty {
    private AdSource source;
    private String externalId;
    private String title;
    private String description;
    private AdType adType;
    private BigDecimal price;
    private String url;
    private String imageUrl;
    private LocalDate publishDate;
    private AdvertiserType advertiserType;
    private BigDecimal surfaceArea;
    private AreaMeasurementUnit areaMeasurementUnit;
    private Optional<Boolean> registered;

    public Realty(String externalId, String title, String description, AdType adType, BigDecimal price, String url, String imageUrl, LocalDate publishDate, AdvertiserType advertiserType, BigDecimal surfaceArea, AreaMeasurementUnit areaMeasurementUnit, Optional<Boolean> registered) {
        this.externalId = externalId;
        this.title = title;
        this.description = description;
        this.adType = adType;
        this.price = price;
        this.url = url;
        this.imageUrl = imageUrl;
        this.publishDate = publishDate;
        this.advertiserType = advertiserType;
        this.surfaceArea = surfaceArea;
        this.areaMeasurementUnit = areaMeasurementUnit;
        this.registered = registered;
    }

    @Override
    public String toString() {
        String registration = registered.isPresent() ? registered.get() ? "uknjizeno" : "neuknjizeno" : "";
        return "Ad source: \t\t\t" + source + System.lineSeparator() + "External Id: \t\t" + externalId + System.lineSeparator()
                + "Title: \t\t\t\t" + title + System.lineSeparator() + "Description: \t\t" + description + System.lineSeparator()
                + System.lineSeparator() + "Ad type: \t\t\t" + adType + System.lineSeparator() + "Price: \t\t\t\t" + price
                + System.lineSeparator() + "Url: \t\t\t\t" + url + System.lineSeparator() + "Thumbnail Url: \t\t" + imageUrl
                + System.lineSeparator() + "Publish date: \t\t" + publishDate + System.lineSeparator() + "Advertiser: \t\t" + advertiserType
                + System.lineSeparator() + "Area: \t\t\t\t" + surfaceArea + " " + areaMeasurementUnit + System.lineSeparator()
                + (registered.isPresent() ? "Registration: \t\t" + registration : "");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof Realty) {
            return ((Realty) obj).getSource() == this.getSource()
                    && ((Realty) obj).getExternalId().equals(this.getExternalId());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, externalId);
    }
}
