package com.paki.scrape.services;

import com.paki.persistence.ApartmentRepository;
import com.paki.persistence.HouseRepository;
import com.paki.persistence.LandRepository;
import com.paki.persistence.RealtyRepository;
import com.paki.realties.Realty;
import com.paki.realties.enums.RealtyType;
import com.paki.scrape.entities.ScrapeInfo;
import com.paki.scrape.entities.Search;
import com.paki.scrape.entities.SearchProfile;
import com.paki.scrape.topad.TopAdCondition;
import com.paki.scrape.topad.TopAdDefinition;
import com.paki.scrape.topad.TopAdParameterizedCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopAdService {

    private ApartmentRepository apartmentRepository;
    private HouseRepository houseRepository;
    private LandRepository landRepository;
    private CriteriaService criteriaService;

    @Autowired
    public TopAdService(ApartmentRepository apartmentRepository
            , HouseRepository houseRepository
            , LandRepository landRepository
            , CriteriaService criteriaService) {
        this.apartmentRepository = apartmentRepository;
        this.houseRepository = houseRepository;
        this.landRepository = landRepository;
        this.criteriaService = criteriaService;
    }

    public Optional<Map<TopAdCondition, List<? extends Realty>>> getTopAds(ScrapeInfo scrapeInfo, SearchProfile searchProfile) {
        List<TopAdCondition> conditions = searchProfile.getTopAdConditions();
        if (conditions == null || conditions.isEmpty())
            return Optional.empty();

//        Map<TopAdCondition, List<Realty>> resultMap = null;
//        for (TopAdCondition topAdCondition: searchProfile.getTopAdConditions()) {
//            List<Realty> realties = getTopAds(searchProfile.getName(), topAdCondition);
//            if (!realties.isEmpty()) {
//                if (resultMap == null)
//                    resultMap = new HashMap<>();
//                resultMap.put(topAdCondition, realties);
//            }
//        }

        Map<TopAdCondition, List<? extends Realty>> resultMap = searchProfile.getTopAdConditions().stream().collect(Collectors.toMap(condition -> condition, condition -> getTopAds(scrapeInfo, searchProfile, condition)));
        if (resultMap.isEmpty())
            return Optional.empty();
        else
            return Optional.of(resultMap);
    }

    public List<? extends Realty> getTopAds(ScrapeInfo scrapeInfo, SearchProfile searchProfile, TopAdCondition topAdCondition) {
        TopAdDefinition definition = topAdCondition.getDefinition();
        switch (definition.getName()) {
            case NEW_AD:
                return fetchLatest(scrapeInfo, searchProfile);
            case PRICE_DROP:
                return fetchDiscounted(scrapeInfo, searchProfile);
            case PRICE_IN_TOP_N_PERCENT:
                return fetchLatestWithTopPrice(scrapeInfo, searchProfile, ((TopAdParameterizedCondition) topAdCondition).getParameter());
            case PRICE_PER_M2_IN_TOP_N_PERCENT:
            case PRICE_PER_ARE_IN_TOP_N_PERCENT:
                return fetchLatestWithTopPricePerArea(scrapeInfo, searchProfile, ((TopAdParameterizedCondition) topAdCondition).getParameter());
            default:
                return Collections.emptyList();
        }
    }

    private List<? extends Realty> fetchLatest(ScrapeInfo scrapeInfo, SearchProfile searchProfile) {
        RealtyRepository realtyRepository = inferRealtyRepository(searchProfile.getSearch());
        if (realtyRepository != null)
            return realtyRepository.findByScrapeRunNumberAndSearchId(scrapeInfo.getLastRunNumber(), searchProfile.getSearch().getId());
        return Collections.emptyList();
    }

    private List<? extends Realty> fetchDiscounted(ScrapeInfo scrapeInfo, SearchProfile searchProfile) {
        RealtyRepository realtyRepository = inferRealtyRepository(searchProfile.getSearch());
        if (realtyRepository != null)
            return realtyRepository.findDiscountedByScrapeRunNumberAndSearchId(scrapeInfo.getLastRunNumber(), searchProfile.getSearch().getId());
        return Collections.emptyList();
    }

    private List<? extends Realty> fetchLatestWithTopPrice(ScrapeInfo scrapeInfo, SearchProfile searchProfile, String conditionParameter) {
        // TODO handle NumberFormatException
        RealtyRepository realtyRepository = inferRealtyRepository(searchProfile.getSearch());

        Integer percent = Integer.valueOf(conditionParameter);
        List<BigDecimal> sortedPrices = realtyRepository.findAllPrices();
        if (sortedPrices.isEmpty())
            return Collections.emptyList();

        int thresholdPriceIndex = (int)((double)sortedPrices.size() * percent) / 100;
        BigDecimal thresholdPrice = sortedPrices.get(thresholdPriceIndex);

        return realtyRepository.findByPriceLessThan(thresholdPrice);
    }

    private List<? extends Realty> fetchLatestWithTopPricePerArea(ScrapeInfo scrapeInfo, SearchProfile searchProfile, String conditionParameter) {
        return Collections.emptyList();
    }

    private RealtyRepository<? extends Realty> inferRealtyRepository(Search search) {
        RealtyRepository realtyRepository = inferRealtyRepository(search.getRealtyType());
        if (realtyRepository == null)
            realtyRepository = inferRealtyRepository(criteriaService.inferRealtyType(search.getCriteria()));
        return realtyRepository;
    }

    private RealtyRepository<? extends Realty> inferRealtyRepository(RealtyType realtyType) {
        switch (realtyType) {
            case APARTMENT:
                return apartmentRepository;
            case HOUSE:
                return houseRepository;
            case LAND:
                return landRepository;
            default:
                return null;
        }
    }
}
