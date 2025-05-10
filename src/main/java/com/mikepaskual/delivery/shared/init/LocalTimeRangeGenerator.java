package com.mikepaskual.delivery.shared.init;

import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public class LocalTimeRangeGenerator {

    public static LocalTime[] generateLocalTimeRange() {
        int init = ThreadLocalRandom.current().nextInt(0, 18);
        int end = ThreadLocalRandom.current().nextInt(0, 60);

        if (init == 17 && end > 30) {
            end = 30;
        }
        LocalTime initLocalTime = LocalTime.of(init, end);

        int minDuration = 30;
        int maxDuration = 6 * 60;
        int availableMinutes = 24 * 60 - (initLocalTime.getHour() * 60 + initLocalTime.getMinute());

        int duracionMaxAjustada = Math.min(maxDuration, availableMinutes);
        int duracion = ThreadLocalRandom.current().nextInt(minDuration, duracionMaxAjustada + 1);

        LocalTime fin = initLocalTime.plusMinutes(duracion);
        return new LocalTime[] { initLocalTime, fin };

    }
}
