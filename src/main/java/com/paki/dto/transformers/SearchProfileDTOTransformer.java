package com.paki.dto.transformers;

import com.paki.dto.SearchDTO;
import com.paki.dto.SearchProfileDTO;
import com.paki.realties.enums.RealtyType;
import com.paki.scrape.entities.Search;
import com.paki.scrape.entities.SearchProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class SearchProfileDTOTransformer {

    private CriteriaDTOTransformer criteriaDTOTransformer;

    @Autowired
    public SearchProfileDTOTransformer(CriteriaDTOTransformer criteriaDTOTransformer) {
        this.criteriaDTOTransformer = criteriaDTOTransformer;
    }

    public SearchProfileDTO transformSearchProfileToDTO(SearchProfile searchProfile) {
        if (searchProfile == null)
            return null;
        SearchProfileDTO searchProfileDTO = new SearchProfileDTO();
        searchProfileDTO.setId(searchProfile.getId());
        searchProfileDTO.setName(searchProfile.getName());
        searchProfileDTO.setTopAdConditions(searchProfile.getTopAdConditions());
        searchProfileDTO.setSearch(transformSearchToDTO(searchProfile.getSearch()));
        return searchProfileDTO;
    }

    public SearchProfile transformDTOToSearchProfile(SearchProfileDTO dto) {
        if (dto == null)
            return null;
        SearchProfile profile = new SearchProfile();
        profile.setId(dto.getId());
        profile.setName(dto.getName());
        profile.setSearch(transformDTOToSearch(dto.getSearch()));
        profile.setTopAdConditions(dto.getTopAdConditions());
        return profile;
    }

    private SearchDTO transformSearchToDTO(Search search) {
        if (search == null)
            return null;
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setRealtyType(criteriaDTOTransformer.transformToValueDTO(search.getRealtyType()));
        if (search.getCriteria() == null) {
            searchDTO.setCriteria(Collections.emptySet());
        } else {
            searchDTO.setCriteria(search.getCriteria().stream().map(criteriaDTOTransformer::transformCriteriaToDTO).collect(Collectors.toSet()));
        }
        return searchDTO;
    }

    private Search transformDTOToSearch(SearchDTO dto) {
        if (dto == null)
            return null;
        Search search = new Search();
        search.setRealtyType(RealtyType.get(dto.getRealtyType().getName()));
        if (dto.getCriteria() == null) {
            search.setCriteria(Collections.emptySet());
        } else {
            search.setCriteria(dto.getCriteria().stream().map(criteriaDTOTransformer::transformDTOToCriteria).collect(Collectors.toSet()));
        }
        return search;
    }
}
