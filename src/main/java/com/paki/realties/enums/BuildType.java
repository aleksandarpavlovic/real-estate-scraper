package com.paki.realties.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum BuildType {
    OLD_BUILD,
    NEW_BUILD,
    UNDER_CONSTRUCTION;

    private static final Map<String,BuildType> ENUM_MAP;

    static {
        Map<String,BuildType> map = new ConcurrentHashMap<>();
        for (BuildType instance : BuildType.values()) {
            map.put(instance.name(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static BuildType get (String name) {
        return ENUM_MAP.get(name);
    }
}
