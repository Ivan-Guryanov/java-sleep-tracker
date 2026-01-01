package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.Metrics;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;

import java.util.function.Function;

public class FilterSessionBad implements Function<Metrics, SleepAnalysisResult> {

    private static final String description = "Вы плохо спали (дней)";

    @Override
    public SleepAnalysisResult apply(Metrics metrics) {
        int numberBad = Math.toIntExact(metrics.getMetrics().values().stream()
                .filter(parts -> parts.getSleepQuality().equals("BAD"))
                .count());

        return new SleepAnalysisResult(description, numberBad);

    }
}