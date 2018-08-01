package com.paki.realties.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum RealtyType {
    APARTMENT,
    HOUSE,
    LAND;

    private static final Map<String,RealtyType> ENUM_MAP;

    static {
        Map<String,RealtyType> map = new ConcurrentHashMap<>();
        for (RealtyType instance : RealtyType.values()) {
            map.put(instance.name(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static RealtyType get (String name) {
        return ENUM_MAP.get(name);
    }
}
