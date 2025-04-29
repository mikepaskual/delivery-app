package com.mikepaskual.delivery.truck.model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum FuelType {
    GASOLINE, DIESEL, ELECTRIC, HYBRID;

    public static Set<String> getFuelTypesAsNames() {
        return Arrays.stream(FuelType.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
