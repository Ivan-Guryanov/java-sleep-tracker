package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SleeplessNightsTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void shouldIdentifyMissingNightsInCalendar() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "27.12.25 22:00;28.12.25 06:00;GOOD",
                "29.12.25 01:00;29.12.25 05:00;BAD"
        ));

        SleeplessNights sleeplessNights = new SleeplessNights();
        sleeplessNights.apply(metrics);

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("2025-12-28"));
    }

    @Test
    void shouldMarkNightAsSleeplessIfSleepOutsideRange() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "27.12.25 01:00;27.12.25 05:00;GOOD",
                "28.12.25 14:00;28.12.25 16:00;GOOD",
                "29.12.25 01:00;29.12.25 05:00;GOOD"
        ));

        SleeplessNights sleeplessNights = new SleeplessNights();
        sleeplessNights.apply(metrics);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("2025-12-28"));
    }

    @Test
    void shouldNotMarkNightAsSleeplessIfSleepCrossesMidnight() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "27.12.25 23:00;28.12.25 07:00;GOOD",
                "28.12.25 23:00;29.12.25 07:00;GOOD"
        ));

        SleeplessNights sleeplessNights = new SleeplessNights();
        sleeplessNights.apply(metrics);

        String output = outputStreamCaptor.toString();
        assertFalse(output.contains("2025-12-27") || output.contains("2025-12-28"));
    }

    @Test
    void shouldNotMarkNightAsSleeplessWhenSleepEndsExactlyAtMidnight() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "10.01.25 22:00;11.01.25 00:00;GOOD",
                "11.01.25 22:00;12.01.25 00:00;GOOD"
        ));

        SleeplessNights sleeplessNights = new SleeplessNights();
        sleeplessNights.apply(metrics);

        String output = outputStreamCaptor.toString();

        assertFalse(output.contains("2025-01-10"));
        assertFalse(output.contains("2025-01-11"));
    }

    @Test
    void shouldNotMarkNightAsSleeplessWhenSleptForOnlyOneHour() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "20.12.25 02:00;20.12.25 03:00;BAD",
                "21.12.25 02:00;21.12.25 03:00;BAD"
        ));

        SleeplessNights sleeplessNights = new SleeplessNights();
        sleeplessNights.apply(metrics);

        String output = outputStreamCaptor.toString();

        assertFalse(output.contains("2025-12-20"));
    }

}