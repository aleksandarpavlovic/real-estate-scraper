package com.paki.scrape.topad;

import com.paki.realties.enums.RealtyType;

import java.util.*;

public class TopAdDefinitions {
    public static TopAdDefinition newAd;
    public static TopAdDefinition priceDrop;
    public static TopAdDefinition priceInTopNPercent;
    public static TopAdDefinition pricePerM2InTopNPercent;
    public static TopAdDefinition pricePerAreInTopNPercent;

    private static List<TopAdDefinition> apartmentTopAdDefinitions;
    private static List<TopAdDefinition> houseTopAdDefinitions;
    private static List<TopAdDefinition> landTopAdDefinitions;

    private static Map<RealtyType, List<TopAdDefinition>> definitionsMap;
    private static Map<TopAdName, TopAdDefinition> topAdNameDefinitionMap;

    static {
        newAd = new TopAdDefinition(TopAdName.NEW_AD, TopAdType.PLAIN, "Nov oglas");
        priceDrop = new TopAdDefinition(TopAdName.PRICE_DROP, TopAdType.PLAIN, "Cena oglasa snižena");
        priceInTopNPercent = new TopAdDefinition(TopAdName.PRICE_IN_TOP_N_PERCENT, TopAdType.PARAMETERIZED, "Cena novog oglasa u prvih ?procenat? %");
        pricePerM2InTopNPercent = new TopAdDefinition(TopAdName.PRICE_PER_M2_IN_TOP_N_PERCENT, TopAdType.PARAMETERIZED, "Cena/m2 novog oglasa u prvih ?procenat? %");
        pricePerAreInTopNPercent = new TopAdDefinition(TopAdName.PRICE_PER_ARE_IN_TOP_N_PERCENT, TopAdType.PARAMETERIZED, "Cena/ar novog oglasa u prvih ?procenat? %");

        apartmentTopAdDefinitions = Arrays.asList(newAd, priceDrop, priceInTopNPercent, pricePerM2InTopNPercent);
        houseTopAdDefinitions = Arrays.asList(newAd, priceDrop, priceInTopNPercent, pricePerM2InTopNPercent);
        landTopAdDefinitions = Arrays.asList(newAd, priceDrop, priceInTopNPercent, pricePerAreInTopNPercent);

        definitionsMap = new HashMap<>();
        definitionsMap.put(RealtyType.APARTMENT, apartmentTopAdDefinitions);
        definitionsMap.put(RealtyType.HOUSE, houseTopAdDefinitions);
        definitionsMap.put(RealtyType.LAND, landTopAdDefinitions);

        topAdNameDefinitionMap = new HashMap<>();
        topAdNameDefinitionMap.put(TopAdName.NEW_AD, newAd);
        topAdNameDefinitionMap.put(TopAdName.PRICE_DROP, priceDrop);
        topAdNameDefinitionMap.put(TopAdName.PRICE_IN_TOP_N_PERCENT, priceInTopNPercent);
        topAdNameDefinitionMap.put(TopAdName.PRICE_PER_M2_IN_TOP_N_PERCENT, pricePerM2InTopNPercent);
        topAdNameDefinitionMap.put(TopAdName.PRICE_PER_ARE_IN_TOP_N_PERCENT, pricePerAreInTopNPercent);
    }

    public static List<TopAdDefinition> getDefinitions(RealtyType realtyType) {
        return definitionsMap.getOrDefault(realtyType, Collections.emptyList());
    }

    public static TopAdDefinition getDefinitionByTopAdName(TopAdName name) {
        return topAdNameDefinitionMap.get(name);
    }
}
