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
    @CsvSource(
            value = {
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

    @Test
    public void testReverse() {
        // Case 1: input = null => expected: null
        assertNull(StringUtils.reverse(null));

        // Case 2: input = empty string => expected: "" (vẫn là empty string)
        assertEquals("", StringUtils.reverse(""));

        // Case 3: input = blank string (chỉ có spaces) => expected: giữ nguyên spaces
        assertEquals("   ", StringUtils.reverse("   "));

        // Case 4: input = single character => expected: chính nó
        assertEquals("a", StringUtils.reverse("a"));

        // Case 5: input = multiple characters => expected: đảo ngược thứ tự
        assertEquals("dcba", StringUtils.reverse("abcd"));

        // Case 6: input = palindrome => expected: giống ban đầu
        assertEquals("radar", StringUtils.reverse("radar"));

        // Case 7: input = mixed case => expected: đảo ngược nhưng giữ nguyên case
        assertEquals("cbA", StringUtils.reverse("Abc"));

        // Case 8: input với special characters => expected: đảo ngược cả special chars
        assertEquals("!@#$%", StringUtils.reverse("%$#@!"));

        // Case 9: input với numbers => expected: đảo ngược số
        assertEquals("54321", StringUtils.reverse("12345"));

        // Case 10: input với unicode characters => expected: đảo ngược unicode
        assertEquals("cbaĀ", StringUtils.reverse("Āabc"));

        // Case 11: input với emoji => expected: đảo ngược thứ tự emoji
        assertEquals("😀🌍", StringUtils.reverse("🌍😀"));

        // Case 12: input có whitespace ở giữa => expected: đảo ngược nhưng giữ vị trí whitespace
        assertEquals("c ba", StringUtils.reverse("ab c"));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "null, null",                      // null input => null output
            "'', ''",                         // empty string => empty string
            "'   ', '   '",                   // blank string => giữ nguyên
            "a, a",                           // single character => chính nó
            "abcd, dcba",                     // simple string => đảo ngược
            "radar, radar",                   // palindrome => giống ban đầu
            "Abc, cbA",                       // mixed case => đảo ngược giữ case
            "'%$#@!', '!@#$%'",              // special characters => đảo ngược
            "12345, 54321",                   // numbers => đảo ngược số
            "Āabc, cbaĀ",                     // unicode => đảo ngược
            "'🌍😀', '😀🌍'",               // emoji => đảo ngược thứ tự
            "'ab c', 'c ba'",                 // với whitespace => giữ whitespace
            "Hello World, dlroW olleH",       // multiple words => đảo ngược cả chuỗi
            "a1b2c3, 3c2b1a",                 // alphanumeric => đảo ngược
            "A man a plan a canal Panama, amanaP lanac a nalp a nam A" // complex palindrome
    }, nullValues = "null")
    void testReverseParameterized(String input, String expected) {
        // Test nhiều trường hợp với parameterized test
        assertEquals(expected, StringUtils.reverse(input));
    }


//


    @Test
    public void testContainsIgnoreCase() {
        // Case 1: cả text và search đều null => expected: false (theo logic hiện tại)
        assertFalse(StringUtils.containsIgnoreCase(null, null));

        // Case 2: text null, search không null => expected: false
        assertFalse(StringUtils.containsIgnoreCase(null, "search"));

        // Case 3: text không null, search null => expected: false
        assertFalse(StringUtils.containsIgnoreCase("text", null));

        // Case 4: cả text và search đều empty => expected: true (empty string chứa empty string)
        assertTrue(StringUtils.containsIgnoreCase("", ""));

        // Case 5: text không empty, search empty => expected: true (mọi chuỗi đều chứa empty string)
        assertTrue(StringUtils.containsIgnoreCase("Hello World", ""));

        // Case 6: exact match (trùng hoàn toàn) => expected: true
        assertTrue(StringUtils.containsIgnoreCase("Hello World", "Hello World"));

        // Case 7: case insensitive match (không phân biệt hoa/thường) => expected: true
        assertTrue(StringUtils.containsIgnoreCase("Hello World", "hello world"));
        assertTrue(StringUtils.containsIgnoreCase("HELLO WORLD", "hello world"));
        assertTrue(StringUtils.containsIgnoreCase("hello world", "HELLO WORLD"));

        // Case 8: partial match (tìm chuỗi con) => expected: true
        assertTrue(StringUtils.containsIgnoreCase("Hello World", "ello"));
        assertTrue(StringUtils.containsIgnoreCase("Hello World", "WORLD"));

        // Case 9: không tìm thấy => expected: false
        assertFalse(StringUtils.containsIgnoreCase("Hello World", "xyz"));

        // Case 10: search dài hơn text => expected: false (không thể chứa chuỗi dài hơn)
        assertFalse(StringUtils.containsIgnoreCase("Hello", "Hello World"));

        // Case 11: với special characters => expected: tìm kiếm case insensitive
        assertTrue(StringUtils.containsIgnoreCase("Hello@World#123", "@world"));
        assertFalse(StringUtils.containsIgnoreCase("Hello@World#123", "@WORLD!")); // thiếu '!'

        // Case 12: với numbers => expected: true
        assertTrue(StringUtils.containsIgnoreCase("Version 2.0", "2.0"));
        assertTrue(StringUtils.containsIgnoreCase("Version 2.0", "version"));

        // Case 13: với unicode characters => expected: case insensitive với unicode
        assertTrue(StringUtils.containsIgnoreCase("Café", "CAFÉ"));
        assertTrue(StringUtils.containsIgnoreCase("Café", "café"));

        // Case 14: với emoji => expected: true (emoji thường không có case)
        assertTrue(StringUtils.containsIgnoreCase("Hello 😀 World", "😀"));
        assertTrue(StringUtils.containsIgnoreCase("Hello 😀 World", "WORLD"));

        // Case 15: whitespace sensitivity => expected: false (vì whitespace khác nhau)
        assertFalse(StringUtils.containsIgnoreCase("HelloWorld", "Hello World")); // thiếu space
        assertTrue(StringUtils.containsIgnoreCase("Hello World", "Hello  World")); // double space tìm trong single space

        // Case 16: leading/trailing spaces trong text => expected: true (vẫn tìm thấy)
        assertTrue(StringUtils.containsIgnoreCase("  Hello World  ", "hello"));

        // Case 17: leading/trailing spaces trong search => expected: true (tìm thấy phần giữa)
        assertTrue(StringUtils.containsIgnoreCase("Hello World", " hello "));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "null, null, false",                     // cả hai null => false
            "null, 'search', false",                // text null => false
            "'text', null, false",                  // search null => false
            "'', '', true",                         // cả hai empty => true
            "'Hello World', '', true",              // search empty => true
            "'Hello World', 'Hello World', true",   // exact match => true
            "'Hello World', 'hello world', true",   // case insensitive exact => true
            "'HELLO WORLD', 'hello world', true",   // all uppercase text => true
            "'hello world', 'HELLO WORLD', true",   // all lowercase text => true
            "'Hello World', 'ELLO', true",          // partial match => true
            "'Hello World', 'WOR', true",           // partial match different case => true
            "'Hello World', 'xyz', false",          // no match => false
            "'Hello', 'Hello World', false",        // search dài hơn text => false
            "'Café', 'CAFÉ', true",                 // unicode case insensitive => true
            "'Hello 😀 World', '😀', true",        // với emoji => true
            "'  Hello  ', 'HELLO', true",           // spaces trong text => true
            "'Hello', ' HELLO ', true",             // spaces trong search => true
            "'Version 2.0', '2.0', true",           // với numbers => true
            "'Hello@World', '@WORLD', true",        // với special characters => true
            "'Java Programming', 'PROG', true",     // substring match => true
            "'Multiple   Spaces', 'spaces', true",  // multiple spaces => true
            "'CaseSensitive', 'casesensitive', true", // camel case => true
            "'12345', '234', true",                 // numbers only => true
            "'Mixed123', 'mixed', true"             // alphanumeric => true
    }, nullValues = "null")
    void testContainsIgnoreCaseParameterized(String text, String search, boolean expected) {
        // Parameterized test cho nhiều trường hợp
        assertEquals(expected, StringUtils.containsIgnoreCase(text, search));
    }
}
#hello