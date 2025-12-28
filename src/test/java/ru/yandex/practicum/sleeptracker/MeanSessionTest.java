package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MeanSessionTest {
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
    void shouldFindMeanSleepDuration() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "28.12.25 22:00;29.12.25 02:00;GOOD",
                "29.12.25 23:00;30.12.25 01:00;BAD"
        ));

        MeanSession meanSession = new MeanSession();
        meanSession.apply(metrics);

        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Средняя продолжительность сна - 180 мин."));
    }

    @Test
    void shouldNotPrintAnythingWhenMetricsEmpty() {
        Metrics metrics = new Metrics();

        MeanSession meanSession = new MeanSession();
        meanSession.apply(metrics);

        String output = outputStreamCaptor.toString().trim();

        assertTrue(output.isEmpty());
    }
}