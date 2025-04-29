package com.mikepaskual.delivery.truck.model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum StatusTruck {
    ACTIVE, INACTIVE;

    public static Set<String> getStatusAsNames() {
        return Arrays.stream(StatusTruck.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
