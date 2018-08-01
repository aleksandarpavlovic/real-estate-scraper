package com.paki.scrape.top_ad;

import com.paki.realties.Realty;
import com.paki.scrape.SearchProfile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TopAdService {
    public Optional<Map<TopAdCondition, List<Realty>>> getTopAds(SearchProfile searchProfile) {
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

        Map<TopAdCondition, List<Realty>> resultMap = searchProfile.getTopAdConditions().stream().collect(Collectors.toMap(condition -> condition, condition -> getTopAds(searchProfile.getName(), condition)));
        if (resultMap.isEmpty())
            return Optional.empty();
        else
            return Optional.of(resultMap);
    }

    public List<Realty> getTopAds(String searchProfileName, TopAdCondition topAdCondition) {
        TopAdDefinition definition = topAdCondition.getDefinition();
        switch (definition.getName()) {
            case NEW_AD:
                // fetch latest scraped ads
                return Collections.emptyList();
            case PRICE_DROP:
                // fetch ads whose prices have dropped in last scrape run
                return Collections.emptyList();
            case PRICE_IN_TOP_N_PERCENT:
                // fetch fresh ads that have price in top n percent
                return Collections.emptyList();
            case PRICE_PER_M2_IN_TOP_N_PERCENT:
            case PRICE_PER_ARE_IN_TOP_N_PERCENT:
                // fetch ads whose prices per area unit have dropped in last scrape run
                return Collections.emptyList();
            default:
                return Collections.emptyList();
        }
    }
}
