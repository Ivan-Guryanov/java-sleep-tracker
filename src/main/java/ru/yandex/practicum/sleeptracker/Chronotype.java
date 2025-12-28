package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Chronotype implements Function<Metrics, Void> {

    @Override
    public Void apply(Metrics metrics) {
        LocalTime rangeStartOwl = LocalTime.of(23, 0);
        LocalTime rangeEndOwl = LocalTime.of(9, 0);
        LocalTime rangeStartLark = LocalTime.of(22, 0);
        LocalTime rangeEndLark = LocalTime.of(7, 0);

        List<SleepPeriod> sleeplessDates = metrics.getMetrics().values().stream()
                .filter(session -> {
                    LocalTime sleepTimeStart = session.getStartSleep().toLocalTime();
                    LocalTime sleepTimeEnd = session.getEndSleep().toLocalTime();
                    return !(sleepTimeStart.isBefore(rangeStartLark) && sleepTimeEnd.isBefore(rangeStartLark) &&
                            !sleepTimeStart.isBefore(rangeEndOwl) && !sleepTimeEnd.isBefore(rangeEndOwl));
                })
                .collect(Collectors.toList());

        System.out.println("Ваш хронотип по дням:");
        sleeplessDates.stream()
                .forEach(session -> {
                    LocalDate day = (!session.getStartSleep().toLocalTime().isAfter(rangeEndOwl))
                            ? session.getStartSleep().toLocalDate().minusDays(1)
                            : session.getStartSleep().toLocalDate();

                    String chronotype = ((session.getStartSleep().toLocalTime().isAfter(rangeStartOwl) ||
                            session.getStartSleep().toLocalTime().isBefore(rangeEndOwl)) &&
                            session.getEndSleep().toLocalTime().isAfter(rangeEndOwl)) ? " - Сова."
                            : (session.getStartSleep().toLocalTime().isBefore(rangeStartLark) &&
                            session.getStartSleep().toLocalTime().isAfter(rangeEndOwl)) &&
                            session.getEndSleep().toLocalTime().isBefore(rangeEndLark) ? " - Жаворонок"
                            : " - Голубь";

                    System.out.println(day + "\u001B[32m" + chronotype + "\u001B[0m");
                });

        return null;
    }

}
