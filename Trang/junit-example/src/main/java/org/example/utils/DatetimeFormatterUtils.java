package org.example.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DatetimeFormatterUtils {

    private DatetimeFormatterUtils() {
    }

    private static final List<DateTimeFormatter> COMMON_FORMATS = List.of(
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME,
            DateTimeFormatter.RFC_1123_DATE_TIME,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
    );

    public static LocalDateTime parseFlexible(String input) {
        if (input == null || input.isBlank()) return null;

        for (DateTimeFormatter fmt : COMMON_FORMATS) {
            try {
                return LocalDateTime.parse(input, fmt);
            } catch (DateTimeParseException ignore) {
            }

            try {
                return ZonedDateTime.parse(input, fmt).toLocalDateTime();
            } catch (Exception ignore) {
            }

            try {
                LocalDate date = LocalDate.parse(input, fmt);
                return date.atStartOfDay();
            } catch (Exception ignore) {
            }
        }

        throw new IllegalArgumentException("Unrecognized datetime format: " + input);
    }

    public static String convertTimezone(
            String datetime,
            ZoneId from,
            ZoneId to,
            String outputPattern
    ) {
        if (datetime == null || from == null || to == null || outputPattern == null) {
            return null;
        }

        LocalDateTime parsed = parseFlexible(datetime);
        if (parsed == null) return null;

        ZonedDateTime zdt = parsed.atZone(from).withZoneSameInstant(to);
        return zdt.format(DateTimeFormatter.ofPattern(outputPattern));
    }

    public static String toHumanReadableDiff(LocalDateTime target) {
        if (target == null) return null;

        LocalDateTime now = LocalDateTime.now();
        if (target.isAfter(now)) return "in the future";

        Duration diff = Duration.between(target, now);
        long seconds = diff.getSeconds();

        if (seconds < 60) return "just now";
        if (seconds < 3600) return (seconds / 60) + " minutes ago";
        if (seconds < 86_400) return (seconds / 3600) + " hours ago";
        if (seconds < 2 * 86_400) return "yesterday";
        if (seconds < 30L * 86_400) return (seconds / 86_400) + " days ago";
        if (seconds < 365L * 86_400) return (seconds / (30L * 86_400)) + " months ago";

        return (seconds / (365L * 86_400)) + " years ago";
    }

    public static LocalDate getStartOfWeek(LocalDate input) {
        if (input == null) return null;
        return input.with(DayOfWeek.MONDAY);
    }

    public static String formatIsoWithMillis(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
    }

    public static LocalDateTime shiftBy(String pattern, LocalDateTime base) {
        if (pattern == null || base == null) return base;
        if (pattern.length() < 2) {
            throw new IllegalArgumentException("Invalid pattern");
        }

        char unit = pattern.charAt(pattern.length() - 1);
        long value;

        try {
            value = Long.parseLong(pattern.substring(0, pattern.length() - 1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number in pattern");
        }

        return switch (unit) {
            case 'd' -> base.plusDays(value);
            case 'h' -> base.plusHours(value);
            case 'm' -> base.plusMinutes(value);
            case 's' -> base.plusSeconds(value);
            default -> throw new IllegalArgumentException("Unknown time unit: " + unit);
        };
    }
}
