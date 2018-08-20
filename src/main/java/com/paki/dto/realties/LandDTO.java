package com.paki.dto.realties;

import com.paki.dto.ValueDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LandDTO extends RealtyDTO {

    public LandDTO(ValueDTO adSource, ValueDTO adType, ValueDTO title, ValueDTO description, ValueDTO location, ValueDTO price, ValueDTO url, ValueDTO imageUrl, ValueDTO publishDate, ValueDTO advertiser, ValueDTO surfaceArea, ValueDTO registration) {
        super(RealtyDTOType.LAND, adSource, adType, title, description, location, price, url, imageUrl, publishDate, advertiser, surfaceArea, registration);
    }
}