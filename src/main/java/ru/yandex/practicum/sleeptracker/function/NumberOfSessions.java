package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.Metrics;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;

import java.util.function.Function;

public class NumberOfSessions implements Function<Metrics, SleepAnalysisResult> {

    private static final String description = "Количество записей о сне";

    @Override
    public SleepAnalysisResult apply(Metrics metrics) {

        int numberOfSessions = metrics.getMetrics().size();

        return new SleepAnalysisResult(description, numberOfSessions);
    }
}