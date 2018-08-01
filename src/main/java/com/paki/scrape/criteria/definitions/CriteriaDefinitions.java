package com.paki.scrape.criteria.definitions;

import com.google.gson.Gson;
import com.paki.realties.enums.*;
import com.paki.realties.locations.LocationsGenerator;

import java.util.Arrays;
import java.util.List;

public class CriteriaDefinitions {

    public static final SingleSelectCriteriaDefinition adTypeCD;
    public static final String AD_TYPE = "Ad type";

    public static final MultiSelectCriteriaDefinition advertiserTypeCD;
    public static final String ADVERTISER = "Advertiser";

    public static final SingleSelectCriteriaDefinition realtyTypeCD;
    public static final String REALTY_TYPE = "Realty type";

    public static final MultiSelectCriteriaDefinition buildTypeCD;
    public static final String BUILD_TYPE = "Build";

    public static final MultiSelectCriteriaDefinition heatingTypeCD;
    public static final String HEATING_TYPE = "Heating";

    public static final MultiSelectCriteriaDefinition registeredCD;
    public static final String REGISTRATION = "Registration";

    public static final MultiSelectCriteriaDefinition apartmentTypeCD;
    public static final String APARTMENT_TYPE = "Apartment type";

    public static final MultiSelectCriteriaDefinition facilitiesCD;
    public static final String FACILITIES = "Facilities";

    public static final RangeSingleSelectCriteriaDefinition roomCountCD;
    public static final String ROOM_COUNT = "Room count";

    public static final RangeCriteriaDefinition priceCD;
    public static final String PRICE = "Price";

    public static final RangeWithUnitCriteriaDefinition areaM2CD;
    public static final RangeWithUnitCriteriaDefinition areaAreCD;
    public static final String SURFACE_M2 = "Surface m2";
    public static final String SURFACE_ARE = "Surface are";

    public static final RangeWithUnitCriteriaDefinition pricePerM2CD;
    public static final RangeWithUnitCriteriaDefinition pricePerAreCD;
    public static final String PRICE_PER_M2 = "Price per m2";
    public static final String PRICE_PER_ARE = "Price per are";

    public static final RangeSingleSelectCriteriaDefinition floorCD;
    public static final String FLOOR = "Floor";
    public static final Integer SUBTERRAIN = -4;
    public static final Integer LOW_GROUND_FLOOR = -3;
    public static final Integer GROUND_FLOOR = -2;
    public static final Integer HIGH_GROUND_FLOOR = -1;

    public static final LocationCriteriaDefinition locationCD;
    public static final String LOCATION = "Location";

    public static final List<BaseCriteriaDefinition> apartmentCriteriaDefinitions;
    public static final List<BaseCriteriaDefinition> houseCriteriaDefinitions;
    public static final List<BaseCriteriaDefinition> landCriteriaDefinitions;

    static {
        adTypeCD = new SingleSelectCriteriaDefinition(AD_TYPE, AdType.values());
        advertiserTypeCD = new MultiSelectCriteriaDefinition(ADVERTISER, AdvertiserType.values());
        realtyTypeCD = new SingleSelectCriteriaDefinition(REALTY_TYPE, RealtyType.values());
        buildTypeCD = new MultiSelectCriteriaDefinition(BUILD_TYPE, BuildType.values());
        heatingTypeCD = new MultiSelectCriteriaDefinition(HEATING_TYPE, HeatingType.values());
        registeredCD = new MultiSelectCriteriaDefinition(REGISTRATION, RegistrationType.values());
        apartmentTypeCD = new MultiSelectCriteriaDefinition(APARTMENT_TYPE, ApartmentType.values());
        facilitiesCD = new MultiSelectCriteriaDefinition(FACILITIES, Facilities.values());
        roomCountCD = new RangeSingleSelectCriteriaDefinition(ROOM_COUNT, RoomCount.values());
        priceCD = new RangeCriteriaDefinition(PRICE);
        areaM2CD = new RangeWithUnitCriteriaDefinition(SURFACE_M2, AreaMeasurementUnit.SQUARE_METER);
        areaAreCD = new RangeWithUnitCriteriaDefinition(SURFACE_ARE, AreaMeasurementUnit.ARE);
        pricePerM2CD = new RangeWithUnitCriteriaDefinition(PRICE_PER_M2, AreaMeasurementUnit.SQUARE_METER);
        pricePerAreCD = new RangeWithUnitCriteriaDefinition(PRICE_PER_ARE, AreaMeasurementUnit.ARE);
        floorCD = new RangeSingleSelectCriteriaDefinition(FLOOR, new Integer[]{SUBTERRAIN, LOW_GROUND_FLOOR, GROUND_FLOOR, HIGH_GROUND_FLOOR, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30});
        locationCD = new LocationCriteriaDefinition(LOCATION, LocationsGenerator.getLocations());

        apartmentCriteriaDefinitions = Arrays.asList(realtyTypeCD, adTypeCD, advertiserTypeCD, registeredCD, priceCD, pricePerM2CD, areaM2CD, locationCD, roomCountCD, floorCD, buildTypeCD, heatingTypeCD, apartmentTypeCD, facilitiesCD);
        houseCriteriaDefinitions = Arrays.asList(realtyTypeCD, adTypeCD, advertiserTypeCD, registeredCD, priceCD, pricePerM2CD, areaM2CD, locationCD, roomCountCD, heatingTypeCD, facilitiesCD);
        landCriteriaDefinitions = Arrays.asList(realtyTypeCD, adTypeCD, advertiserTypeCD, registeredCD, priceCD, pricePerAreCD, areaAreCD, locationCD);
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        System.out.println(gson.toJson(adTypeCD));
        System.out.println(gson.toJson(advertiserTypeCD));
        System.out.println(gson.toJson(roomCountCD));
    }
}
