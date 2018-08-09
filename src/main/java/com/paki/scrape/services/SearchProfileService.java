package com.paki.scrape.services;

import com.paki.exceptions.BusinessException;
import com.paki.persistence.realties.*;
import com.paki.persistence.scrape.RealtySearchRelationRepository;
import com.paki.persistence.scrape.SearchProfileRepository;
import com.paki.scrape.criteria.BaseCriteria;
import com.paki.scrape.entities.Search;
import com.paki.scrape.entities.SearchProfile;
import com.paki.scrape.synchronization.GlobalLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SearchProfileService {
    private CriteriaService criteriaService;
    private ApartmentRepository apartmentRepository;
    private HouseRepository houseRepository;
    private LandRepository landRepository;
    private RealtySearchRelationRepository relationRepository;
    private RealtyPriceChangeRepository priceChangeRepository;
    private SearchProfileRepository profileRepository;
    private GlobalLock globalLock;
    private List<RealtyRepository> realtyRepositories;

    @Autowired
    public SearchProfileService(CriteriaService criteriaService, ApartmentRepository apartmentRepository, HouseRepository houseRepository, LandRepository landRepository, RealtySearchRelationRepository relationRepository, RealtyPriceChangeRepository priceChangeRepository, SearchProfileRepository profileRepository, GlobalLock globalLock) {
        this.criteriaService = criteriaService;
        this.apartmentRepository = apartmentRepository;
        this.houseRepository = houseRepository;
        this.landRepository = landRepository;
        this.relationRepository = relationRepository;
        this.priceChangeRepository = priceChangeRepository;
        this.profileRepository = profileRepository;
        this.globalLock = globalLock;
        this.realtyRepositories = Arrays.asList(apartmentRepository, houseRepository, landRepository);
    }

    public List<SearchProfile> findAll() {
        return profileRepository.findAll();
    }

    public List<String> getProfilesNames() {
        return profileRepository.findAll().stream().map(SearchProfile::getName).collect(Collectors.toList());
    }

    public Optional<SearchProfile> findProfileByName(String name) {
        List<SearchProfile> profiles = profileRepository.findByName(name);
        if (profiles.isEmpty())
            return Optional.empty();
        else
            return Optional.of(profiles.get(0));
    }

    public void createSearchProfile(SearchProfile profile) {
        Optional<SearchProfile> dbProfile = findProfileByName(profile.getName());
        if (dbProfile.isPresent())
            throw new BusinessException("Profil sa imenom: " + profile.getName() + " vec postoji!");

        criteriaService.normalizeLocationCriteria(profile.getSearch().getCriteria());
        profileRepository.save(profile);
    }

    @Transactional
    public void updateSearchProfile(Long id, SearchProfile profile) {
        if (!globalLock.tryLock())
            throw new BusinessException("Izmena profila nije moguca tokom trajanja skeniranja oglasa. Pokusajte kasnije.");
        try {
            Optional<SearchProfile> dbProfile = profileRepository.findById(id);
            if (!dbProfile.isPresent())
                throw new BusinessException("Azuriranje profila neuspelo. Profil ne postoji u sistemu.");

            boolean searchChanged = !isEqualSearch(dbProfile.get(), profile);
            if (searchChanged)
                deleteRealties(dbProfile.get());
            profile.setId(id);
            criteriaService.normalizeLocationCriteria(profile.getSearch().getCriteria());
            profileRepository.save(profile);
        } finally {
            globalLock.unlock();
        }
    }

    public void deleteSearchProfile(Long id) {
        if (!globalLock.tryLock())
            throw new BusinessException("Brisanje profila nije moguce tokom trajanja skeniranja oglasa. Pokusajte kasnije.");
        try {
            Optional<SearchProfile> dbProfile = profileRepository.findById(id);
            if (!dbProfile.isPresent())
                throw new BusinessException("Brisanje profila neuspelo. Profil ne postoji u sistemu.");

            deleteRealties(dbProfile.get());
            profileRepository.deleteById(id);
        } finally {
            globalLock.unlock();
        }
    }

    /**
     * Deletes realties related only to this profile.
     * Corresponding RealtyPriceChange and RealtySearchRelation entities must be deleted as well
     * @param profile
     */
    private void deleteRealties(SearchProfile profile) {
        // delete relations
        relationRepository.deleteBySearchId(profile.getSearch().getId());
        List<Long> realtiesIds = hangingRealtiesIds();
        // delete price changes
        priceChangeRepository.deleteByRealtyIds(realtiesIds);
        // delete hanging realties
        realtyRepositories.forEach(repo -> repo.deleteByIdIn(realtiesIds));
    }

    private List<Long> hangingRealtiesIds() {
        List<Long> ids = new ArrayList<>();
        for (RealtyRepository repo: realtyRepositories)
            ids.addAll(repo.findIdsOfHangingRealties());
        return ids;
    }

    private boolean isEqualSearch(SearchProfile p1, SearchProfile p2) {
        Search s1 = p1.getSearch();
        Search s2 = p2.getSearch();

        if (s1.getRealtyType() != s2.getRealtyType())
            return false;
        if (s1.getCriteria().size() != s2.getCriteria().size())
            return false;
        for (BaseCriteria criteria: s1.getCriteria()) {
            if (!s2.getCriteria().contains(criteria))
                return false;
        }
        return true;
    }
}
