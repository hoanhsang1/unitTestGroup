package cases.utils;

import base.BaseTestCase;
import org.example.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest extends BaseTestCase {
    @Test
    public void testIsNullOrEmpty() {
        // Chia lam bao nhieu cases?
        // Case 1: input == null
        assertTrue(StringUtils.isNullOrEmpty(null)); // case 1 => expected: true
        // Case 2: input == ""
        assertTrue(StringUtils.isNullOrEmpty("")); // case 2 => expected: true
        // Case 3: input != "" = "kjhfjfshgjdf"
        assertFalse(StringUtils.isNullOrEmpty("hgjhgjshdvjsvcbvcxbj")); // case 3 => expected: false
        assertFalse(StringUtils.isNullOrEmpty("            "));
        // Da hoan thanh muc tieu cua method => Passed
    }

    @Test
    public void testIsBlank() {
        // case 1: input null => true
        assertTrue(StringUtils.isBlank(null));
        // case 2: input rong => true
        assertTrue(StringUtils.isBlank(""));
        // case 3: input blank
        assertTrue(StringUtils.isBlank("            "));
        // case 4: input not null not blank not empty
        assertFalse(StringUtils.isBlank("hgjhgjshdvjsvcbvcxbj"));
    }

    @Test
    public void testCapitalize() {
        assertNull(StringUtils.capitalize(null));
        assertEquals("", StringUtils.capitalize(""));
        assertEquals("Abcd", StringUtils.capitalize("abcd"));
        assertEquals("Uppercase", StringUtils.capitalize("UPPERCASE"));
        assertEquals("Love You To The Moon", StringUtils.capitalize("love you to the moon"));
        assertEquals("@#$%", StringUtils.capitalize("@#$%"));
        assertEquals("   ", StringUtils.capitalize("   "));
        assertEquals("123", StringUtils.capitalize("123"));
        assertEquals(" Abc", StringUtils.capitalize(" abc"));
        assertEquals("Ă", StringUtils.capitalize("ă"));
        assertEquals("A1b2c3", StringUtils.capitalize("a1b2c3"));
        assertEquals("1a2b3c", StringUtils.capitalize("1a2b3c"));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "null, null",
            "'', ''",
            "abcd, Abcd",
            "UPPERCASE, Uppercase",
            "'love you to the moon', 'Love You To The Moon'",
            "'@#$%', '@#$%'",
            "'   ', '   '",
            "123, 123",
            "' abc', ' Abc'",
            "ă, Ă",
            "a1b2c3, A1b2c3",
            "1a2b3c, 1a2b3c",
            "😀, 😀"
    }, nullValues = "null")
    void testCapitalize(String input, String expected) {
        assertEquals(expected, StringUtils.capitalize(input));
    }

    public static String reverse(String input) {
        if (input == null)
            return null;
        return new StringBuilder(input).reverse().toString();
    }

    @Test
    public void testReverse() {
        assertNull(StringUtils.reverse(null)); // case 1
        assertEquals("", StringUtils.reverse("")); // case 2
        assertEquals("dcba", StringUtils.reverse("abcd")); // case 3
        assertEquals("   ", StringUtils.reverse("   ")); // case 4
        assertEquals("321", StringUtils.reverse("123")); // case 5
        assertEquals("%$#@", StringUtils.reverse("@#$%")); // case 6
        assertEquals("c b a", StringUtils.reverse("a b c")); // case 7
        assertEquals("😀", StringUtils.reverse("😀")); // case 8
    }

    @ParameterizedTest
    @CsvSource(value = {
            "null, null",
            "'', ''",
            "abcd, dcba",
            "'   ', '   '",
            "123, 321",
            "'@#$%', '%$#@'",
            "'a b c', 'c b a'",
            "😀, 😀"
    }, nullValues = "null")
    void testReverseParameterized(String input, String expected) {
        assertEquals(expected, StringUtils.reverse(input));
    }

    public static boolean containsIgnoreCase(String text, String search) {
        if (text == null || search == null)
            return false;
        return text.toLowerCase().contains(search.toLowerCase());
    }

    @Test
    public void testContainsIgnoreCase() {
        // case null
        assertFalse(StringUtils.containsIgnoreCase(null, "a"));
        assertFalse(StringUtils.containsIgnoreCase("abc", null));
        assertFalse(StringUtils.containsIgnoreCase(null, null));

        // case empty
        assertTrue(StringUtils.containsIgnoreCase("abc", ""));
        assertFalse(StringUtils.containsIgnoreCase("", "a"));

        // case normal
        assertTrue(StringUtils.containsIgnoreCase("Hello World", "hello"));
        assertTrue(StringUtils.containsIgnoreCase("Hello World", "WORLD"));
        assertFalse(StringUtils.containsIgnoreCase("Hello World", "Java"));

        // case special & unicode
        assertTrue(StringUtils.containsIgnoreCase("@#$%", "#$"));
        assertTrue(StringUtils.containsIgnoreCase("Ăn cơm", "ăn"));
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "null, abc, false",
                    "abc, null, false",
                    "null, null, false",
                    "abc, '', true",
                    "'', a, false",
                    "'Hello World', hello, true",
                    "'Hello World', WORLD, true",
                    "'Hello World', Java, false",
                    "'@#$%', '$#', true",
                    "'Ăn cơm', ăn, true"
            },
            nullValues = "null"
    )
    void testContainsIgnoreCaseParameterized(String text, String search, boolean expected) {
        assertEquals(expected, StringUtils.containsIgnoreCase(text, search));
    }

}#

Sang nè
