package ru.yandex.practicum.sleeptracker;


import java.time.Duration;
import java.util.function.Function;

public class MeanSession implements Function<Metrics, Void> {

    @Override
    public Void apply(Metrics metrics) {
        metrics.getMetrics().values().stream()
                .mapToLong(parts -> {
                    return Duration.between(parts.getStartSleep(), parts.getEndSleep()).toMinutes();
                })
                .average()
                .ifPresent(mean -> System.out.println("Средняя продолжительность сна - " + (long) mean + " мин.\n"));

        return null;

    }

}