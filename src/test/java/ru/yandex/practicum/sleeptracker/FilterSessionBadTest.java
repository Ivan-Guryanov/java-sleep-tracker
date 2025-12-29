package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.FilterSessionBad;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilterSessionBadTest {

    @Test
    void shouldCountOnlyBadQualitySessions() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "25.12.25 22:00;26.12.25 06:00;BAD",
                "26.12.25 22:00;27.12.25 06:00;GOOD",
                "27.12.25 22:00;28.12.25 06:00;BAD"
        ));

        FilterSessionBad filterSessionBad = new FilterSessionBad();
        SleepAnalysisResult result = filterSessionBad.apply(metrics);

        assertEquals(2, result.getResult(), "Должно быть 2 плохих сессии");
    }

    @Test
    void shouldReturnZeroWhenNoBadSessionsFound() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "28.12.25 22:00;29.12.25 06:00;GOOD"
        ));

        FilterSessionBad filterSessionBad = new FilterSessionBad();
        SleepAnalysisResult result = filterSessionBad.apply(metrics);

        assertEquals(0, result.getResult(), "Должно быть 0 плохих сессий");
    }

    @Test
    void shouldHandleEmptyMetrics() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of());

        FilterSessionBad filterSessionBad = new FilterSessionBad();
        SleepAnalysisResult result = filterSessionBad.apply(metrics);

        assertEquals(0, result.getResult(), "Для пустого списка результат должен быть 0");
    }
}