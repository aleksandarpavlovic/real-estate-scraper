package com.paki.dto.realties;

import com.paki.dto.ValueDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApartmentDTO extends HomeDTO {
    public ApartmentDTO(ValueDTO adSource, ValueDTO adType, ValueDTO title, ValueDTO description, ValueDTO location, ValueDTO price, ValueDTO url, ValueDTO imageUrl, ValueDTO publishDate, ValueDTO advertiser, ValueDTO surfaceArea, ValueDTO registration, ValueDTO rooms) {
        super(RealtyDTOType.APARTMENT, adSource, adType, title, description, location, price, url, imageUrl, publishDate, advertiser, surfaceArea, registration, rooms);
    }
}
