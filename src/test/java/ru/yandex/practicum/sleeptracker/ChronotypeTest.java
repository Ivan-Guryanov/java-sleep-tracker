package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ChronotypeTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void shouldIdentifyOwlChronotype() {
        // Сова: ложится после 23:00 и встает после 09:00
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "29.12.25 00:30;29.12.25 09:10;GOOD"
        ));

        Chronotype chronotype = new Chronotype();
        chronotype.apply(metrics);

        String output = outputStreamCaptor.toString();
        // В коде используется цвет \u001B[32m (зеленый)
        assertTrue(output.contains("Сова"), "Должен быть определен хронотип 'Сова'");
        assertTrue(output.contains("2025-12-28"), "Дата должна быть скорректирована на день назад (т.к. старт после 09:00)");
    }

    @Test
    void shouldIdentifyLarkChronotype() {
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "28.12.25 21:00;29.12.25 05:00;GOOD"
        ));

        Chronotype chronotype = new Chronotype();
        chronotype.apply(metrics);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Жаворонок"), "Должен быть определен хронотип 'Жаворонок'");
    }

    @Test
    void shouldIdentifyPigeonChronotype() {
        // Голубь: средние значения (не сова и не жаворонок)
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "28.12.25 22:30;29.12.25 07:30;GOOD"
        ));

        Chronotype chronotype = new Chronotype();
        chronotype.apply(metrics);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Голубь"), "Должен быть определен хронотип 'Голубь'");
    }

    @Test
    void shouldHandleMixedData() {
        // Проверка вывода нескольких строк одновременно
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "28.12.25 23:30;29.12.25 10:00;GOOD", // Сова
                "30.12.25 21:00;31.12.25 05:00;GOOD"  // Жаворонок
        ));

        Chronotype chronotype = new Chronotype();
        chronotype.apply(metrics);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Сова") && output.contains("Жаворонок"),
                "Должны быть выведены оба хронотипа для разных дней");
    }
}