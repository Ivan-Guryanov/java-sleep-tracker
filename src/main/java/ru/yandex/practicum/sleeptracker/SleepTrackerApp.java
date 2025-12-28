package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    public static void main(String[] args) {
        String userChoice = mainMenu();
        String path;

        switch (userChoice) {
            case "1" -> path = "src/main/resources/sleep_log.txt";
            case "2" -> path = pathToFile();
            default -> {
                System.out.println("Выход из программы.");
                return;
            }
        }

        Metrics spisok = new Metrics();

        try {
            List<String> data = Files.lines(Path.of(path), StandardCharsets.UTF_8)
                    .collect(Collectors.toList());
            System.out.println("Данные о сне успешно загружены.\n");
            spisok.setMetrics(data);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        List<Function<Metrics, Void>> functions = new ArrayList<>();
        functions.add(new NumberOfSessions());
        functions.add(new MinSession());
        functions.add(new MaxSession());
        functions.add(new MeanSession());
        functions.add(new FilterSessionBad());
        functions.add(new SleeplessNights());
        functions.add(new Chronotype());
        functions.stream()
                .forEach(function -> function.apply(spisok));
    }

    static String mainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать! \n" +
                "Выберете, что вы хотите сделать:\n" +
                "1 - загрузить данные о сне по молчанию;\n" +
                "2 - загрузить свои данные о сня;\n" +
                "Другой символ - выход");
        return scanner.next();
    }

    static String pathToFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите полный путь к файлу лога: ");
        return scanner.next();
    }
}