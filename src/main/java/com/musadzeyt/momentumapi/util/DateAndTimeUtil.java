package com.musadzeyt.momentumapi.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

/**
 * Utility class for formatting ISO date-time strings into various predefined human-readable formats.
 */
public class DateAndTimeUtil {
    /**
     * Supported formatting keys mapped to their corresponding {@link java.time.format.DateTimeFormatter} patterns.
     * <ul>
     *     <li>{@code "full"} - "d MMMM uuuu HH:mm" (e.g., "1 May 2025 17:45")</li>
     *     <li>{@code "date_only"} - "d MMMM uuuu" (e.g., "1 May 2025")</li>
     *     <li>{@code "time_only"} - "HH:mm" (e.g., "17:45")</li>
     *     <li>{@code "iso"} - "uuuu-MM-dd'T'HH:mm:ss" (e.g., "2025-05-01T17:45:00")</li>
     *     <li>{@code "short"} - "dd/MM/uuuu HH:mm" (e.g., "01/05/2025 17:45")</li>
     * </ul>
     */
    private static final Map<String, String> SUPPORTED_FORMATS = Map.of(
            "full", "d MMMM uuuu HH:mm",
            "date_only", "d MMMM uuuu",
            "time_only", "HH:mm",
            "iso", "uuuu-MM-dd'T'HH:mm:ss",
            "short", "dd/MM/uuuu HH:mm"
    );

    /**
     * Formats an ISO 8601 date-time string using a predefined format key.
     *
     * @param dateStr   the input date-time string (e.g., "2025-05-01T17:45:00")
     * @param formatKey the key representing the desired format; must be one of the keys defined in {@link #SUPPORTED_FORMATS}
     * @return a lower-cased formatted date string
     * @throws IllegalArgumentException if the format key is not supported or the date string is invalid
     */
    public static String formatDateIsoString(String dateStr, String formatKey) {
        String pattern = SUPPORTED_FORMATS.get(formatKey);
        if (pattern == null) {
            throw new IllegalArgumentException("Unsupported format key: " + formatKey);
        }

        LocalDateTime date = LocalDateTime.parse(dateStr);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
        return date.format(fmt).toLowerCase(Locale.ENGLISH);
    }
}
