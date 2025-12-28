package ru.yandex.practicum.sleeptracker;


import java.time.Duration;
import java.util.function.Function;

public class MaxSession implements Function<Metrics, Void> {

    @Override
    public Void apply(Metrics metrics) {
        metrics.getMetrics().values().stream()
                .mapToLong(parts -> {
                    return Duration.between(parts.getStartSleep(), parts.getEndSleep()).toMinutes();
                })
                .max()
                .ifPresent(max -> System.out.println("Максимальная сессия сна - " + max + " мин.\n"));

        return null;

    }

}