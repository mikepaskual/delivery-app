package com.mikepaskual.delivery.shared.init;

import java.security.SecureRandom;

public class LicenseNumberGenerator {

    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generateLicenseNumber() {
        StringBuilder license = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            license.append(ALPHANUM.charAt(random.nextInt(26)));
        }

        for (int i = 0; i < 10; i++) {
            license.append(random.nextInt(10));
        }

        return license.toString();
    }

}
