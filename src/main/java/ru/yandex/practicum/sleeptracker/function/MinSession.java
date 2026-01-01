package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.Metrics;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;

import java.time.Duration;
import java.util.Comparator;
import java.util.function.Function;

public class MinSession implements Function<Metrics, SleepAnalysisResult> {

    private static final String description = "Минимальная продолжительность сна";

    @Override
    public SleepAnalysisResult apply(Metrics metrics) {
        long minDuration = metrics.getMetrics().values().stream()
                .map(record -> Duration.between(record.getStartSleep(), record.getEndSleep()).toMinutes())
                .min(Comparator.naturalOrder())
                .orElse(0L);

        return new SleepAnalysisResult(description, minDuration);

    }

}