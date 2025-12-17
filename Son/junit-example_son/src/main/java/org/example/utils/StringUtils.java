package org.example.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class StringUtils {
    private StringUtils() {
        // prevent instantiation
    }

    private static final String EMPTY = "";

    public static boolean isNullOrEmpty(String input) {
        return input == null || input.equals(EMPTY);
    }

    public static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }

    private static String _capitalizeWord(String input) {
        if (isBlank(input)) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public static String capitalize(String input) {
        if (isBlank(input)) return input;
        var inputArr = input.split(" ");
        System.out.println(Arrays.toString(inputArr));
        var result = new ArrayList<String>();
        for (var item : inputArr) {
            result.add(_capitalizeWord(item));
        }
        return String.join(" ", result);
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
