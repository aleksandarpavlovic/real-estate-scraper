package com.paki.dto.realties;

import com.paki.dto.ValueDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class RealtyDTO {
    RealtyDTOType realtyType;
    ValueDTO adSource;
    ValueDTO adType;
    ValueDTO title;
    ValueDTO description;
    ValueDTO location;
    ValueDTO price;
    ValueDTO url;
    ValueDTO imageUrl;
    ValueDTO publishDate;
    ValueDTO advertiser;
    ValueDTO surfaceArea;
    ValueDTO registration;

    public RealtyDTO(RealtyDTOType realtyType, ValueDTO adSource, ValueDTO adType, ValueDTO title, ValueDTO description, ValueDTO location, ValueDTO price, ValueDTO url, ValueDTO imageUrl, ValueDTO publishDate, ValueDTO advertiser, ValueDTO surfaceArea, ValueDTO registration) {
        this.realtyType = realtyType;
        this.adSource = adSource;
        this.adType = adType;
        this.title = title;
        this.description = description;
        this.location = location;
        this.price = price;
        this.url = url;
        this.imageUrl = imageUrl;
        this.publishDate = publishDate;
        this.advertiser = advertiser;
        this.surfaceArea = surfaceArea;
        this.registration = registration;
    }
}
