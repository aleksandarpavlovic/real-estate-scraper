package com.paki.realties.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum AdvertiserType {
    AGENCY,
    OWNER,
    INVESTOR;

    private static final Map<String,AdvertiserType> ENUM_MAP;

    static {
        Map<String,AdvertiserType> map = new ConcurrentHashMap<>();
        for (AdvertiserType instance : AdvertiserType.values()) {
            map.put(instance.name(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static AdvertiserType get (String name) {
        return ENUM_MAP.get(name);
    }
}
