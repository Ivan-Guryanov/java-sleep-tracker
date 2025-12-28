package ru.yandex.practicum.sleeptracker;

import java.util.function.Function;

public class FilterSessionBad implements Function<Metrics, Void> {

    @Override
    public Void apply(Metrics metrics) {
        int numberBad = Math.toIntExact(metrics.getMetrics().values().stream()
                .filter(parts -> parts.getSleepQuality().equals("BAD"))
                .count());
        System.out.println("Вы плохо спали - " + numberBad + " раз(а).\n");

        return null;

    }

}