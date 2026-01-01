package ru.yandex.practicum.sleeptracker.function;


import ru.yandex.practicum.sleeptracker.Metrics;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;

import java.time.Duration;
import java.util.function.Function;

public class MeanSession implements Function<Metrics, SleepAnalysisResult> {

    private static final String description = "Средняя продолжительность сна";

    @Override
    public SleepAnalysisResult apply(Metrics metrics) {
        long meanDuration = (long) metrics.getMetrics().values().stream()
                .mapToLong(parts -> Duration.between(parts.getStartSleep(), parts.getEndSleep()).toMinutes())
                .average()
                .orElse(0.0);

        return new SleepAnalysisResult(description, meanDuration);

    }
}