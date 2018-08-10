package com.paki.controller;

import com.paki.scrape.entities.ScrapeSettings;
import com.paki.scrape.services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
public class ScrapeSettingsController {

    private SettingsService settingsService;

    @Autowired
    public ScrapeSettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping
    private ScrapeSettings getSettings() {
        return settingsService.getSettings();
    }

    @PutMapping
    private ResponseEntity saveSettings(@RequestBody ScrapeSettings settings) {
        settingsService.save(settings);
        return ResponseEntity.ok().build();
    }
}
