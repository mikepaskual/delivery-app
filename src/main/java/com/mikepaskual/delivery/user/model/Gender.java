package com.mikepaskual.delivery.user.model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Gender {
    MALE, FEMALE;

    public static Set<String> getGendersAsNames() {
        return Arrays.stream(Gender.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
