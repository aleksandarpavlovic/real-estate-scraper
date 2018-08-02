package com.paki.realties;

import com.paki.realties.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"externalId", "source"}))
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Realty {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AdSource source;
    private String externalId;
    private String title;
    @Lob
    private String description;
    private String location;
    @Enumerated(EnumType.STRING)
    private AdType adType;
    @Column(precision = 20, scale = 2)
    private BigDecimal price;
    private String url;
    private String imageUrl;
    private LocalDate publishDate;
    @Enumerated(EnumType.STRING)
    private AdvertiserType advertiserType;
    @Column(precision = 20, scale = 2)
    private BigDecimal surfaceArea;
    @Enumerated(EnumType.STRING)
    private AreaMeasurementUnit areaMeasurementUnit;
    @Enumerated(EnumType.STRING)
    private RegistrationType registered;

    public Realty(String externalId, String title, String description, String location, AdType adType, BigDecimal price, String url, String imageUrl, LocalDate publishDate, AdvertiserType advertiserType, BigDecimal surfaceArea, AreaMeasurementUnit areaMeasurementUnit, RegistrationType registered) {
        this.externalId = externalId;
        this.title = title;
        this.description = description;
        this.location = location;
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
        return "Ad source: \t\t\t" + source + System.lineSeparator() + "External Id: \t\t" + externalId
                + System.lineSeparator() + "Title: \t\t\t\t" + title + System.lineSeparator() + "Description: \t\t" + description
                + System.lineSeparator() + "Ad type: \t\t\t" + adType + System.lineSeparator() + "Location: \t\t\t" + location + System.lineSeparator() + "Price: \t\t\t\t" + price
                + System.lineSeparator() + "Url: \t\t\t\t" + url + System.lineSeparator() + "Thumbnail Url: \t\t" + imageUrl
                + System.lineSeparator() + "Publish date: \t\t" + publishDate + System.lineSeparator() + "Advertiser: \t\t" + advertiserType
                + System.lineSeparator() + "Area: \t\t\t\t" + surfaceArea + " " + areaMeasurementUnit
                + System.lineSeparator() + "Registration: \t\t" + registered;
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
