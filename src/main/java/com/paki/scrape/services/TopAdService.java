package com.paki.scrape.services;

import com.paki.persistence.realties.ApartmentRepository;
import com.paki.persistence.realties.HouseRepository;
import com.paki.persistence.realties.LandRepository;
import com.paki.persistence.realties.RealtyRepository;
import com.paki.realties.Realty;
import com.paki.realties.enums.AreaMeasurementUnit;
import com.paki.realties.enums.RealtyType;
import com.paki.realties.util.UnitConversionUtil;
import com.paki.scrape.entities.ScrapeInfo;
import com.paki.scrape.entities.Search;
import com.paki.scrape.entities.SearchProfile;
import com.paki.scrape.topad.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TopAdService {

    private ApartmentRepository apartmentRepository;
    private HouseRepository houseRepository;
    private LandRepository landRepository;
    private CriteriaService criteriaService;

    private static final Comparator<Realty> PRICE_PER_AREA_COMPARATOR;

    static {
        PRICE_PER_AREA_COMPARATOR = new Comparator<Realty>() {
            @Override
            public int compare(Realty r1, Realty r2) {
                BigDecimal r1AreaM2 = UnitConversionUtil.convertArea(r1.getSurfaceArea(), r1.getAreaMeasurementUnit(), AreaMeasurementUnit.SQUARE_METER);
                BigDecimal r2AreaM2 = UnitConversionUtil.convertArea(r2.getSurfaceArea(), r2.getAreaMeasurementUnit(), AreaMeasurementUnit.SQUARE_METER);
                if (r1AreaM2 == BigDecimal.ZERO && r2AreaM2 == BigDecimal.ZERO)
                    return r1.getPrice().compareTo(r2.getPrice());
                else if (r1AreaM2 == BigDecimal.ZERO)
                    return 1;
                else if (r2AreaM2 == BigDecimal.ZERO)
                    return -1;
                else {
                    BigDecimal r1PricePerM2 = r1.getPrice().divide(r1AreaM2, 2, RoundingMode.CEILING);
                    BigDecimal r2PricePerM2 = r2.getPrice().divide(r2AreaM2, 2, RoundingMode.CEILING);
                    return r1PricePerM2.compareTo(r2PricePerM2);
                }
            }
        };
    }

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

    public Map<String, List<? extends Realty>> getTopAds(ScrapeInfo scrapeInfo, SearchProfile searchProfile) {
        Set<TopAdCondition> conditions = searchProfile.getTopAdConditions();
        if (conditions == null || conditions.isEmpty())
            return new HashMap<>();

        Map<String, List<? extends Realty>> topAds = searchProfile.getTopAdConditions().stream().collect(Collectors.toMap(this::conditionDisplay, condition -> getTopAds(scrapeInfo, searchProfile, condition)));
        return filteredTopAds(topAds);
    }

    private Map<String, List<? extends Realty>> filteredTopAds(Map<String, List<? extends Realty>> topAds) {
        Map<String, List<? extends Realty>> filteredTopAds = new HashMap<>();
        for (Map.Entry<String, List<? extends Realty>> entry: topAds.entrySet()) {
            if (entry.getValue() == null || entry.getValue().isEmpty())
                continue;
            else
                filteredTopAds.put(entry.getKey(), entry.getValue());
        }
        return filteredTopAds;
    }

    private String conditionDisplay(TopAdCondition condition) {
        TopAdDefinition definition = TopAdDefinitions.getDefinitionByTopAdName(condition.getTopAdName());
        if (definition == null)
            return "";
        if (condition instanceof TopAdParameterizedCondition) {
            TopAdParameterizedCondition parameterizedCondition = (TopAdParameterizedCondition) condition;
            return definition.getText().replaceFirst("\\?.+?\\?", parameterizedCondition.getParameter());
        } else {
            return definition.getText();
        }
    }

    private List<? extends Realty> getTopAds(ScrapeInfo scrapeInfo, SearchProfile searchProfile, TopAdCondition topAdCondition) {
        TopAdName topAdName = topAdCondition.getTopAdName();
        switch (topAdName) {
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

        List<Realty> realties = realtyRepository.findByPriceLessThanEqual(thresholdPrice);
        return realties.stream().filter(realty -> realty.getScrapeRunNumber() == scrapeInfo.getLastRunNumber()).collect(Collectors.toList());
    }

    private List<? extends Realty> fetchLatestWithTopPricePerArea(ScrapeInfo scrapeInfo, SearchProfile searchProfile, String conditionParameter) {
        RealtyRepository realtyRepository = inferRealtyRepository(searchProfile.getSearch());

        Integer percent = Integer.valueOf(conditionParameter);
        List<Realty> realties = realtyRepository.findBySearchId(searchProfile.getSearch().getId());
        realties.sort(PRICE_PER_AREA_COMPARATOR);

        int thresholdIndex = Math.max(1, (int)((double)realties.size() * percent) / 100);
        return realties.stream().limit(thresholdIndex).filter(realty -> realty.getScrapeRunNumber() == scrapeInfo.getLastRunNumber()).collect(Collectors.toList());
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
