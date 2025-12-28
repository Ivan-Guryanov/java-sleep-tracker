package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Metrics {
    private TreeMap<LocalDateTime, SleepPeriod> metrics = new TreeMap<>();

    public TreeMap<LocalDateTime, SleepPeriod> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<String> sleepLog) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        try {
            this.metrics = sleepLog.stream()
                    .map(logEntry -> logEntry.split(";", 3))
                    .filter(parts -> parts.length == 3)
                    .map(parts -> {
                        LocalDateTime startSleep = LocalDateTime.parse(parts[0], formatter);
                        LocalDateTime endSleep = LocalDateTime.parse(parts[1], formatter);
                        String quality = parts[2];
                        return new SleepPeriod(startSleep, endSleep, quality);
                    })
                    .collect(Collectors.toMap(
                            SleepPeriod::getStartSleep,
                            period -> period,
                            (existing, replacement) -> existing,
                            TreeMap::new
                    ));
        } catch (Exception e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}
