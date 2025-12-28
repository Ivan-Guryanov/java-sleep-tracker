package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepPeriod {
    private LocalDateTime startSleep;
    private LocalDateTime endSleep;
    private String sleepQuality;

    public SleepPeriod(LocalDateTime startSleep, LocalDateTime endSleep, String sleepQuality) {
        this.startSleep = startSleep;
        this.endSleep = endSleep;
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getStartSleep() {
        return startSleep;
    }

    public void setStartSleep(LocalDateTime startSleep) {
        this.startSleep = startSleep;
    }

    public LocalDateTime getEndSleep() {
        return endSleep;
    }

    public void setEndSleep(LocalDateTime endSleep) {
        this.endSleep = endSleep;
    }

    public String getSleepQuality() {
        return sleepQuality;
    }

    public void setSleepQuality(String sleepQuality) {
        this.sleepQuality = sleepQuality;
    }
}
