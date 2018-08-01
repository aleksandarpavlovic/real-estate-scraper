package com.paki.realties.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum HeatingType {
    CENTRAL,
    OWN_CENTRAL,
    ELECTRIC,
    GAS;

    private static final Map<String,HeatingType> ENUM_MAP;

    static {
        Map<String,HeatingType> map = new ConcurrentHashMap<>();
        for (HeatingType instance : HeatingType.values()) {
            map.put(instance.name(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static HeatingType get (String name) {
        return ENUM_MAP.get(name);
    }
}
