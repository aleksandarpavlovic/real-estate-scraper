package com.paki.realties.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum AdType {
    SELL,
    RENT;

    private static final Map<String,AdType> ENUM_MAP;

    static {
        Map<String,AdType> map = new ConcurrentHashMap<>();
        for (AdType instance : AdType.values()) {
            map.put(instance.name(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static AdType get (String name) {
        return ENUM_MAP.get(name);
    }
}
