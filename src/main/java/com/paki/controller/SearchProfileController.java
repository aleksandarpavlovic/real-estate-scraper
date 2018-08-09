package com.paki.controller;

import com.paki.scrape.entities.SearchProfile;
import com.paki.scrape.services.SearchProfileService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class SearchProfileController {

    SearchProfileService profileService;

    @Autowired
    public SearchProfileController(SearchProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    private List<SearchProfile> getProfiles() {
        return profileService.findAll();
    }

    @GetMapping(value = "/names")
    private List<String> getProfilesNames() {
        return profileService.getProfilesNames();
    }

    @GetMapping(value = "/{name}")
    private SearchProfile getProfile(@PathVariable("name") String name) {
        return profileService.findProfileByName(name).orElse(null);
    }

    @PostMapping
    private ResponseEntity saveNewProfile(@RequestBody SearchProfile profile) {
        try {
            profileService.createSearchProfile(profile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_METHOD_FAILURE).body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    private ResponseEntity updateProfile(@PathVariable("id") Long id, @RequestBody SearchProfile profile) {
        try {
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
}
