package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterSessionBadTest {
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
    void shouldCountOnlyBadQualitySessions() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "25.12.25 22:00;26.12.25 06:00;BAD",
                "26.12.25 22:00;27.12.25 06:00;GOOD",
                "27.12.25 22:00;28.12.25 06:00;BAD"
        ));

        FilterSessionBad filterSessionBad = new FilterSessionBad();
        filterSessionBad.apply(metrics);

        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Вы плохо спали - 2 раз(а)."));
    }

    @Test
    void shouldPrintZeroWhenNoBadSessionsFound() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "28.12.25 22:00;29.12.25 06:00;GOOD"
        ));

        FilterSessionBad filterSessionBad = new FilterSessionBad();
        filterSessionBad.apply(metrics);

        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Вы плохо спали - 0 раз(а)."));
    }

    @Test
    void shouldHandleEmptyMetrics() {
        Metrics metrics = new Metrics();

        FilterSessionBad filterSessionBad = new FilterSessionBad();
        filterSessionBad.apply(metrics);

        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Вы плохо спали - 0 раз(а)."));
    }
}