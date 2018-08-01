package com.paki.realties.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum RoomCount {
    RC_0,
    RC_0_5,
    RC_1_0,
    RC_1_5,
    RC_2_0,
    RC_2_5,
    RC_3_0,
    RC_3_5,
    RC_4_0,
    RC_4_5,
    RC_5_0,
    RC_5_p;

    private static final Map<String,RoomCount> ENUM_MAP;

    static {
        Map<String,RoomCount> map = new ConcurrentHashMap<>();
        for (RoomCount instance : RoomCount.values()) {
            map.put(instance.name(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static RoomCount get (String name) {
        return ENUM_MAP.get(name);
    }
}
