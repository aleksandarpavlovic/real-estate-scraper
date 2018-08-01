package com.paki.realties.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum AdSource {
    HALO_OGLASI,
    NEKRETNINE_RS;

    private static final Map<String,AdSource> ENUM_MAP;

    static {
        Map<String,AdSource> map = new ConcurrentHashMap<>();
        for (AdSource instance : AdSource.values()) {
            map.put(instance.name(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static AdSource get (String name) {
        return ENUM_MAP.get(name);
    }
}
