package com.loo.tp.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class InstantUtils {
    private static final String PATTERN_FORMAT = "uuuu/MM/dd";

    public static String format(Instant instant) {
        if (instant == null) {
            return "Sin fecha";
        }
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());
        
        return formatter.format(instant);

    }
}
