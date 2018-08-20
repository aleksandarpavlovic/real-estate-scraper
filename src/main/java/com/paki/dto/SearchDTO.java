package com.paki.dto;

import com.paki.dto.criteria.CriteriaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {
    private ValueDTO realtyType;
    private Set<CriteriaDTO> criteria;
}
