package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.Chronotype;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChronotypeTest {

    @Test
    void shouldIdentifyOwlChronotype() {
        // Сова: ложится после 23:00 и встает после 09:00
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "29.12.25 00:30;29.12.25 09:10;GOOD"
        ));

        Chronotype chronotype = new Chronotype();
        SleepAnalysisResult result = chronotype.apply(metrics);

        assertEquals("Сова", result.getResult(), "Должен быть определен хронотип 'Сова'");
    }

    @Test
    void shouldIdentifyLarkChronotype() {
        // Жаворонок: ложится до 22:00 и встает до 07:00
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "28.12.25 21:00;29.12.25 05:00;GOOD"
        ));

        Chronotype chronotype = new Chronotype();
        SleepAnalysisResult result = chronotype.apply(metrics);

        assertEquals("Жаворонок", result.getResult(), "Должен быть определен хронотип 'Жаворонок'");
    }

    @Test
    void shouldIdentifyPigeonChronotype() {
        // Голубь: средние значения (не сова и не жаворонок)
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "28.12.25 22:30;29.12.25 07:30;GOOD"
        ));

        Chronotype chronotype = new Chronotype();
        SleepAnalysisResult result = chronotype.apply(metrics);

        assertEquals("Голубь", result.getResult(), "Должен быть определен хронотип 'Голубь'");
    }

    @Test
    void shouldIdentifyDominantChronotype() {
        // 2 Совы, 1 Жаворонок -> Доминантный: Сова
        Metrics metrics = new Metrics();
        metrics.setMetrics(List.of(
                "29.12.25 23:30;30.12.25 10:00;GOOD", // Сова
                "30.12.25 21:00;31.12.25 05:00;GOOD"  // Жаворонок
        ));

        Chronotype chronotype = new Chronotype();
        SleepAnalysisResult result = chronotype.apply(metrics);

        assertEquals("Голубь", result.getResult(), "Должен быть определен доминантный хронотип 'Сова'");
    }
}