package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.function.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    public static void main(String[] args) {
        String path = Optional.of(args)
                .filter(a -> a.length > 0)
                .map(a -> a[0])
                .orElseThrow(() -> new IllegalArgumentException("Укажите путь к файлу с логом сна в аргументах запуска."));

        Metrics spisok = new Metrics();

        try {
            List<String> data = Files.lines(Path.of(path), StandardCharsets.UTF_8)
                    .collect(Collectors.toList());
            System.out.println("Данные о сне успешно загружены.\n");
            spisok.setMetrics(data);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        List<Function<Metrics, SleepAnalysisResult>> functions = new ArrayList<>();
        functions.add(new NumberOfSessions());
        functions.add(new MinSession());
        functions.add(new MaxSession());
        functions.add(new MeanSession());
        functions.add(new FilterSessionBad());
        functions.add(new SleeplessNights());
        functions.add(new Chronotype());
        functions.stream()
                .forEach(function -> System.out.println(function.apply(spisok)));
    }
}