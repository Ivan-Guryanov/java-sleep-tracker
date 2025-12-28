package ru.yandex.practicum.sleeptracker;

import java.util.function.Function;

public class NumberOfSessions implements Function<Metrics, Void> {

    @Override
    public Void apply(Metrics metrics) {
        System.out.println("Количество записей о сне - " + metrics.getMetrics().size() + ".\n");
        return null;
    }

}