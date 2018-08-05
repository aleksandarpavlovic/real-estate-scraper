package com.paki.scrape.topad;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum TopAdName {
    NEW_AD,
    PRICE_DROP,
    PRICE_IN_TOP_N_PERCENT,
    PRICE_PER_M2_IN_TOP_N_PERCENT,
    PRICE_PER_ARE_IN_TOP_N_PERCENT;

    private static final Map<String,TopAdName> ENUM_MAP;

    static {
        Map<String,TopAdName> map = new ConcurrentHashMap<>();
        for (TopAdName instance : TopAdName.values()) {
            map.put(instance.name(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static TopAdName get (String name) {
        return ENUM_MAP.get(name);
    }
}
