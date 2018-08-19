package com.paki.scrape.services;

import com.paki.persistence.scrape.ScrapeSettingsRepository;
import com.paki.scheduler.SchedulerService;
import com.paki.scrape.entities.ScrapeSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingsService {

    private ScrapeSettingsRepository settingsRepository;
    private SchedulerService schedulerService;

    @Autowired
    public SettingsService(ScrapeSettingsRepository settingsRepository, SchedulerService schedulerService) {
        this.settingsRepository = settingsRepository;
        this.schedulerService = schedulerService;
    }

    public ScrapeSettings getSettings() {
        Optional<ScrapeSettings> settings = settingsRepository.findById(ScrapeSettings.SINGLETON_ID);
        return settings.orElseGet(ScrapeSettings::new);
    }

    public void save(ScrapeSettings settings) {
        settings.setScheduledPeriod(Math.max(SchedulerService.MIN_JOB_PERIOD, settings.getScheduledPeriod()));
        settingsRepository.save(settings);
        schedulerService.schedule(settings.getScheduledPeriod());
    }

    public void deleteSettings() {
        settingsRepository.deleteAll();
    }
}
