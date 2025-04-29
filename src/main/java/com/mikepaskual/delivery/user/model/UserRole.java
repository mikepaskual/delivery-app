package com.mikepaskual.delivery.user.model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum UserRole {
    ADMIN(false),
    CUSTOMER(true),
    DRIVER(true),
    MODERATOR(false),
    USER(false);

    private final boolean isPublicRole;

    UserRole(boolean isPublicRole) {
        this.isPublicRole = isPublicRole;
    }

    public static Set<String> getPublicRolesAsNames() {
        return Arrays.stream(UserRole.values())
                .filter(UserRole::isPublicRole)
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    public boolean isPublicRole() {
        return isPublicRole;
    }
}
