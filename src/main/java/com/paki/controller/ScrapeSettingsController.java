package com.paki.controller;

import com.paki.persistence.scrape.ScrapeSettingsRepository;
import com.paki.scrape.entities.ScrapeSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/settings")
public class ScrapeSettingsController {

    private ScrapeSettingsRepository settingsRepository;

    @Autowired
    public ScrapeSettingsController(ScrapeSettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @GetMapping
    private ScrapeSettings getSettings() {
        Optional<ScrapeSettings> settings = settingsRepository.findById(ScrapeSettings.SINGLETON_ID);
        return settings.orElseGet(ScrapeSettings::new);
    }

    @PutMapping
    private ResponseEntity saveSettings(@RequestBody ScrapeSettings settings) {
        settingsRepository.save(settings);
        return ResponseEntity.ok().build();
    }
}
