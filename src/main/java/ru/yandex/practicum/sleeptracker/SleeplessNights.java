package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleeplessNights implements Function<Metrics, Void> {

    @Override
    public Void apply(Metrics metrics) {
        List<LocalDate> allDates = getFullCalendar(metrics);
        LocalTime rangeStart = LocalTime.MIDNIGHT;
        LocalTime rangeEnd = LocalTime.of(6, 0);

        List<LocalDate> sleeplessDates = allDates.stream()
                .filter(date -> !metrics.getMetrics().values().stream()
                        .anyMatch(session -> {
                            boolean sameDate = session.getStartSleep().toLocalDate().equals(date);
                            LocalTime sleepTimeStart = session.getStartSleep().toLocalTime();
                            LocalTime sleepTimeEnd = session.getEndSleep().toLocalTime();
                            boolean inRange = (!sleepTimeStart.isBefore(rangeStart) && sleepTimeStart.isBefore(rangeEnd)) ||
                                    (!sleepTimeEnd.isBefore(rangeStart) && sleepTimeEnd.isBefore(rangeEnd)) ||
                                    (!session.getStartSleep().toLocalDate().equals(session.getEndSleep().toLocalDate()));
                            return sameDate && inRange;
                        }))
                .collect(Collectors.toList());
        System.out.println("Вы не спали:");
        sleeplessDates.forEach(date -> System.out.println("- " + date));
        System.out.println("\n");
        return null;
    }

    private List<LocalDate> getFullCalendar(Metrics metrics) {
        LocalDate start = metrics.getMetrics().firstEntry().getValue().getStartSleep().toLocalDate();
        LocalDate end = metrics.getMetrics().lastEntry().getValue().getEndSleep().toLocalDate();

        return start.datesUntil(end.plusDays(1)).collect(Collectors.toList());
    }
}
