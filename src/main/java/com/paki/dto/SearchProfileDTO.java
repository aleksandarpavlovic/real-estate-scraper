package com.paki.dto;

import com.paki.scrape.topad.TopAdCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchProfileDTO {
    private Long id;
    private String name;
    private SearchDTO searchDTO;
    private Set<TopAdCondition> topAdConditions;
}
