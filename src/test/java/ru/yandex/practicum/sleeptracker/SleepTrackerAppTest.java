package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SleepTrackerAppTest {

    @Test
    void shouldParseDataCorrectly() {
        Metrics metrics = new Metrics();
        List<String> rawData = List.of("03.10.25 23:40;04.10.25 08:00;BAD");

        metrics.setMetrics(rawData);

        assertFalse(metrics.getMetrics().isEmpty());
        assertEquals(1, metrics.getMetrics().size());

        SleepPeriod period = metrics.getMetrics().firstEntry().getValue();
        assertEquals("BAD", period.getSleepQuality());
        assertEquals(23, period.getStartSleep().getHour());
    }

    @Test
    void shouldHandleInvalidDateFormat() {
        Metrics metrics = new Metrics();
        List<String> invalidData = List.of("invalid-date,invalid-date,BAD");
        metrics.setMetrics(invalidData);

        assertTrue(metrics.getMetrics().isEmpty());
    }

}