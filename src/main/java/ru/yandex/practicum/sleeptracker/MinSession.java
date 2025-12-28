package ru.yandex.practicum.sleeptracker;


import java.time.Duration;
import java.util.function.Function;

public class MinSession implements Function<Metrics, Void> {

    @Override
    public Void apply(Metrics metrics) {
        metrics.getMetrics().values().stream()
                .mapToLong(parts -> {
                    return Duration.between(parts.getStartSleep(), parts.getEndSleep()).toMinutes();
                })
                .min()
                .ifPresent(min -> System.out.println("Минимальная сессия сна - " + min + " мин.\n"));

        return null;

    }

}