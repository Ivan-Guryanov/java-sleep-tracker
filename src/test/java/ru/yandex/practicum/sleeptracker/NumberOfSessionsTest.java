package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberOfSessionsTest {
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
    void shouldPrintCorrectNumberOfSessions() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "28.12.25 22:00;29.12.25 06:00;GOOD",
                "29.12.25 23:00;30.12.25 07:00;BAD"
        ));

        NumberOfSessions numberOfSessions = new NumberOfSessions();
        numberOfSessions.apply(metrics);

        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Количество записей о сне - 2"));
    }

    @Test
    void shouldPrintZeroForEmptyMetrics() {
        Metrics metrics = new Metrics();

        NumberOfSessions numberOfSessions = new NumberOfSessions();
        numberOfSessions.apply(metrics);

        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Количество записей о сне - 0"));
    }
}