package org.example.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import org.example.utils.StringUtils;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DatetimeFormatterUtils {

    private DatetimeFormatterUtils() {
    }

    // List of common formats you may want to parse
    private static final List<DateTimeFormatter> COMMON_FORMATS = List.of(
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME,
            DateTimeFormatter.RFC_1123_DATE_TIME,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
    );


    /**
     * Tries parsing a date using multiple formats.
     */
    public static LocalDateTime parseFlexible(String input) {
        if (input == null || StringUtils.isBlank(input)) return null;

        for (DateTimeFormatter fmt : COMMON_FORMATS) {
            try {
                return LocalDateTime.parse(input, fmt);
            } catch (DateTimeParseException ignore) {
            }
            try {
                LocalDate date = LocalDate.parse(input, fmt);
                return date.atStartOfDay();
            } catch (Exception ignore) {
            }
        }

        throw new IllegalArgumentException("Unrecognized datetime format: " + input);
    }

    /**
     * Converts date/time between timezones.
     */
    public static String convertTimezone(
            String datetime,
            ZoneId from,
            ZoneId to,
            String outputPattern
    ) {
        LocalDateTime parsed = parseFlexible(datetime);
        ZonedDateTime zdt = parsed.atZone(from).withZoneSameInstant(to);
        return zdt.format(DateTimeFormatter.ofPattern(outputPattern));
    }

    /**
     * Returns "2 days ago", "5 minutes ago", "just now" style strings.
     */
    public static String toHumanReadableDiff(LocalDateTime target) {
        if (target == null) return null;

        Duration diff = Duration.between(target, LocalDateTime.now());
        long seconds = diff.getSeconds();

        if (seconds < 60) return "just now";
        if (seconds < 3600) return (seconds / 60) + " minutes ago";
        if (seconds < 86_400) return (seconds / 3600) + " hours ago";
        if (seconds < 2 * 86_400) return "yesterday";
        if (seconds < 30L * 86_400) return (seconds / 86_400) + " days ago";
        if (seconds < 365L * 86_400) return (seconds / (30L * 86_400)) + " months ago";

        return (seconds / (365L * 86_400)) + " years ago";
    }

    /**
     * Returns the start of week (Monday).
     */
    public static LocalDate getStartOfWeek(LocalDate input) {
        return input.with(DayOfWeek.MONDAY);
    }

    /**
     * Formats a timestamp into ISO string with milliseconds.
     */
    public static String formatIsoWithMillis(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
    }

    /**
     * Adds or subtracts time using a compact pattern like "1d", "-3h", "45m".
     */
    public static LocalDateTime shiftBy(String pattern, LocalDateTime base) {
        if (pattern == null || base == null) return base;

        char unit = pattern.charAt(pattern.length() - 1);
        long value = Long.parseLong(pattern.substring(0, pattern.length() - 1));

        return switch (unit) {
            case 'd' -> base.plusDays(value);
            case 'h' -> base.plusHours(value);
            case 'm' -> base.plusMinutes(value);
            case 's' -> base.plusSeconds(value);
            default -> throw new IllegalArgumentException("Unknown time unit: " + unit);
        };
    }
}
