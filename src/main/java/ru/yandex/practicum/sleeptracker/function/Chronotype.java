package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.Metrics;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepPeriod;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Chronotype implements Function<Metrics, SleepAnalysisResult> {

    private static final String description = "Ваш хронотип";

    @Override
    public SleepAnalysisResult apply(Metrics metrics) {
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

        Map<String, Long> chronotypes =  sleeplessDates.stream()
                .map(session -> {
                    return ((session.getStartSleep().toLocalTime().isAfter(rangeStartOwl) ||
                            session.getStartSleep().toLocalTime().isBefore(rangeEndOwl)) &&
                            session.getEndSleep().toLocalTime().isAfter(rangeEndOwl)) ? "Сова"
                            : (session.getStartSleep().toLocalTime().isBefore(rangeStartLark) &&
                            session.getStartSleep().toLocalTime().isAfter(rangeEndOwl)) &&
                            session.getEndSleep().toLocalTime().isBefore(rangeEndLark) ? "Жаворонок"
                            : "Голубь";
                })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        String dominantChronotype = Optional.of(chronotypes)
                .filter(map -> !map.isEmpty())
                .filter(map -> map.getOrDefault("Сова", 0L).equals(map.getOrDefault("Жаворонок", 0L))
                        && map.getOrDefault("Сова", 0L) > 0)
                .map(map -> "Голубь")
                .orElseGet(() -> chronotypes.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse("Не определен"));

        return new SleepAnalysisResult(description, dominantChronotype);
    }
}