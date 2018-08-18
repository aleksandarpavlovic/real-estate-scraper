package com.paki.scrape.services;

import com.paki.exceptions.BusinessException;
import com.paki.persistence.realties.*;
import com.paki.persistence.scrape.RealtySearchRelationRepository;
import com.paki.persistence.scrape.SearchProfileRepository;
import com.paki.persistence.scrape.SearchRepository;
import com.paki.persistence.scrape.TopAdConditionRepository;
import com.paki.scrape.entities.Search;
import com.paki.scrape.entities.SearchProfile;
import com.paki.scrape.synchronization.GlobalLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    private SearchRepository searchRepository;
    private TopAdConditionRepository topAdRepository;
    private GlobalLock globalLock;
    private List<RealtyRepository> realtyRepositories;

    @Autowired
    public SearchProfileService(CriteriaService criteriaService, ApartmentRepository apartmentRepository, HouseRepository houseRepository, LandRepository landRepository, RealtySearchRelationRepository relationRepository, RealtyPriceChangeRepository priceChangeRepository, SearchProfileRepository profileRepository, SearchRepository searchRepository, TopAdConditionRepository topAdRepository, GlobalLock globalLock) {
        this.criteriaService = criteriaService;
        this.apartmentRepository = apartmentRepository;
        this.houseRepository = houseRepository;
        this.landRepository = landRepository;
        this.relationRepository = relationRepository;
        this.priceChangeRepository = priceChangeRepository;
        this.profileRepository = profileRepository;
        this.searchRepository = searchRepository;
        this.topAdRepository = topAdRepository;
        this.globalLock = globalLock;
        this.realtyRepositories = Arrays.asList(apartmentRepository, houseRepository, landRepository);
    }

    public List<SearchProfile> findAll() {
        return profileRepository.findAll();
    }

    public List<String> getProfilesNames() {
        return profileRepository.findAll().stream().map(SearchProfile::getName).collect(Collectors.toList());
    }

    public Optional<SearchProfile> findProfileById(Long id) {
        return profileRepository.findById(id);
    }

    public Optional<SearchProfile> findProfileByName(String name) {
        List<SearchProfile> profiles = profileRepository.findByName(name);
        if (profiles.isEmpty())
            return Optional.empty();
        else
            return Optional.of(profiles.get(0));
    }

    public SearchProfile createSearchProfile(SearchProfile profile) {
        Optional<SearchProfile> dbProfile = findProfileByName(profile.getName());
        if (dbProfile.isPresent())
            throw new BusinessException("Profil sa imenom: " + profile.getName() + " vec postoji!");

        createBidirectionalLinks(profile);
        criteriaService.normalizeLocationCriteria(profile.getSearch().getCriteria());
        if (profile.getSearch().getRealtyType() == null)
            profile.getSearch().setRealtyType(criteriaService.inferRealtyType(profile.getSearch().getCriteria()));
        return profileRepository.save(profile);
    }

    @Transactional
    public void updateSearchProfile(Long id, SearchProfile profile) {
        if (!globalLock.tryLock())
            throw new BusinessException("Izmena profila nije moguća tokom trajanja skeniranja oglasa. Pokušajte kasnije.");
        try {
            Optional<SearchProfile> dbProfile = profileRepository.findById(id);
            if (!dbProfile.isPresent())
                throw new BusinessException("Ažuriranje profila neuspelo. Profil ne postoji u sistemu.");

            if (!isEqualSearch(dbProfile.get(), profile)) {
                deleteRealties(dbProfile.get());
                deleteOldSearch(dbProfile.get());
            } else {
                profile.setSearch(dbProfile.get().getSearch());
            }
            if (!isEqualTopAdConditions(dbProfile.get(), profile)) {
                deleteOldTopAdConditions(dbProfile.get());
            } else {
                profile.setTopAdConditions(dbProfile.get().getTopAdConditions());
            }
            profile.setId(id);
            createBidirectionalLinks(profile);
            criteriaService.normalizeLocationCriteria(profile.getSearch().getCriteria());
            profileRepository.save(profile);
        } finally {
            globalLock.unlock();
        }
    }

    private void deleteOldSearch(SearchProfile profile) {
        searchRepository.deleteById(profile.getSearch().getId());
    }

    private void deleteOldTopAdConditions(SearchProfile profile) {
        topAdRepository.deleteBySearchProfileId(profile.getId());
    }

    @Transactional
    public void deleteSearchProfile(Long id) {
        if (!globalLock.tryLock())
            throw new BusinessException("Brisanje profila nije moguće tokom trajanja skeniranja oglasa. Pokušajte kasnije.");
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
     *
     * @param profile
     */
    private void deleteRealties(SearchProfile profile) {
        // delete relations
        relationRepository.deleteBySearchId(profile.getSearch().getId());
        Set<Long> realtiesIds = hangingRealtiesIds();
        // delete price changes
        priceChangeRepository.deleteByRealtyIds(realtiesIds);
        // delete hanging realties
        realtyRepositories.forEach(repo -> repo.deleteByIdIn(realtiesIds));
    }

    private Set<Long> hangingRealtiesIds() {
        Set<Long> ids = new HashSet<>();
        for (RealtyRepository repo : realtyRepositories)
            ids.addAll(repo.findIdsOfHangingRealties());
        return ids;
    }

    private boolean isEqualSearch(SearchProfile p1, SearchProfile p2) {
        Search s1 = p1.getSearch();
        Search s2 = p2.getSearch();

        if (s1.getRealtyType() != s2.getRealtyType()) {
            return false;
        }
        if (s1.getCriteria() == null) {
            return s2.getCriteria() == null;
        } else if (s2.getCriteria() == null) {
            return false;
        }
        if (s1.getCriteria().size() != s2.getCriteria().size()) {
            return false;
        } else {
            return s1.getCriteria().containsAll(s2.getCriteria());
        }
    }

    private boolean isEqualTopAdConditions(SearchProfile p1, SearchProfile p2) {
        if (p1.getTopAdConditions() == null) {
            return p2.getTopAdConditions() == null;
        } else if (p2.getTopAdConditions() == null) {
            return false;
        }
        if (p1.getTopAdConditions().size() != p2.getTopAdConditions().size()) {
            return false;
        } else {
            return p1.getTopAdConditions().containsAll(p2.getTopAdConditions());
        }
    }

    private void createBidirectionalLinks(SearchProfile searchProfile) {
        searchProfile.getSearch().setSearchProfile(searchProfile);
        searchProfile.getSearch().getCriteria().forEach(c -> c.setSearch(searchProfile.getSearch()));
        searchProfile.getTopAdConditions().forEach(c -> c.setSearchProfile(searchProfile));
    }
}
