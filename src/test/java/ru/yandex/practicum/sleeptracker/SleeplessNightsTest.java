package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.SleeplessNights;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleeplessNightsTest {

    @Test
    void shouldCountZeroSleeplessNightsWhenSleepIsRegular() {
        Metrics metrics = new Metrics();
        // Сон каждую ночь с 28 по 30 число
        metrics.setMetrics(List.of(
                "28.12.25 23:00;29.12.25 07:00;GOOD",
                "29.12.25 23:00;30.12.25 07:00;GOOD"
        ));

        SleeplessNights sleeplessNights = new SleeplessNights();
        SleepAnalysisResult result = sleeplessNights.apply(metrics);

        // Ожидаем 0 бессонных ночей, так как все ночи покрыты сном
        assertEquals(0L, result.getResult(), "Должно быть 0 бессонных ночей");
    }

    @Test
    void shouldCountOneSleeplessNightWhenGapExists() {
        Metrics metrics = new Metrics();
        // Сон 28-го и 30-го. 29-е пропущено (бессонная ночь).
        metrics.setMetrics(List.of(
                "28.12.25 23:00;29.12.25 07:00;GOOD",
                "30.12.25 23:00;31.12.25 07:00;GOOD"
        ));

        SleeplessNights sleeplessNights = new SleeplessNights();
        SleepAnalysisResult result = sleeplessNights.apply(metrics);

        // Диапазон дат: 28, 29, 30, 31.
        // Сон есть для ночи 28->29 и 30->31.
        // Ночь 29->30 считается бессонной.
        assertEquals(1L, result.getResult(), "Должна быть 1 бессонная ночь");
    }

    @Test
    void shouldHandleSleepStartingAfterMidnight() {
        Metrics metrics = new Metrics();
        // Сон начинается после полуночи (например, в 01:00), но попадает в диапазон 00:00-06:00
        metrics.setMetrics(List.of(
                "29.12.25 01:00;29.12.25 05:00;GOOD"
        ));

        SleeplessNights sleeplessNights = new SleeplessNights();
        SleepAnalysisResult result = sleeplessNights.apply(metrics);

        // Диапазон дат: 29.
        // Сон начался в 01:00 29-го числа. Это попадает в условие "inRange" (00:00 - 06:00).
        // Значит, ночь засчитана как "спал".
        assertEquals(0L, result.getResult(), "Сон после полуночи должен считаться");
    }

    @Test
    void shouldHandleSleepEndingBeforeMidnightNextDay() {
        Metrics metrics = new Metrics();
        // Сон днем, не захватывающий ночь (например, 14:00 - 16:00)
        // Это должно считаться бессонной ночью для этой даты, если нет другого сна.
        metrics.setMetrics(List.of(
                "29.12.25 14:00;29.12.25 16:00;GOOD"
        ));

        SleeplessNights sleeplessNights = new SleeplessNights();
        SleepAnalysisResult result = sleeplessNights.apply(metrics);

        // Диапазон: 29.
        // Сон 14:00-16:00 не попадает в 00:00-06:00 и не переходит через полночь.
        // Значит, ночь 29-го считается бессонной.
        assertEquals(1L, result.getResult(), "Дневной сон не должен считаться за ночной");
    }
}