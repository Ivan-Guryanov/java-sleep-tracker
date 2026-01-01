package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.MinSession;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinSessionTest {

    @Test
    void shouldFindMinimumSleepDuration() {
        Metrics metrics = new Metrics();
        // 1. 22:00 -> 02:00 = 4 часа = 240 минут
        // 2. 23:00 -> 01:00 = 2 часа = 120 минут
        metrics.setMetrics(List.of(
                "28.12.25 22:00;29.12.25 02:00;GOOD",
                "29.12.25 23:00;30.12.25 01:00;BAD"
        ));

        MinSession minSession = new MinSession();
        SleepAnalysisResult result = minSession.apply(metrics);

        assertEquals(120L, result.getResult(), "Минимальная продолжительность должна быть 120 минут");
    }

    @Test
    void shouldReturnZeroWhenMetricsEmpty() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of());

        MinSession minSession = new MinSession();
        SleepAnalysisResult result = minSession.apply(metrics);

        assertEquals(0L, result.getResult(), "Для пустого списка результат должен быть 0");
    }
}