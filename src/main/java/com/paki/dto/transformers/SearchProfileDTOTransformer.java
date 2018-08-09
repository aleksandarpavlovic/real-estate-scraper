package com.paki.dto.transformers;

import com.paki.dto.SearchDTO;
import com.paki.dto.SearchProfileDTO;
import com.paki.scrape.entities.Search;
import com.paki.scrape.entities.SearchProfile;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.stream.Collectors;

public class SearchProfileDTOTransformer {

    private CriteriaDTOTransformer criteriaDTOTransformer;

    @Autowired
    public SearchProfileDTOTransformer(CriteriaDTOTransformer criteriaDTOTransformer) {
        this.criteriaDTOTransformer = criteriaDTOTransformer;
    }

    public SearchProfileDTO transformSearchProfileToDTO(SearchProfile searchProfile) {
        SearchProfileDTO searchProfileDTO = new SearchProfileDTO();

        searchProfileDTO.setId(searchProfile.getId());
        searchProfileDTO.setName(searchProfile.getName());
        searchProfileDTO.setTopAdConditions(searchProfile.getTopAdConditions());

        searchProfileDTO.setSearchDTO(transformSearchToDTO(searchProfile.getSearch()));

        return searchProfileDTO;
    }

    private SearchDTO transformSearchToDTO(Search search) {
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setRealtyType(criteriaDTOTransformer.transformToValueDTO(search.getRealtyType()));
        if (search.getCriteria() == null) {
            searchDTO.setCriteria(Collections.emptySet());
        } else {
            searchDTO.setCriteria(search.getCriteria().stream().map(criteriaDTOTransformer::transformCriteriaToDTO).collect(Collectors.toSet()));
        }
        return searchDTO;
    }


}
