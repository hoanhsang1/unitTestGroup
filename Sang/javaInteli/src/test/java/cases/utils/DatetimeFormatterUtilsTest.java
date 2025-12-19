package cases.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;
import org.example.utils.DatetimeFormatterUtils;

class DatetimeFormatterUtilsTest {

    // ===== parseFlexible =====
    @ParameterizedTest
    @CsvSource({
            "2025-01-10T10:15:30",
            "2025-01-10 10:15:30",
            "2025/01/10 10:15",
            "2025/01/10 10:15:30",
            "10-01-2025 10:15",
            "10/01/2025 10:15",
            "10/01/2025",
    })
    public void testParseFlexibleSuccess(String input) {
        assertNotNull(DatetimeFormatterUtils.parseFlexible(input));
    }

    @Test
    public void testParseFlexibleNull() {
        assertNull(DatetimeFormatterUtils.parseFlexible(null));
        assertNull(DatetimeFormatterUtils.parseFlexible(""));
        assertNull(DatetimeFormatterUtils.parseFlexible(" "));
    }

    @Test
    public void testParseFlexibleInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> DatetimeFormatterUtils.parseFlexible("invalid-date"));
    }

    // ===== convertTimezone =====
    @Test
    public void testConvertTimezone() {
        String result = DatetimeFormatterUtils.convertTimezone(
                "2025-01-10T10:00:00",
                ZoneId.of("UTC"),
                ZoneId.of("Asia/Ho_Chi_Minh"),
                "yyyy-MM-dd HH:mm"
        );
        assertEquals("2025-01-10 17:00", result);
    }

    // ===== toHumanReadableDiff =====
    @Test
    public void testHumanReadableJustNow() {
        LocalDateTime now = LocalDateTime.now().minusSeconds(10);
        assertEquals("just now",
                DatetimeFormatterUtils.toHumanReadableDiff(now));
    }

    @Test
    public void testHumanReadableMinutes() {
        LocalDateTime time = LocalDateTime.now().minusMinutes(5);
        assertEquals("5 minutes ago",
                DatetimeFormatterUtils.toHumanReadableDiff(time));
    }

    // ===== getStartOfWeek =====
    @Test
    public void testGetStartOfWeek() {
        LocalDate date = LocalDate.of(2025, 1, 8); // Wednesday
        assertEquals(LocalDate.of(2025, 1, 6),
                DatetimeFormatterUtils.getStartOfWeek(date));
    }

    // ===== formatIsoWithMillis =====
    @Test
    public void testFormatIsoWithMillis() {
        LocalDateTime dt = LocalDateTime.of(2025, 1, 1, 10, 0, 0, 123_000_000);
        assertEquals("2025-01-01T10:00:00.123",
                DatetimeFormatterUtils.formatIsoWithMillis(dt));
    }

    @Test
    public void testFormatIsoWithMillisNull() {
        assertNull(DatetimeFormatterUtils.formatIsoWithMillis(null));
    }

    // ===== shiftBy =====
    @Test
    public void testShiftByDays() {
        LocalDateTime base = LocalDateTime.of(2025, 1, 1, 0, 0);
        assertEquals(base.plusDays(1),
                DatetimeFormatterUtils.shiftBy("1d", base));
    }

    @Test
    public void testShiftByHours() {
        LocalDateTime base = LocalDateTime.of(2025, 1, 1, 0, 0);
        assertEquals(base.minusHours(3),
                DatetimeFormatterUtils.shiftBy("-3h", base));
    }

    @Test
    public void testShiftByMinutes() {
        LocalDateTime base = LocalDateTime.of(2025, 1, 1, 0, 0);
        assertEquals(base.minusMinutes(3),
                DatetimeFormatterUtils.shiftBy("-3m", base));
    }

    @Test
    public void testShiftBySeconds() {
        LocalDateTime base = LocalDateTime.of(2025, 1, 1, 0, 0,4);
        assertEquals(base.minusSeconds(3),
                DatetimeFormatterUtils.shiftBy("-3s", base));
    }

    @Test
    public void testShiftByInvalidUnit() {
        LocalDateTime base = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class,
                () -> DatetimeFormatterUtils.shiftBy("5x", base));
    }

    @Test
    public void testShiftByPatternNull() {
        LocalDateTime base = LocalDateTime.of(2025, 1, 1, 0, 0);
        assertEquals(base, DatetimeFormatterUtils.shiftBy(null, base));
    }

    @Test
    public void testShiftByBaseNull() {
        assertNull(DatetimeFormatterUtils.shiftBy("1d", null));
    }

    @Test
    public void testShiftByBothNull() {
        assertNull(DatetimeFormatterUtils.shiftBy(null, null));
    }
}
