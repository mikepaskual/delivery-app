package com.mikepaskual.delivery.shared.init;

import java.security.SecureRandom;

public class PlateGenerator {

    private static final String LETTERS = "BCDFGHJKLMNPQRSTVWXYZ";
    private static final SecureRandom random = new SecureRandom();

    public static String generatePlate() {
        int numbers = random.nextInt(9000) + 1000;StringBuilder letras = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char letter = LETTERS.charAt(random.nextInt(LETTERS.length()));
            letras.append(letter);
        }

        return numbers + " " + letras;
    }
}
