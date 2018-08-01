package com.paki.realties.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum AreaMeasurementUnit {
    SQUARE_METER,
    ARE,
    HECTARE;

    private static final Map<String,AreaMeasurementUnit> ENUM_MAP;

    static {
        Map<String,AreaMeasurementUnit> map = new ConcurrentHashMap<>();
        for (AreaMeasurementUnit instance : AreaMeasurementUnit.values()) {
            map.put(instance.name(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static AreaMeasurementUnit get (String name) {
        return ENUM_MAP.get(name);
    }
}
