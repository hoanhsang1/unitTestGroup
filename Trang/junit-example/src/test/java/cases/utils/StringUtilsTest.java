package cases.utils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.example.utils.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class StringUtilsTest {

    @Test
    public void testIsNullOrEmptySuccess() {
        assertTrue(StringUtils.isNullOrEmpty(null));
        assertTrue(StringUtils.isNullOrEmpty(""));
    }

    @Test
    public void testIsNullOrEmptyFalse() {
        assertFalse(StringUtils.isNullOrEmpty(" "));
        assertFalse(StringUtils.isNullOrEmpty("àdsaga"));
        assertFalse(StringUtils.isNullOrEmpty("123"));
        assertFalse(StringUtils.isNullOrEmpty("0"));
        assertFalse(StringUtils.isNullOrEmpty("null"));
    }

    @Test
    public void testIsBlankSuccess() {
        assertTrue(StringUtils.isBlank(null));
        assertTrue(StringUtils.isBlank(""));
        assertTrue(StringUtils.isBlank(" "));
    }

    @Test
    public void testIsBlankFalse() {
        assertFalse(StringUtils.isBlank("adsa"));
        assertFalse(StringUtils.isBlank("0"));
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
                    "a, A",
                    "a1b2c3, A1b2c3",
                    "1a2b3c, 1a2b3c"
            }, nullValues = "null")
    public void testCapitalizeSuccess(String input, String Expected) {
        assertEquals(Expected,StringUtils.capitalize(input));
    }

    @Test
    public void testReverse() {
        assertEquals(StringUtils.reverse("abc"),"cba");
        assertEquals(StringUtils.reverse(null),null);
        assertEquals(StringUtils.reverse("null"),"llun");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "hello, he, true",
            "hello, HE, true",
            "HELLO, he, true",
            "Hello World, world, true",
            "java, python, false",
            "'', '', true",
            "'', a, false",
            "text, '', true",
            "null, '', false",
            "text, null, false"
    }, nullValues = "null")
    void testContainsIgnoreCase(String text, String search, boolean expected) {
        assertEquals(expected, StringUtils.containsIgnoreCase(text, search));
    }

}
