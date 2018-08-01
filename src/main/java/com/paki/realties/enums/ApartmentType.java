package com.paki.realties.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ApartmentType {
    WITH_SALON,
    DUPLEX,
    PENTHOUSE,
    LOFT,
    WITH_YARD;

    private static final Map<String,ApartmentType> ENUM_MAP;

    static {
        Map<String,ApartmentType> map = new ConcurrentHashMap<>();
        for (ApartmentType instance : ApartmentType.values()) {
            map.put(instance.name(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static ApartmentType get (String name) {
        return ENUM_MAP.get(name);
    }
}
