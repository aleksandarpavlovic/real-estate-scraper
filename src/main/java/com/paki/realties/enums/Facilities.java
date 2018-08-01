package com.paki.realties.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Facilities {
    TERRASSE,
    LOGGIA,
    BALCONY,
    FRENCH_BALCONY,
    GARAGE,
    PARKING;

    private static final Map<String,Facilities> ENUM_MAP;

    static {
        Map<String,Facilities> map = new ConcurrentHashMap<>();
        for (Facilities instance : Facilities.values()) {
            map.put(instance.name(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static Facilities get (String name) {
        return ENUM_MAP.get(name);
    }

}
