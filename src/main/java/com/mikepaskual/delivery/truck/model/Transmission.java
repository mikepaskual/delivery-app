package com.mikepaskual.delivery.truck.model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Transmission {
    AUTOMATIC, MANUAL;

    public static Set<String> getTransmissionsAsNames() {
        return Arrays.stream(Transmission.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
