package cases.utils;
import static org.junit.jupiter.api.Assertions.*;
import org.example.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvFileSource;


public class StringUtilsTest {
    @Test
    public void testIsNullOrEmpty(){
        // Case 1: input == null
        assertTrue(StringUtils.isNullOrEmpty(null)); // case 1 => expected: true
        // Case 2: input == ""
        assertTrue(StringUtils.isNullOrEmpty("")); // case 2 => expected: true
        // Case 3: input != "" = "kjhfjfshgjdf"
        assertFalse(StringUtils.isNullOrEmpty("hgjhgjshdvjsvcbvcxbj")); // case 3 => expected: false
        // case 4: input not null not blank not empty
        assertFalse(StringUtils.isBlank("hgjhgjshdvjsvcbvcxbi"));

    }
    @Test

    public void testCapitalize() {
        assertNull(StringUtils.capitalize(null));
        assertEquals("", StringUtils.capitalize(""));
        assertEquals("Abcd", StringUtils.capitalize("abcd"));
        assertEquals("Uppercase", StringUtils.capitalize("UPPERCASE"));
        assertEquals("Love You To The Moon", StringUtils.capitalize("Love you to the moon"));
        assertEquals("@#$%", StringUtils.capitalize("@#$%"));
        assertEquals(" ", StringUtils.capitalize(" "));
        assertEquals("123", StringUtils.capitalize("123"));
        assertEquals(" Abe", StringUtils.capitalize(" abe"));
        assertEquals("A", StringUtils.capitalize("a"));
        assertEquals("A1B2c3", StringUtils.capitalize("a1b2c3"));
        assertEquals("1a2b3c", StringUtils.capitalize("a1b2c3"));

    }

    @ParameterizedTest
    @CsvSource(value = {
            "null, null",
            "'', ''",
            "abcd, Abcd",
            "UPPERCASE, Uppercase",
            "'Love you to the moon', 'Love you to the moon'",
            "'@#$%', '@#$%'",
            "' ', ' '",
            "123, 123",
            "' abc', ' abc'",
            "a, A",
            "a1b2c3, A1b2c3",
            "1a20bc, 1a20bc"
    }, nullValues = "null")
    void testCapitalize(String input, String expected) {
        assertEquals(expected, StringUtils.capitalize(input));
    }
}
