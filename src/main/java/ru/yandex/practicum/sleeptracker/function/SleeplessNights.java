package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.Metrics;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepPeriod;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Function;

public class SleeplessNights implements Function<Metrics, SleepAnalysisResult> {

    private static final String description = "Вы не спали (ночей)";

    @Override
    public SleepAnalysisResult apply(Metrics metrics) {
        if (metrics.getMetrics().isEmpty()) {
            return new SleepAnalysisResult(description, 0L);
        }

        var sleepPeriods = metrics.getMetrics().values();
        LocalDate start = metrics.getMetrics().firstEntry().getValue().getStartSleep().toLocalDate();
        LocalDate end = metrics.getMetrics().lastEntry().getValue().getEndSleep().toLocalDate();
        SleepPeriod lastSession = metrics.getMetrics().lastEntry().getValue();
        LocalTime rangeEnd = LocalTime.of(6, 0);

        boolean lastSessionBelongsToPreviousNight = isSleepCoveringNight(lastSession, end.minusDays(1), rangeEnd);
        LocalDate effectiveEnd = lastSessionBelongsToPreviousNight ? end : end.plusDays(1);

        long count = start.datesUntil(effectiveEnd)
                .filter(date -> sleepPeriods.stream()
                        .noneMatch(session -> isSleepCoveringNight(session, date, rangeEnd)))
                .count();

        return new SleepAnalysisResult(description, count);
    }

    private boolean isSleepCoveringNight(SleepPeriod session, LocalDate date, LocalTime rangeEnd) {
        LocalDate nextDate = date.plusDays(1);

        boolean crossesMidnight = session.getStartSleep().toLocalDate().equals(date) &&
                session.getEndSleep().toLocalDate().isAfter(date);

        boolean startsEarlyNextMorning = session.getStartSleep().toLocalDate().equals(nextDate) &&
                session.getStartSleep().toLocalTime().isBefore(rangeEnd);

        return crossesMidnight || startsEarlyNextMorning;
    }
}