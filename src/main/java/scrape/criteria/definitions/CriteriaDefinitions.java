package scrape.criteria.definitions;

import realties.enums.*;

public class CriteriaDefinitions {

    public static final SingleSelectCriteriaDefinition adTypeCD;
    public static final String AD_TYPE = "tip oglasa";

    public static final MultiSelectCriteriaDefinition advertiserTypeCD;
    public static final String ADVERTISER = "oglasivac";

    public static final SingleSelectCriteriaDefinition realtyTypeCD;
    public static final String REALTY_TYPE = "tip nekretnine";

    public static final MultiSelectCriteriaDefinition buildTypeCD;
    public static final String BUILD_TYPE = "gradnja";

    public static final MultiSelectCriteriaDefinition heatingTypeCD;
    public static final String HEATING_TYPE = "grejanje";

    public static final MultiSelectCriteriaDefinition registeredCD;
    public static final String REGISTERED = "registered";

    public static final MultiSelectCriteriaDefinition apartmentTypeCD;
    public static final String APARTMENT_TYPE = "tip stana";

    public static final MultiSelectCriteriaDefinition facilitiesCD;
    public static final String FACILITIES = "prateci objekti";

    public static final RangeSingleSelectCriteriaDefinition roomCountCD;
    public static final String ROOM_COUNT = "broj soba";

    public static final RangeCriteriaDefinition priceCD;
    public static final String PRICE = "cena";

    public static final RangeCriteriaDefinition areaCD;
    public static final String SURFACE_AREA = "kvadratura";

    public static final RangeSingleSelectCriteriaDefinition floorCD;
    public static final String FLOOR = "spratnost";
    public static final Integer SUBTERRAIN = -4;
    public static final Integer LOW_GROUND_FLOOR = -3;
    public static final Integer GROUND_FLOOR = -2;
    public static final Integer HIGH_GROUND_FLOOR = -1;

    static {
        adTypeCD = new SingleSelectCriteriaDefinition(AD_TYPE, AdType.values());
        advertiserTypeCD = new MultiSelectCriteriaDefinition(ADVERTISER, AdvertiserType.values());
        realtyTypeCD = new SingleSelectCriteriaDefinition(REALTY_TYPE, RealtyType.values());
        buildTypeCD = new MultiSelectCriteriaDefinition(BUILD_TYPE, BuildType.values());
        heatingTypeCD = new MultiSelectCriteriaDefinition(HEATING_TYPE, HeatingType.values());
        registeredCD = new MultiSelectCriteriaDefinition(REGISTERED, RegistrationType.values());
        apartmentTypeCD = new MultiSelectCriteriaDefinition(APARTMENT_TYPE, ApartmentType.values());
        facilitiesCD = new MultiSelectCriteriaDefinition(FACILITIES, Facilities.values());
        roomCountCD = new RangeSingleSelectCriteriaDefinition(ROOM_COUNT, RoomCount.values());
        priceCD = new RangeCriteriaDefinition(PRICE);
        areaCD = new RangeCriteriaDefinition(SURFACE_AREA);
        floorCD = new RangeSingleSelectCriteriaDefinition(FLOOR, new Integer[]{SUBTERRAIN, LOW_GROUND_FLOOR, GROUND_FLOOR, HIGH_GROUND_FLOOR, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30});
    }
}
