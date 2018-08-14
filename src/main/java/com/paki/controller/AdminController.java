package com.paki.controller;

import com.paki.realties.enums.*;
import com.paki.scrape.criteria.*;
import com.paki.scrape.criteria.definitions.CriteriaDefinitions;
import com.paki.scrape.entities.ScrapeSettings;
import com.paki.scrape.entities.Search;
import com.paki.scrape.entities.SearchProfile;
import com.paki.scrape.services.ScrapeService;
import com.paki.scrape.services.SearchProfileService;
import com.paki.scrape.services.SettingsService;
import com.paki.scrape.topad.TopAdCondition;
import com.paki.scrape.topad.TopAdName;
import com.paki.scrape.topad.TopAdParameterizedCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private ScrapeService scrapeService;
    private SearchProfileService searchProfileService;
    private SettingsService settingsService;

    @Autowired
    public AdminController(ScrapeService scrapeService, SearchProfileService searchProfileService, SettingsService settingsService) {
        this.scrapeService = scrapeService;
        this.searchProfileService = searchProfileService;
        this.settingsService = settingsService;
    }

    @PostMapping("/scrape")
    private ResponseEntity scrape() {
        scrapeService.scrape();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/test-profile")
    private ResponseEntity postTestProfile() {
        SearchProfile testProfile = createTestProfile();
        searchProfileService.createSearchProfile(testProfile);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/test-settings")
    private ResponseEntity postSettings() {
        ScrapeSettings settings = createTestSettings();
        settingsService.save(settings);
        return ResponseEntity.ok().build();
    }

    private ScrapeSettings createTestSettings() {
        ScrapeSettings settings = new ScrapeSettings();
        settings.setEmailList(Arrays.asList("alex.pavlovic92@gmail.com", "pa173064m@student.etf.bg.ac.rs"));
        settings.setScheduledPeriod(11116);
        return settings;
    }

    private SearchProfile createTestProfile() {
        SearchProfile profile = new SearchProfile();
        profile.setName("testProfile");
        profile.setSearch(new Search());
        profile.getSearch().setCriteria(testCriteria());
        profile.setTopAdConditions(testTopAd());
        return profile;
    }

    private static Set<BaseCriteria> testCriteria() {
        Set<BaseCriteria> criteriaList = new HashSet<>();
        criteriaList.add(new SingleValueCriteria(CriteriaDefinitions.AD_TYPE, AdType.SELL.name()));
        criteriaList.add(new SingleValueCriteria(CriteriaDefinitions.REALTY_TYPE, RealtyType.APARTMENT.name()));
//        criteriaList.add(new MultiValueCriteria(CriteriaDefinitions.ADVERTISER, new HashSet<>(Arrays.asList(AdvertiserType.OWNER.name()))));
        criteriaList.add(new MultiValueCriteria(CriteriaDefinitions.REGISTRATION, new HashSet<>(Arrays.asList(RegistrationType.REGISTERED.name()))));
        criteriaList.add(new IntegerRangeCriteria(CriteriaDefinitions.PRICE, 0, 51000));
        criteriaList.add(new RangeWithUnitCriteria(CriteriaDefinitions.SURFACE_M2, 12, 46, AreaMeasurementUnit.SQUARE_METER));
        criteriaList.add(new StringRangeCriteria(CriteriaDefinitions.ROOM_COUNT, RoomCount.RC_0_5.name(), RoomCount.RC_2_0.name()));
        criteriaList.add(new IntegerRangeCriteria(CriteriaDefinitions.FLOOR, CriteriaDefinitions.HIGH_GROUND_FLOOR, 1));
        criteriaList.add(new MultiValueCriteria(CriteriaDefinitions.LOCATION, new HashSet<>(Arrays.asList("1_46_3", "4"))));//medakovic2 i kv

        return criteriaList;
    }

    private static Set<TopAdCondition> testTopAd() {
        Set<TopAdCondition> conditionSet = new HashSet<>();
        conditionSet.add(new TopAdCondition(TopAdName.NEW_AD));
        conditionSet.add(new TopAdCondition(TopAdName.PRICE_DROP));
        conditionSet.add(new TopAdParameterizedCondition(TopAdName.PRICE_IN_TOP_N_PERCENT, "5"));
        conditionSet.add(new TopAdParameterizedCondition(TopAdName.PRICE_PER_M2_IN_TOP_N_PERCENT, "5"));
        return conditionSet;
    }

}
