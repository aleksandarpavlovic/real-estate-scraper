package com.paki.realties.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum RegistrationType {
    NA,
    REGISTERED,
    NOT_REGISTERED;

    private static final Map<String,RegistrationType> ENUM_MAP;

    static {
        Map<String,RegistrationType> map = new ConcurrentHashMap<>();
        for (RegistrationType instance : RegistrationType.values()) {
            map.put(instance.name(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static RegistrationType get (String name) {
        return ENUM_MAP.get(name);
    }
}
