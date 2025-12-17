package org.example.utils;

public class StringUtils {
    private StringUtils() {
        // prevent instantiation
    }

    public static boolean isNullOrEmpty(String input) {
        return input == null || input.isEmpty();
    }

    public static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static String capitalize(String input) {
        if (isNullOrEmpty(input)) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public static String reverse(String input) {
        if (input == null) return null;
        return new StringBuilder(input).reverse().toString();
    }

    public static boolean containsIgnoreCase(String text, String search) {
        if (text == null || search == null) return false;
        return text.toLowerCase().contains(search.toLowerCase());
    }
}
