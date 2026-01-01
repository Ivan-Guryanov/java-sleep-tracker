package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.Metrics;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;

import java.time.Duration;
import java.util.Comparator;
import java.util.function.Function;

public class MaxSession implements Function<Metrics, SleepAnalysisResult> {

    private static final String description = "Максимальная сессия сна";

    @Override
    public SleepAnalysisResult apply(Metrics metrics) {
        long maxDuration = metrics.getMetrics().values().stream()
                .map(parts -> Duration.between(parts.getStartSleep(), parts.getEndSleep()).toMinutes())
                .max(Comparator.naturalOrder())
                .orElse(0L);

        return new SleepAnalysisResult(description, maxDuration);

    }
}