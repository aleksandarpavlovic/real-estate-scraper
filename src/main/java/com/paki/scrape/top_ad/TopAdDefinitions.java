package com.paki.scrape.top_ad;

import java.util.Arrays;
import java.util.List;

public class TopAdDefinitions {
    private static TopAdDefinition newAd;
    private static TopAdDefinition priceDrop;
    private static TopAdDefinition priceInTopNPercent;
    private static TopAdDefinition pricePerM2InTopNPercent;
    private static TopAdDefinition pricePerAreInTopNPercent;

    public static List<TopAdDefinition> apartmentTopAdDefinitions;
    public static List<TopAdDefinition> houseTopAdDefinitions;
    public static List<TopAdDefinition> landTopAdDefinitions;

    static {
        newAd = new TopAdDefinition(TopAdName.NEW_AD, "Nov oglas");
        priceDrop = new TopAdDefinition(TopAdName.PRICE_DROP, "Cena oglasa snižena");
        priceInTopNPercent = new TopAdDefinition(TopAdName.PRICE_IN_TOP_N_PERCENT, "Cena novog oglasa u prvih ?procenat? %");
        pricePerM2InTopNPercent = new TopAdDefinition(TopAdName.PRICE_PER_M2_IN_TOP_N_PERCENT, "Cena/m2 novog oglasa u prvih ?procenat? %");
        pricePerAreInTopNPercent = new TopAdDefinition(TopAdName.PRICE_PER_ARE_IN_TOP_N_PERCENT, "Cena/ar novog oglasa u prvih ?procenat? %");

        apartmentTopAdDefinitions = Arrays.asList(newAd, priceDrop, priceInTopNPercent, pricePerM2InTopNPercent);
        houseTopAdDefinitions = Arrays.asList(newAd, priceDrop, priceInTopNPercent, pricePerM2InTopNPercent);
        landTopAdDefinitions = Arrays.asList(newAd, priceDrop, priceInTopNPercent, pricePerAreInTopNPercent);
    }
}
