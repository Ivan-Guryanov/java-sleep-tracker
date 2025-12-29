package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.NumberOfSessions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberOfSessionsTest {

    @Test
    void shouldCountSessionsCorrectly() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "28.12.25 23:00;29.12.25 07:00;GOOD",
                "29.12.25 23:00;30.12.25 07:00;GOOD"
        ));

        NumberOfSessions numberOfSessions = new NumberOfSessions();
        SleepAnalysisResult result = numberOfSessions.apply(metrics);

        assertEquals(2, result.getResult(), "Должно быть 2 записи о сне");
    }

    @Test
    void shouldReturnZeroForEmptyMetrics() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of());

        NumberOfSessions numberOfSessions = new NumberOfSessions();
        SleepAnalysisResult result = numberOfSessions.apply(metrics);

        assertEquals(0, result.getResult(), "Должно быть 0 записей для пустого списка");
    }
}