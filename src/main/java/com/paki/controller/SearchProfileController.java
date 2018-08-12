package com.paki.controller;

import com.paki.dto.SearchProfileDTO;
import com.paki.dto.transformers.SearchProfileDTOTransformer;
import com.paki.scrape.entities.SearchProfile;
import com.paki.scrape.services.SearchProfileService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.beans.Expression;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profiles")
public class SearchProfileController {

    private static final String FIELDS = "fields";

    private SearchProfileService profileService;
    private SearchProfileDTOTransformer transformer;

    @Autowired
    public SearchProfileController(SearchProfileService profileService, SearchProfileDTOTransformer transformer) {
        this.profileService = profileService;
        this.transformer = transformer;
    }

    @GetMapping
    private List<SearchProfileDTO> getProfiles(@RequestParam(name = FIELDS, required = false) List<String> fields) {
        List<SearchProfile> profiles = profileService.findAll();
        return filterFields(profiles, fields).stream().map(transformer::transformSearchProfileToDTO).collect(Collectors.toList());
    }

    private List<SearchProfile> filterFields(List<SearchProfile> profiles, List<String> fields) {
        if (fields == null || fields.isEmpty())
            return profiles;

        List<Pair> gettersAndSetters = fields.stream()
                .filter(field -> field != null && !field.equals(""))
                .map(field -> {
                    String capitalizedField = field.substring(0, 1).toUpperCase() + field.substring(1);
                    return new Pair("get" + capitalizedField, "set" + capitalizedField);
                })
                .collect(Collectors.toList());

        return profiles.stream()
                .map(profile -> getLightProfile(profile, gettersAndSetters))
                .collect(Collectors.toList());
    }

    private SearchProfile getLightProfile(SearchProfile profile, List<Pair> gettersAndSetters) {
        SearchProfile lightProfile = new SearchProfile();
        lightProfile.setId(profile.getId());
        try {
            for (Pair getterAndSetter : gettersAndSetters) {
                Expression getExpr = new Expression(profile, getterAndSetter.first, new Object[0]);
                getExpr.execute();
                Expression setExpr = new Expression(lightProfile, getterAndSetter.second, new Object[]{getExpr.getValue()});
                setExpr.execute();
            }
        } finally {
            return lightProfile;
        }
    }

    @GetMapping("/{id}")
    private SearchProfileDTO getProfile(@PathVariable("id") Long id) {
        return profileService.findProfileById(id).map(transformer::transformSearchProfileToDTO).orElse(null);
    }

    @PostMapping
    private ResponseEntity saveNewProfile(@RequestBody SearchProfileDTO dto) {
        try {
            SearchProfile profile = transformer.transformDTOToSearchProfile(dto);
            profileService.createSearchProfile(profile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_METHOD_FAILURE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    private ResponseEntity updateProfile(@PathVariable("id") Long id, @RequestBody SearchProfileDTO dto) {
        try {
            SearchProfile profile = transformer.transformDTOToSearchProfile(dto);
            profileService.updateSearchProfile(id, profile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_METHOD_FAILURE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deleteProfile(@PathVariable("id") Long id) {
        try {
            profileService.deleteSearchProfile(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_METHOD_FAILURE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Pair {
        String first;
        String second;
    }
}
