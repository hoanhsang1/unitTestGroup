package org.example.utils;

import java.util.ArrayList;
import java.util.List;

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
        if (input == null || input.isEmpty()) return input;

        StringBuilder sb = new StringBuilder(input.length());
        boolean newWord = true;

        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c)) {
                sb.append(c);
                newWord = true;
            } else {
                if (newWord) {
                    sb.append(Character.toUpperCase(c));
                    newWord = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
        }
        return sb.toString();
    }



    public static String reverse(String input) {
        if (input == null) return null;
        return new StringBuilder(input).reverse().toString();
    }

    public static boolean containsIgnoreCase(String text, String search) {
        if (text == null || search == null) {
            return false;
        }

        String t = text.toLowerCase();
        String s = search.toLowerCase();

        return t.contains(s);
    }

}
